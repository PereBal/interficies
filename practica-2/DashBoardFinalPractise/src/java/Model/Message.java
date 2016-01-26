package Model;

import Database.Chat.DBActions;
import Database.Chat.Exceptions.ChatDoNotExistsException;
import Database.Chat.Exceptions.UserIsNotInPartyException;
import Database.Www.Exceptions.UserDoNotExistsException;
import java.util.Date;
import org.bson.types.ObjectId;

public class Message {
  
  private final ObjectId id;
  private final User user;
  private final String text;
  private final Date created_at;
  
  public Message(String chat_id, int user_id, String text) throws 
    UserDoNotExistsException, 
    UserIsNotInPartyException, 
    ChatDoNotExistsException 
  {
    Message m = DBActions.createMessage(chat_id, user_id, text);
    
    this.id         = m.getId();
    this.user       = m.getUser();
    this.text       = m.getText();
    this.created_at = m.getCreatedAt();
  }
  
  public Message(ObjectId id, User user, String text) {
    this.id         = id;
    this.user       = user;
    this.text       = text;
    this.created_at = id.getDate();
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
  
  ///////////////////////////////////
  /////   Wrappering DBActions!!!
  ///////////////////////////////////
  public static Message retrieveByPk(String id) {
    return DBActions.getMessageById(id);
  }
  
}
