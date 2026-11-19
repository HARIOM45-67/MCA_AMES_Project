package in.gitam.ames;
import jakarta.servlet.annotation.*;import jakarta.servlet.http.*;import java.io.*;
@WebServlet("/logout") public class LogoutServlet extends HttpServlet {
 protected void doGet(HttpServletRequest q,HttpServletResponse p)throws IOException {if(q.getSession(false)!=null)q.getSession(false).invalidate();p.sendRedirect("index.jsp");}
}
