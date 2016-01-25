package Objects;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

public class Chat {
  
  private ObjectId id;
  private Boolean is_deleted;
  private List<Party> parties;
  private List<Message> messages;
  private Date created_at;
  
  public Chat() {
  }

  public ObjectId getId() {
    return id;
  }

  public Boolean getIsDeleted() {
    return is_deleted;
  }

  public List<Party> getParties() {
    return parties;
  }

  public List<Message> getMessages() {
    return messages;
  }

  public Date getCreatedAt() {
    return created_at;
  }
}
