package db.chat;

public class Sample {

  public static final String HOST = "localhost";
  public static final int PORT    = 27017;
  public static final String DB   = "chat";
  public static final String COLL = "chats";
  public static final String OPTS = "";

  public static final String OPTS() {
    return (OPTS.equals("")) ? "" : "?" + OPTS;
  }
}
