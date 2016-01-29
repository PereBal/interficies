package database.chat;

public class DBProperties {

  static public final String HOST = "localhost";
  static public final String PORT = "27017";
  static public final String DB = "chat";
  static public final String COLL = "chats";
  public static final String OPTS = "";

  public static final String OPTS() {
    return (OPTS.equals("")) ? "" : "?" + OPTS;
  }
}
