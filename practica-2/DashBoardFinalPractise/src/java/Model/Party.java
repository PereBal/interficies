package Model;

public class Party {
  
  private final User user;
  private Message last_read_message;
  
  public Party(User user, Message message) {
    this.user              = user;
    this.last_read_message = message;
  }

  public User getUser() {
    return user;
  }

  public Message getLastReadMessage() {
    return last_read_message;
  }
  
  public void setLastReadMessage(Message message) {
    // guardar a mongo
    this.last_read_message = message;
  }
}
