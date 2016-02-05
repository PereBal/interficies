package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Chat;
import model.Message;
import model.User;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import servlets.tools.Helper;
import database.www.exceptions.UserDoesNotExistException;
import database.chat.exceptions.UserNotInPartyException;
import database.chat.exceptions.ChatDoesNotExistException;

@WebServlet(name = "Chats", urlPatterns = {"/chats/*"})
public class Chats extends HttpServlet {

  /**
   *
   * @param request
   * @return
   */
  protected ObjectId getChatId(HttpServletRequest request) {
    try { // rewrite all this bullshit
      String  [] path = request.getPathInfo().split("/");

      if (path.length == 2) {
        return new ObjectId(path[1]);
      }
    } catch (NullPointerException e) {}

    return null;
  }

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
    Helper.authenticate(request, response);
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
    processRequest(request, response);

    if (Helper.isAjax(request)) {
      response.setContentType("application/json;charset=UTF-8");
      try {
        User user = Helper.getCurrentUser(request);

        String unread = request.getParameter("unread");
        String skip   = request.getParameter("skip");

        JSONArray JSONChats = new JSONArray();
        List<Chat> chats = Chat.retrieveChatsByUserPk(user.getId());

        if (skip != null) {
          try {
            Chat skipChat = Chat.retrieveByPk(skip);
            int i = chats.indexOf(skipChat);
            chats.remove(i);
          } catch (ChatDoesNotExistException | UserNotInPartyException e) {}
        }

        for (Chat chat : chats) {
          JSONObject object = new JSONObject();

          String numberUnreadMessages = Integer.toString(chat.countUnreadMessages(user.getId()));
          object.put("count", numberUnreadMessages);

          List<Message> messages = null;
          if (unread == null || unread.equals("false")) {
            messages = chat.getMessages();
          } else if (unread.equals("true")) {
            messages = chat.getUnreadMessages(user.getId());
          }

          String lastMessage = messages.size() > 0 ? messages.get(0).getText() : "";
          object.put("last_msg", lastMessage);

          JSONObject JSONChat = new JSONObject().put(chat.toString(), object);
          JSONChats.put(JSONChat);
        }

        JSONChats.write(response.getWriter());
		    response.getWriter().close();
      } catch (UserDoesNotExistException | ChatDoesNotExistException | UserNotInPartyException ex) {
        Logger.getLogger(Chats.class.getName()).log(Level.SEVERE, null, ex);
        response.sendRedirect("/duckboard");
      }
    } else {
      try {
        User user = Helper.getCurrentUser(request);
        List<Chat> chats = user.getChats();
        request.setAttribute("chats", chats);

        Chat currentChat = null;
        ObjectId chatId = getChatId(request);
        if (chatId != null) {
          currentChat = Chat.retrieveByPk(chatId.toString());
        }
        request.setAttribute("currentChat", currentChat);
      } catch (UserDoesNotExistException | ChatDoesNotExistException | UserNotInPartyException ex) {
        Logger.getLogger(Chats.class.getName()).log(Level.SEVERE, null, ex);
        response.sendRedirect("/duckboard");
      }

      request.getRequestDispatcher("/chats.jsp").forward(request, response);
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

    User user     = Helper.getCurrentUser(request);
    String userId = request.getParameter("party");

    if (userId == null) {
      Helper.setErrorFlash(request, "Hablar solo esta guay, pero aquí nos gusta hablar con otras personas!!!");
      response.sendRedirect("/duckboard/chats");
    }

    try {
      Chat currentChat = Chat.create(user.getId(), Integer.parseInt(userId));
      response.sendRedirect("/duckboard/chats/" + currentChat);
    } catch (NumberFormatException | UserDoesNotExistException e) {
      Helper.setErrorFlash(request, "El usuario no existe");
      response.sendRedirect("/duckboard/chats");
    }
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
