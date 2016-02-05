package servlets.tools;

import database.www.DBActions;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import model.User;

public class Helper {

  public static boolean isAjax(HttpServletRequest request) {
    return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
  }

  public static boolean isLogged(HttpServletRequest request) {
    if (request == null) {
      return false;
    }

    return request.getSession(false).getAttribute("userId") != null;
  }
  
  public static List<Flash> getFlash(HttpServletRequest request) {
    return (List<Flash>) request.getSession(false).getAttribute("flash");
  }
  
  public static User getCurrentUser(HttpServletRequest request) {
    if (isLogged(request)) {
      int userId = (int) request.getSession().getAttribute("userId");
      return DBActions.getUserById(userId);
    }

    return null;
  }
}
