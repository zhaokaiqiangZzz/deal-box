package xiaoqiangZzz.api.dealBox.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Chat {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany
  @JsonView(ChatMessagesJsonView.class)
  private List<ChatMessage> chatMessages = new ArrayList<>();

  @ManyToMany
  @JsonView(UsersJsonView.class)
  private List<User> users = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<ChatMessage> getChatMessages() {
    return chatMessages;
  }

  public void setChatMessages(List<ChatMessage> chatMessages) {
    this.chatMessages = chatMessages;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public interface UsersJsonView {
  }

  public interface ChatMessagesJsonView {
  }
}
