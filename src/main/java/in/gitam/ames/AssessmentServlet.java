package in.gitam.ames;
import jakarta.servlet.http.*;import jakarta.servlet.annotation.*;import java.io.*;import java.sql.*;
@WebServlet("/assessment") public class AssessmentServlet extends HttpServlet {
 protected void doPost(HttpServletRequest q,HttpServletResponse p)throws IOException {
  HttpSession s=q.getSession(false);if(s==null||!isManager(s)){p.sendError(403);return;}
  try{
   int studentId=Integer.parseInt(q.getParameter("studentId"));
   double got=Double.parseDouble(q.getParameter("marks")),max=Double.parseDouble(q.getParameter("max"));
   String type=q.getParameter("type"),title=q.getParameter("title"),date=q.getParameter("date");
   if(got<0||max<=0||got>max||title==null||title.isBlank()||date==null||date.isBlank()||!isType(type)){p.sendRedirect("assessments.jsp?error=marks");return;}
   try(Connection c=DB.get();PreparedStatement x=c.prepareStatement("INSERT INTO assessments(student_id,assessment_type,title,marks_obtained,max_marks,assessment_date,entered_by) VALUES(?,?,?,?,?,?,?)")){
    x.setInt(1,studentId);x.setString(2,type);x.setString(3,title.trim());x.setDouble(4,got);x.setDouble(5,max);x.setDate(6,Date.valueOf(date));x.setInt(7,(Integer)s.getAttribute("userId"));x.executeUpdate();p.sendRedirect("assessments.jsp?saved=1");return;
   }
  }catch(NumberFormatException|java.time.format.DateTimeParseException e){p.sendRedirect("assessments.jsp?error=marks");return;}
  catch(Exception e){getServletContext().log("Assessment save failed",e);p.sendRedirect("assessments.jsp?error=1");}
 }
 private boolean isManager(HttpSession s){String role=(String)s.getAttribute("role");return "ADMIN".equals(role)||"TEACHER".equals(role);}
 private boolean isType(String type){return "SESSIONAL".equals(type)||"ASSIGNMENT".equals(type)||"CLASSWORK".equals(type)||"EXTERNAL".equals(type);}
}
