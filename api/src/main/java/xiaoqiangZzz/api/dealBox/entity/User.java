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
  private List<Goods> goods;

  @OneToMany
  private List<Chat> chats;

  /**
   * 状态：
   * 0 冻结中
   * 1 正常.
   */
  private Integer status = User.STATUS_NORMAL;

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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
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

  public interface PasswordJsonView {
  }


  public interface RolesJsonView {
  }
}
