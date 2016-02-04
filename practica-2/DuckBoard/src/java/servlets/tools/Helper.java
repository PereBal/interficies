package servlets.tools;

import javax.servlet.http.HttpServletRequest;

public class Helper {
  
  public static boolean isAjax(HttpServletRequest request) {
    return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
  }
  
  // TO DO
  public static boolean isAdmin(HttpServletRequest request) {
    return true;
  }
}
