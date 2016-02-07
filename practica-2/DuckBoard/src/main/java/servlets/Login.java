package servlets;

import model.User;
import java.io.IOException;
import db.www.DBActions;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlets.tools.Helper;
import servlets.tools.Sesion;

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
    String email    = request.getParameter("email");
    String password = request.getParameter("password");
    
    Sesion session = new Sesion(request.getSession());

    User user = DBActions.getUserByEmail(email, password);

    if (user == null) {
      Helper.setNewErrorFlash(session, "El usuario no existe o el correo ha sido mal introducido");
    } else {
      session.autenticate(user);
      session.conf().setMaxInactiveInterval(600);
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
