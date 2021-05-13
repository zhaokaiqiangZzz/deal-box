package xiaoqiangZzz.api.dealBox.controller;

import xiaoqiangZzz.api.dealBox.entity.User;
import xiaoqiangZzz.api.dealBox.repository.UserRepository;
import xiaoqiangZzz.api.dealBox.service.UserService;
import xiaoqiangZzz.api.dealBox.vo.PasswordUser;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.security.Principal;

@RestController
@RequestMapping("user")
public class UserController {
  private final UserRepository userRepository;
  private final UserService userService;

  public UserController(UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @PostMapping("login")
  public User loginByToken(@RequestBody User user, HttpServletResponse response) {
    return userService.login(user.getUsername(), user.getPassword(), response);
  }

  @GetMapping("currentLoginUser")
  public User getCurrentLoginUser() {
    return this.userService.getCurrentUser();
  }

  /**
   * 校验密码是否正确接口
   * @param user user
   * @return boolean
   */
  @PostMapping("checkPasswordIsRight")
  public boolean checkPasswordIsRight(@RequestBody PasswordUser user) {
    return this.userService.checkPasswordIsRight(user.getPassword());
  }

  /**
   * 更新密码接口.
   * @param user user
   * @throws ValidationException 验证异常.
   */
  @PutMapping("updatePassword")
  public void updatePassword(@RequestBody PasswordUser user) throws ValidationException {
    this.userService.updatePassword(user.getPassword(), user.getNewPassword());
  }
}