package Objects;

import java.util.Date;
import org.bson.types.ObjectId;

public class Message {
  
  private ObjectId id;
  private User user;
  private String text;
  private Date created_at;
  
  public Message() {
  }

  public ObjectId getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public String getText() {
    return text;
  }

  public Date getCreatedAt() {
    return created_at;
  }
}
