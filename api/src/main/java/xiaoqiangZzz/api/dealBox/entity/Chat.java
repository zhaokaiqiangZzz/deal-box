package xiaoqiangZzz.api.dealBox.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Chat {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany
  private List<Message> messages = new ArrayList<>();

  @OneToOne
  private User buyUser;

  @OneToOne
  private User sellUser;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Message> getMessages() {
    return messages;
  }

  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  public User getBuyUser() {
    return buyUser;
  }

  public void setBuyUser(User buyUser) {
    this.buyUser = buyUser;
  }

  public User getSellUser() {
    return sellUser;
  }

  public void setSellUser(User sellUser) {
    this.sellUser = sellUser;
  }
}
