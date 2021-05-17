package xiaoqiangZzz.api.dealBox.entity;

import javax.persistence.*;

@Entity
public class ChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String message;

  private Long userId;

  private Long fromUserId;

  public ChatMessage() {}


  public ChatMessage(String message) {
    this.message = message;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getFromUserId() {
    return fromUserId;
  }

  public void setFromUserId(Long fromUserId) {
    this.fromUserId = fromUserId;
  }
}
