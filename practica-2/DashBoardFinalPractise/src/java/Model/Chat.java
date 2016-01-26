package Model;

import Database.Chat.DBActions;
import Database.Chat.Exceptions.ChatDoNotExistsException;
import Database.Chat.Exceptions.PartyNotCorrectSizeException;
import Database.Chat.Exceptions.UserIsNotInPartyException;
import Database.Www.Exceptions.UserDoNotExistsException;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

public class Chat {
  
  private final ObjectId id;
  private Boolean is_deleted;
  private final List<Party> parties;
  private final List<Message> messages;
  private final Date created_at;
  
  public Chat(List<Party> parties) throws 
    UserDoNotExistsException, 
    PartyNotCorrectSizeException 
  {
    if (parties.size() != 2) {
      throw new PartyNotCorrectSizeException();
    } 
    User u1 = parties.get(0).getUser();
    User u2 = parties.get(1).getUser();
    
    Chat c = DBActions.createChat(u1.getId(), u2.getId());
    
    this.id         = c.getId();
    this.is_deleted = c.getIsDeleted();
    this.parties    = c.getParties();
    this.messages   = c.getMessages();
    this.created_at = c.getCreatedAt();
  }
  
  public Chat(ObjectId id, Boolean is_deleted, List<Party> parties, List<Message> messages) {
    this.id         = id;
    this.is_deleted = is_deleted;
    this.parties    = parties;
    this.messages   = messages;
    this.created_at = id.getDate(); //revisar el format del date object id
  }

  public ObjectId getId() {
    return id;
  }

  public Boolean getIsDeleted() {
    return is_deleted;
  }

  public void setIsDeleted(Boolean is_deleted) {
    // guardar a mongo
    this.is_deleted = is_deleted;
  }
  
  public List<Party> getParties() {
    return parties;
  }

  public List<Message> getMessages() {
    return messages;
  }
  
  public void setMessages(List<Message> messages) throws 
    UserDoNotExistsException, 
    UserIsNotInPartyException, 
    ChatDoNotExistsException 
  {
    for (Message message : messages) {
      Message msg = DBActions.createMessage(this.id.toString(), message.getUser().getId(), message.getText());
      this.messages.add(msg);
    }
  }

  public Date getCreatedAt() {
    return created_at;
  }
  
  ///////////////////////////////////
  /////   Wrappering DBActions!!!
  ///////////////////////////////////
  public static Chat retrieveByPk(String id) {
    return DBActions.getChatById(id);
  }
}
