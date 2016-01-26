package Model;

import Database.Chat.DBActions;
import Database.Chat.Exceptions.ChatDoNotExistsException;
import Database.Chat.Exceptions.UserIsNotInPartyException;
import Database.Www.Exceptions.UserDoNotExistsException;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

public class Message {
  
  private final ObjectId id;
  private final ObjectId chat_id;
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
    this.chat_id    = new ObjectId(chat_id);
    this.user       = m.getUser();
    this.text       = m.getText();
    this.created_at = m.getCreatedAt();
  }
  
  public Message(ObjectId id, ObjectId chat_id, User user, String text) {
    this.id         = id;
    this.chat_id    = chat_id;
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
  
  public String getChatId() {
    if (this.chat_id == null) {
      return null;
    }
    
    return this.chat_id.toString();
  }
  
  public Chat getChat() {
    if (this.chat_id == null) {
      return null;
    }
    
    return DBActions.getChatById(this.chat_id.toString());
  }
  
  ///////////////////////////////////
  /////   Wrappering DBActions!!!
  ///////////////////////////////////
  public static Message retrieveByPk(String id) {
    return DBActions.getMessageById(id);
  }
  
  // TO DO
  public static List<Message> retrieveUnreadMessagesByChatPk(String chat_id, int user_id) {
    return null;
  }
}
