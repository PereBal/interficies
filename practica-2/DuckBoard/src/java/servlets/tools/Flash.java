package servlets.tools;

public class Flash {
  
  public static final String ERROR = "error";
  public static final String SUCCESS = "success";
  
  private final String message;
  private final String claz;
  
  public Flash(String message, String claz) {
    this.message = message;
    this.claz    = claz;
  }

  public String getMessage() {
    return message;
  }

  public String getClaz() {
    return claz;
  }
}
