package xiaoqiangZzz.api.dealBox.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.List;

/**
 * 用户实体.
 */
@Entity
public class User extends BaseEntity {

  /**
   * 默认密码
   */
  public static final String DEFAULT_PASSWORD = "hebut";

  /**
   * 用户状态.
   */
  public static Integer STATUS_FROZEN = 0;
  public static Integer STATUS_NORMAL = 1;


  @JsonView(PasswordJsonView.class)
  private String password;

  @OneToMany
  @JsonView(GoodsJsonVIew.class)
  private List<Goods> goods;

  @ManyToMany
  @JsonView(ChatsJsonView.class)
  private List<Chat> chats;

  private String imageUrl;

  public String getPetName() {
    return petName;
  }

  public void setPetName(String petName) {
    this.petName = petName;
  }

  private String petName;

  @Column(unique = true)
  private String username;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<Goods> getGoods() {
    return goods;
  }

  public void setGoods(List<Goods> goods) {
    this.goods = goods;
  }

  public List<Chat> getChats() {
    return chats;
  }

  public void setChats(List<Chat> chats) {
    this.chats = chats;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public interface PasswordJsonView {
  }

  public interface ChatsJsonView {
  }

  public interface GoodsJsonVIew {
  }
}
