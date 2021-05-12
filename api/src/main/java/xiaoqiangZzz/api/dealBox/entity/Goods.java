package xiaoqiangZzz.api.dealBox.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Goods {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  @OneToMany
  private List<Attachment> attachments = new ArrayList<>();

  @OneToOne
  private User createUser;

  @OneToOne
  private User buyUser;

  private Integer status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Attachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<Attachment> attachments) {
    this.attachments = attachments;
  }

  public User getCreateUser() {
    return createUser;
  }

  public void setCreateUser(User createUser) {
    this.createUser = createUser;
  }

  public User getBuyUser() {
    return buyUser;
  }

  public void setBuyUser(User buyUser) {
    this.buyUser = buyUser;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
