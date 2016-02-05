package servlets;

import database.chat.exceptions.ChatDoesNotExistException;
import database.chat.exceptions.UserNotInPartyException;
import database.www.DBActions;
import database.www.exceptions.UserDoesNotExistException;
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

@WebServlet(name = "Chats", urlPatterns = {"/chats/*"})
public class Chats extends HttpServlet {
  
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
    // Logged user, sino redirect
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
      try {
        User user = Helper.getCurrentUser(request);

        String unread = request.getParameter("unread");
        String skip   = request.getParameter("skip");

        JSONArray JSONChats = new JSONArray();
        
        List<Chat> chats = Chat.retrieveChatsByUserPk(user.getId());
        for (Chat chat : chats) {
          JSONObject object = new JSONObject();
          
          String [] attrs = new String[2];
          
          attrs[0] = Integer.toString(chat.countUnreadMessages(user.getId()));
          
          if (unread == null || unread.equals("false")) {          
            object.put("count", attrs[0]);

            List<Message> messages = chat.getMessages();
            attrs[1] = messages.size() > 0 ? messages.get(0).getText() : "";
            object.put("last_msg", attrs[1]);
          } else if (unread.equals("true")) {
            // shan de fer coses
          }
          
          JSONObject JSONChat = new JSONObject().put(chat.toString(), object);
          
          JSONChats.put(JSONChat);
        }
        
        response.setContentType("application/json;charset=UTF-8");
        JSONChats.write(response.getWriter());
		response.getWriter().close();
      } catch (UserDoesNotExistException | ChatDoesNotExistException | UserNotInPartyException ex) {
        Logger.getLogger(Chats.class.getName()).log(Level.SEVERE, null, ex);
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
        response.sendError(404, "BITCHHHHHH LA URL NO ESTA BEN FORMADA O EL CHAT NO EXISTEIS O ALGO");
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
