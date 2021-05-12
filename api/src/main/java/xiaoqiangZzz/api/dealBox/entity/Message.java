package xiaoqiangZzz.api.dealBox.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String chatMessage;

  private boolean isMeSend;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getChatMessage() {
    return chatMessage;
  }

  public void setChatMessage(String chatMessage) {
    this.chatMessage = chatMessage;
  }

  public boolean isMeSend() {
    return isMeSend;
  }

  public void setMeSend(boolean meSend) {
    isMeSend = meSend;
  }
}
