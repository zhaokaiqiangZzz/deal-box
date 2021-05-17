package xiaoqiangZzz.api.dealBox.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Goods {
  public static Integer STATUS_ISSUING = 0;
  public static Integer STATUS_SOLD = 1;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  private int price;

  @OneToMany
  private List<Attachment> attachments = new ArrayList<>();

  @OneToOne
  @JsonView(CreateUserJsonView.class)
  private User createUser;

  @OneToOne
  @JsonView(BuyUserJsonView.class)
  private User buyUser;

  private Integer status = STATUS_ISSUING;

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

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public interface CreateUserJsonView {
  }
  public interface BuyUserJsonView {
  }
}
