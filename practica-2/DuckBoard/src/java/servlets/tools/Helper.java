package servlets.tools;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Helper {

  public static boolean isAjax(HttpServletRequest request) {
    return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
  }

  public static boolean isLogged(HttpServletRequest request) {
    Sesion s = new Sesion(request.getSession());
    return s.isValid();
  }

  public static model.User getCurrentUser(HttpServletRequest request) {
    Sesion s = new Sesion(request.getSession());
    return s.getUser();
  }

  public static boolean authenticate(Sesion s, HttpServletResponse response) throws IOException {
    if (!s.isValid()) {
      response.sendRedirect("/duckboard");
      return false;
    }
    return true;
  }

  public static java.util.List<Flash> getFlash(HttpServletRequest request) {
    Sesion s = new Sesion(request.getSession(false));
    return s.getFlash();
  }

  public static void setErrorFlash(Sesion s, String msg) {
    s.setFlash(new Flash(msg, Flash.ERROR), false);
  }

  public static void setSuccessFlash(Sesion s, String msg) {
    s.setFlash(new Flash(msg, Flash.SUCCESS), false);
  }

  public static void setNewErrorFlash(Sesion s, String msg) {
    s.setFlash(new Flash(msg, Flash.ERROR), true);
  }

  public static void setNewSuccessFlash(Sesion s, String msg) {
    s.setFlash(new Flash(msg, Flash.SUCCESS), true);
  }
}
