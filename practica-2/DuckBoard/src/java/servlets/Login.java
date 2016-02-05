package servlets;

import model.User;
import java.io.IOException;
import database.www.DBActions;
import javax.servlet.http.Cookie;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String email     = request.getParameter("email");
    String password  = request.getParameter("password");

    User user = DBActions.getUserByEmail(email, password);
    
    if (user != null) { // aixo es al reves
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.addCookie(new Cookie("flash", "El usuario no existe o has introducido mal el correo"));
    } else {
      HttpSession session = request.getSession();   
      session.setAttribute("userId", 1); // aqui va una id
      session.setMaxInactiveInterval(3600);    
    }
    
    response.sendRedirect("/duckboard");
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }
}
