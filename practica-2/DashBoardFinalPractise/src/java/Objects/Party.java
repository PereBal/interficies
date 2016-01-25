package Objects;

public class Party {
  
  private User user;
  private Message last_read_message;
  
  public Party() {
  }

  public User getUser() {
    return user;
  }

  public Message getLastReadMessage() {
    return last_read_message;
  }
}
