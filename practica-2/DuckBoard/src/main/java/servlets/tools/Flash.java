package servlets.tools;

public class Flash {
  
  public static final String ERROR = "Error";
  public static final String SUCCESS = "Genial";
  
  private final String message;
  private final String clazz;
  
  public Flash(String message, String clazz) {
    this.message = message;
    this.clazz    = clazz;
  }

  public String getMessage() {
    return message;
  }

  public String getClazz() {
    return clazz;
  }
  
  public org.json.JSONObject toJSON() {
    return new org.json.JSONObject().put(
            "message", message
    ).put(
            "clazz", clazz
    );
  }
}
