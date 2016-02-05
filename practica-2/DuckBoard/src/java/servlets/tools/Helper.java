package servlets.tools;

import database.www.DBActions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    List<Flash> list = (List<Flash>) request.getSession(false).getAttribute("flash");
    request.getSession(false).removeAttribute("flash");

    return list;
  }

  public static User getCurrentUser(HttpServletRequest request) {
    if (isLogged(request)) {
      int userId = (int) request.getSession().getAttribute("userId");
      return DBActions.getUserById(userId);
    }

    return null;
  }

  public static void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if (!isLogged(request)) {
      response.sendRedirect("/duckboard");
    }
  }

  public static void setErrorFlash(HttpServletRequest request, String msg) {
    setFlash(request, msg, Flash.ERROR);
  }

  public static void setSuccessFlash(HttpServletRequest request, String msg) {
    setFlash(request, msg, Flash.SUCCESS);
  }
  
  private static void setFlash(HttpServletRequest request, String msg, String claz) {
    if (request.getSession(false).getAttribute("flash") == null) {
      List<Flash> flash = new ArrayList<>();
      flash.add(new Flash(msg, claz));
      
      request.getSession(false).setAttribute("flash", flash);
    } else {
      List<Flash> list = (List<Flash>) request.getSession(false).getAttribute("flash");
      list.add(new Flash(msg, claz));
    }
  }
}
