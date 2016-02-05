package servlets;

import model.User;
import java.io.IOException;
import database.www.DBActions;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlets.tools.Flash;

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
      
    HttpSession session = request.getSession();     
    
    if (user == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      List<Flash> flash = new ArrayList<>();
      flash.add(new Flash("El usuario no existe o has introducido mal el correo", Flash.ERROR));      
      session.setAttribute("flash", flash);
    } else {
      session.setAttribute("userId", user.getId()); // aqui va una id
      session.setMaxInactiveInterval(600);    
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
