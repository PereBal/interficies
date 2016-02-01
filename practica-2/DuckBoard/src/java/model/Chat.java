package model;

import database.chat.DBActions;
import database.chat.exceptions.ChatDoesNotExistException;
import database.chat.exceptions.UserNotInPartyException;
import database.www.exceptions.UserDoesNotExistException;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

public class Chat {

  private final ObjectId id;
  private Boolean isDeleted;
  private final List<Party> parties;
  private final List<Message> messages;
  private final Date createdAt;

  public Chat(int userId1, int userId2) throws UserDoesNotExistException {
    /*
     * Alternative:
     * 1. Create/Get parties List
     * 2. Create/Get messages List
     * 3. id = DBActions.createChat(parties, messages)
     * 4. if (id != null) createdAt = now(); 
    */
    Chat c = DBActions.createChat(userId1, userId2);

    this.id = c.getId();
    this.isDeleted = c.getIsDeleted();
    this.parties = c.getParties();
    this.messages = c.getMessages();
    this.createdAt = c.getCreatedAt();
  }

  public Chat(ObjectId id, boolean isDeleted, List<Party> parties, List<Message> messages) {
    this.id = id;
    this.isDeleted = isDeleted;
    this.parties = parties;
    this.messages = messages;
    this.createdAt = id.getDate(); //revisar el format del date object id
  }

  public ObjectId getId() {
    return id;
  }

  public Boolean getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(Boolean isDeleted) throws ChatDoesNotExistException {
    DBActions.updateIsDeleted(this.id.toString(), isDeleted);
    this.isDeleted = isDeleted;
  }

  public List<Party> getParties() {
    return parties;
  }

  public List<Message> getMessages() {
    return messages;
  }

  // TO DO
  public List<Message> getUnreadMessages(int user_id) {
    return null;
  }

  public void setMessages(List<Message> messages) throws
          UserDoesNotExistException,
          UserNotInPartyException,
          ChatDoesNotExistException {
    for (Message message : messages) {
      Message msg = DBActions.createMessage(this.id.toString(), message.getUser().getId(), message.getText());
      this.messages.add(msg);
    }
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  ///////////////////////////////////
  /////   Wrappering DBActions!!!
  ///////////////////////////////////
  public static Chat retrieveByPk(String id) {
    return DBActions.getChatById(id);
  }

  // TO DO
  public static List<Chat> retrieveChatsByUserPk(int user_id) {
    return null;
  }
}
