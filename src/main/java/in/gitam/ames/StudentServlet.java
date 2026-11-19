package in.gitam.ames;
import jakarta.servlet.http.*;import jakarta.servlet.annotation.*;import java.io.*;import java.sql.*;
@WebServlet("/students") public class StudentServlet extends HttpServlet {
 protected void doPost(HttpServletRequest q,HttpServletResponse p)throws IOException {
  HttpSession s=q.getSession(false);if(s==null||!isManager(s)){p.sendError(403);return;}
  String roll=q.getParameter("roll"),name=q.getParameter("name"),email=q.getParameter("email"),semester=q.getParameter("semester"),phone=q.getParameter("phone");
  if(roll==null||roll.isBlank()||name==null||name.isBlank()||semester==null||semester.isBlank()){p.sendRedirect("students.jsp?error=required");return;}
  try{
   int sem=Integer.parseInt(semester);
   if(sem<1||sem>6){p.sendRedirect("students.jsp?error=semester");return;}
   try(Connection c=DB.get();PreparedStatement x=c.prepareStatement("INSERT INTO students(roll_no,full_name,email,semester,phone) VALUES(?,?,?,?,?)")){
    x.setString(1,roll.trim());x.setString(2,name.trim());x.setString(3,email==null||email.isBlank()?null:email.trim());x.setInt(4,sem);x.setString(5,phone==null||phone.isBlank()?null:phone.trim());x.executeUpdate();p.sendRedirect("students.jsp?added=1");return;
   }
  }catch(NumberFormatException e){p.sendRedirect("students.jsp?error=semester");return;}
  catch(Exception e){q.getServletContext().log("Student save failed",e);p.sendRedirect("students.jsp?error=1");}
 }
 private boolean isManager(HttpSession s){String role=(String)s.getAttribute("role");return "ADMIN".equals(role)||"TEACHER".equals(role);}
}
