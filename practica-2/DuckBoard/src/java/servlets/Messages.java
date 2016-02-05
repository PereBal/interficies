package servlets;

import db.chat.exceptions.ChatDoesNotExistException;
import db.chat.exceptions.UserNotInPartyException;
import db.www.exceptions.UserDoesNotExistException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Message;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import servlets.tools.Helper;

@WebServlet(name = "Messages", urlPatterns = {"/chat/messages"})
public class Messages extends HttpServlet {

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
    if (!Helper.authenticate(request, response)) {
      return;
    }

    if (Helper.isAjax(request)) {
      User user = Helper.getCurrentUser(request);

      String chatId = request.getParameter("cid");
      String unread = request.getParameter("unread");
      String skip = request.getParameter("skip");

      if (chatId == null) {
        response.sendRedirect("/duckboard/chats");
        return;
      }

      int skipMessages;
      if (skip != null) {
        try {
          skipMessages = Integer.parseInt(skip);
        } catch (NumberFormatException e) {
          skipMessages = 0;
        }
      } else {
        skipMessages = 0;
      }

      List<Message> messages = new ArrayList<>();
      try {
        if (unread != null && unread.equals("true")) {
          messages = user.getUnreadMessages(chatId, skipMessages);
        } else {
          messages = user.getMessages(chatId, skipMessages);
        }
      } catch (ChatDoesNotExistException | UserNotInPartyException | UserDoesNotExistException e) {
        response.sendRedirect("/duckboard/chats");
      }

      JSONObject object = new JSONObject();
      JSONArray jsonMessages = new JSONArray();

      object.put("cid", chatId);
      for (Message message : messages) {
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("user_id", message.getUser().getId());
        jsonMessage.put("text", message.getText());
        jsonMessage.put("created_at", message.getCreatedAt());

        jsonMessages.put(jsonMessage);
      }
      object.put("messages", jsonMessages);

      jsonMessages.write(response.getWriter());
      response.getWriter().close();
    } else {
      response.sendRedirect("/duckboard/chats");
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
    if (!Helper.authenticate(request, response)) {
      return;
    }

    if (Helper.isAjax(request)) {
      User user = Helper.getCurrentUser(request);

      String chatId = request.getParameter("cid");
      String text = request.getParameter("text");
      
      if (chatId == null || text == null ) {
        response.sendRedirect("/duckboard/chats"); return;
      }
      
      try {
        Message.create(chatId, user.getId(), text);
      } catch (ChatDoesNotExistException | UserDoesNotExistException | UserNotInPartyException ex) {
        Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, null, ex);
        response.sendRedirect("/duckboard/chats"); return;
      }
    } else {
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
