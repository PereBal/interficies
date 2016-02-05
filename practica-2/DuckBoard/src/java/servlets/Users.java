package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servlets.tools.Flash;
import servlets.tools.Helper;

@WebServlet(name = "Users", urlPatterns = {"/users"})
public class Users extends HttpServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    Helper.isLogged(request);
  }

  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    if (Helper.isAjax(request)) {
      
    } else if (Helper.isLogged(request)) {
      
      request.getRequestDispatcher("/profile.jsp").forward(request, response);
    } else {
      request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
  }

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
    processRequest(request, response);

    HttpSession session = request.getSession();
    
    String email = request.getParameter("email");
    String name = request.getParameter("name");
    String lastName = request.getParameter("last_name");
    char sex = request.getParameter("sex").charAt(0);
    String authToken = java.util.UUID.randomUUID().toString();
    request.getSession().setAttribute("auth_token", authToken);
    LocalDate birthDay = LocalDate.parse(request.getParameter("birth_day"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    String quote = request.getParameter("quote");
    String pwd = request.getParameter("pwd");
    
    model.User u = new model.User(-1, email, name, lastName, sex, authToken, birthDay, quote);
    
    boolean registered = db.www.DBActions.insertUser(u, pwd);
    List<Flash> flash = new ArrayList<>();

    if (registered) {
      flash.add(new Flash("Has sido registrado correctamente, loggeate paradisfrutar!.", Flash.SUCCESS));
      session.setAttribute("flash", flash);
    } else {

    }

    response.sendRedirect("/duckboard");

  }

  /**
   * Handles the HTTP <code>PUT</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>Delete</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
