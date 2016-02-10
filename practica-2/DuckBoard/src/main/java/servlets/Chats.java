package servlets;

import db.chat.exceptions.ChatDoesNotExistException;
import db.chat.exceptions.UserNotInPartyException;
import db.www.exceptions.UserDoesNotExistException;
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
import servlets.tools.Sesion;


@WebServlet(name = "Chats", urlPatterns = {"/chats"})
public class Chats extends HttpServlet {

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
    response.setContentType("application/text-plain;charset=UTF-8");
    Sesion s = new Sesion(request.getSession());
    if (!Sesion.isAutenticated(s)) {
      response.sendRedirect("/duckboard"); return ;
    }
    
    if (Helper.isAjax(request) && request.getParameter("hell") == null) {
      try {
        User user = s.getUser();

        String unread = request.getParameter("unread");
        String skip   = request.getParameter("skip");

        JSONArray jsonChats = new JSONArray();
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

          JSONObject jsonChat = new JSONObject().put(chat.toString(), object);
          jsonChats.put(jsonChat);
        }

        jsonChats.write(response.getWriter());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().close();
      } catch (UserDoesNotExistException | ChatDoesNotExistException | UserNotInPartyException ex) {
        Logger.getLogger(Chats.class.getName()).log(Level.SEVERE, null, ex);
        response.sendRedirect("/duckboard");
      }
    } else {
      try {
        User user = s.getUser();
        List<Chat> chats = user.getChats();
        request.setAttribute("chats", chats);

        Chat currentChat = null;
        String chatIdP = request.getParameter("cid");
        
        if (chatIdP != null) {
          ObjectId chatId = new ObjectId(chatIdP);
          currentChat = Chat.retrieveByPk(chatId.toString());
          
          if (!currentChat.getMessages().isEmpty()) {
            currentChat.setLastReadMessage(user.getId());
          }
        }
        request.setAttribute("currentChat", currentChat);
      } catch (NullPointerException | UserDoesNotExistException | ChatDoesNotExistException | UserNotInPartyException ex) {
        Logger.getLogger(Chats.class.getName()).log(Level.SEVERE, null, ex);
        response.sendRedirect("/duckboard"); return;
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
    response.setContentType("application/text-plain;charset=UTF-8");
    Sesion s = new Sesion(request.getSession());
    if (!Sesion.isAutenticated(s)) {
      response.sendRedirect("/duckboard"); return;
    }

    User user     = s.getUser();
    String userId = request.getParameter("party");

    if (userId == null) {
      Helper.setErrorFlash(s, "Hablar solo, esta guay, pero aquí nos gusta hablar con otras personas!!!");
    }
    
    try {
      Chat currentChat = Chat.create(user.getId(), Integer.parseInt(userId));
      response.sendRedirect("/duckboard/chats?cid=" + currentChat);
    } catch (NumberFormatException | UserDoesNotExistException e) {
      Helper.setErrorFlash(s, "No me times, no hables con tu amigo invisble");
      response.sendRedirect("/duckboard/chats");
    }
  }
  
  /**
   * Handles the HTTP <code>DELETE</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("application/text-plain;charset=UTF-8");
    Sesion s = new Sesion(request.getSession());
    if (!Sesion.isAutenticated(s)) {
      response.sendRedirect("/duckboard"); return;
    }

    String chatId = request.getParameter("cid");

    if (chatId == null) {
      Helper.setErrorFlash(s, "¿No puedes no borrar un chat?");
      response.sendRedirect("/duckboard/chats");
    }

    try {
      Chat chat = Chat.retrieveByPk(chatId);
      chat.delete(s.getUserId());
    } catch (UserDoesNotExistException | ChatDoesNotExistException | UserNotInPartyException ex) {
      Helper.setErrorFlash(s, "No hemos sido capaces de borrar tu conversación :(");
      Logger.getLogger(Chats.class.getName()).log(Level.SEVERE, null, ex);
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
