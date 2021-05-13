package xiaoqiangZzz.api.dealBox.startup;

import xiaoqiangZzz.api.dealBox.entity.User;
import xiaoqiangZzz.api.dealBox.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 系统数据初始化.
 */
@Component
public class InitData {
  private static final Logger logger = LoggerFactory.getLogger(InitData.class);

  private UserRepository userRepository;

  private String systemAdminUsername = "15713301902";
  private String systemAdminPassword = "123456";


  public InitData(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void postConstruct() {

    List<User> users = this.userRepository.findAll();

    if (!users.isEmpty()) {
      return;
    }

    User systemAdmin = new User();
    systemAdmin.setStatus(User.STATUS_NORMAL);
    systemAdmin.setUsername(this.systemAdminUsername);
    systemAdmin.setPassword(this.systemAdminPassword);
    this.userRepository.save(systemAdmin);
  }
}
