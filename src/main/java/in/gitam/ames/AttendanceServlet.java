package in.gitam.ames;
import jakarta.servlet.http.*;import jakarta.servlet.annotation.*;import java.io.*;import java.sql.*;
@WebServlet("/attendance") public class AttendanceServlet extends HttpServlet {
 protected void doPost(HttpServletRequest q,HttpServletResponse p)throws IOException {
  HttpSession s=q.getSession(false);if(s==null||!isManager(s)){p.sendError(403);return;}
  try{
   int studentId=Integer.parseInt(q.getParameter("studentId"));
   String date=q.getParameter("date"),status=q.getParameter("status");
   if(date==null||date.isBlank()||!("PRESENT".equals(status)||"ABSENT".equals(status))){p.sendRedirect("attendance.jsp?error=required");return;}
   try(Connection c=DB.get();PreparedStatement x=c.prepareStatement("INSERT INTO attendance(student_id,attendance_date,status,marked_by) VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE status=VALUES(status),marked_by=VALUES(marked_by)")){
    x.setInt(1,studentId);x.setDate(2,Date.valueOf(date));x.setString(3,status);x.setInt(4,(Integer)s.getAttribute("userId"));x.executeUpdate();p.sendRedirect("attendance.jsp?saved=1");return;
   }
  }catch(NumberFormatException|java.time.format.DateTimeParseException e){p.sendRedirect("attendance.jsp?error=required");return;}
  catch(Exception e){getServletContext().log("Attendance save failed",e);p.sendRedirect("attendance.jsp?error=1");}
 }
 private boolean isManager(HttpSession s){String role=(String)s.getAttribute("role");return "ADMIN".equals(role)||"TEACHER".equals(role);}
}
