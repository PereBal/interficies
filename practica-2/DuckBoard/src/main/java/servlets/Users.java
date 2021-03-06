package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlets.tools.Helper;
import servlets.tools.Sesion;

@WebServlet(name = "Users", urlPatterns = {"/users"})
public class Users extends HttpServlet {

  private model.User getUserData(HttpServletRequest request) {
    return getUserData(-1, request);
  }

  private model.User getUserData(int userId, HttpServletRequest request) {
    String email = request.getParameter("email");
    String name = request.getParameter("name");
    String lastName = request.getParameter("last_name");
    char sex = request.getParameter("sex").charAt(0);
    String birthDay;
    try {
        birthDay = LocalDate.parse(request.getParameter("birth_day"), DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
    } catch (Exception e) {
        LocalDate a = LocalDate.now();
        a.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        birthDay = a.toString();
    }
    String quote = request.getParameter("quote");

    return new model.User(userId, email, name, lastName, sex, null, birthDay, quote);
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

    Sesion session = new Sesion(request.getSession());

    String id = request.getParameter("id");

    int uid = id != null ? Integer.parseInt(id) : -2014;

    if (Sesion.isAutenticated(session)) {
      if (Helper.isAjax(request)) {
        response.setContentType("application/json;charset=UTF-8");
        if (uid > -1) {
          // get User
          org.json.JSONObject res = db.www.DBActions.getUserById(uid).toJSON();
          try (java.io.PrintWriter out = response.getWriter()) {
            out.println(res.toString());
          }
        } else {
          // get Users
          org.json.JSONArray res = db.www.DBActions.getJSONUsers(request.getParameter("q"));
          try (java.io.PrintWriter out = response.getWriter()) {
            out.println(res.toString());
          }
        }
      } else if (uid > -1) {
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
      } else {
        request.getRequestDispatcher("/users.jsp").forward(request, response);
      }
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

    Sesion session = new Sesion(request.getSession());
    String pwd = request.getParameter("pwd");
    String authToken = java.util.UUID.randomUUID().toString();

    model.User user;

    if (request.getParameter("id") != null) {

      if (Sesion.isAutenticated(session)) {

        user = getUserData(session.getUserId(), request);
        if (db.www.DBActions.updateUser(user, pwd)) {

          session.setUser(user);
          Helper.setNewSuccessFlash(session, "Actualización completada :D.");

        } else {

          Helper.setNewErrorFlash(session, "Se ha producido un error :'(");
        }
      } else {

        Helper.setNewErrorFlash(session, "Sesion invalida -_-!!");
      }
      response.sendRedirect("/duckboard/users?id=" + session.getUserId());
    } else {

      user = getUserData(request);
      user.setAuthToken(authToken);
      if (db.www.DBActions.insertUser(user, pwd)) {

        session.autenticate(user.getEmail());
        if (user.getSex() == 'H') {
          Helper.setNewSuccessFlash(session, "Has sido registrado correctamente, se bienvenido " + user.getName() + "!.");
        } else {
          Helper.setNewSuccessFlash(session, "Has sido registrada correctamente, se bienvenida " + user.getName() + "!.");
        }
      } else {

        Helper.setNewErrorFlash(session, "Ha ocurrido un error");
      }
      response.sendRedirect("/duckboard");
    }
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
    Sesion session = new Sesion(request.getSession());
    if (Sesion.isAutenticated(session)) {
      if (db.www.DBActions.deleteUser(session.getUserId())) {
        Helper.setNewSuccessFlash(session, "Go fund yourself!");
      } else {
        Helper.setNewErrorFlash(session, "HAW! HAW! LOOOOSER!!!");
      }
    } else {
      Helper.setNewErrorFlash(session, "HAW! HAW! LOOOOSER!!!");
    }
    session.invalidate();
    // even if on /duckboard is a doDelete method, this shiet keeps fucking up...
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
  }// </editor-fold>

}
