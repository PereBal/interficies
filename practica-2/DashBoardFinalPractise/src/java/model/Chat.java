package model;

import database.chat.DBActions;
import database.chat.exceptions.ChatDoNotExistsException;
import database.chat.exceptions.UserIsNotInPartyException;
import database.www.exceptions.UserDoNotExistsException;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

public class Chat {

  private final ObjectId id;
  private Boolean is_deleted;
  private final List<Party> parties;
  private final List<Message> messages;
  private final Date created_at;

  public Chat(int user_id1, int user_id2) throws UserDoNotExistsException {
    Chat c = DBActions.createChat(user_id1, user_id2);

    this.id = c.getId();
    this.is_deleted = c.getIsDeleted();
    this.parties = c.getParties();
    this.messages = c.getMessages();
    this.created_at = c.getCreatedAt();
  }

  public Chat(ObjectId id, Boolean is_deleted, List<Party> parties, List<Message> messages) {
    this.id = id;
    this.is_deleted = is_deleted;
    this.parties = parties;
    this.messages = messages;
    this.created_at = id.getDate(); //revisar el format del date object id
  }

  public ObjectId getId() {
    return id;
  }

  public Boolean getIsDeleted() {
    return is_deleted;
  }

  public void setIsDeleted(Boolean is_deleted) throws ChatDoNotExistsException {
    DBActions.updateIsDeleted(this.id.toString(), is_deleted);
    this.is_deleted = is_deleted;
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
          UserDoNotExistsException,
          UserIsNotInPartyException,
          ChatDoNotExistsException {
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

  // TO DO
  public static List<Chat> retrieveChatsByUserPk(int user_id) {
    return null;
  }
}
