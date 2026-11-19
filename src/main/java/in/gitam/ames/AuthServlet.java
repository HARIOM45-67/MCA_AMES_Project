package in.gitam.ames;
import jakarta.servlet.http.*;import jakarta.servlet.annotation.*;import java.io.*;import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;
@WebServlet("/login")
public class AuthServlet extends HttpServlet {
 protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException {
  String email=req.getParameter("email"), pass=req.getParameter("password");
  if(email==null||email.isBlank()||pass==null||pass.isBlank()){resp.sendRedirect("index.jsp?error=1");return;}
  try(Connection c=DB.get();PreparedStatement p=c.prepareStatement("SELECT id,full_name,email,password_hash,role FROM users WHERE email=?")){
   p.setString(1,email);try(ResultSet r=p.executeQuery()){
    if(r.next() && BCrypt.checkpw(pass,r.getString("password_hash"))){
     HttpSession s=req.getSession(true);s.setAttribute("userId",r.getInt("id"));s.setAttribute("name",r.getString("full_name"));s.setAttribute("role",r.getString("role"));resp.sendRedirect("dashboard.jsp");return;
    }
   }
  }catch(Exception e){getServletContext().log("Login failed",e);}
  resp.sendRedirect("index.jsp?error=1");
 }
}
