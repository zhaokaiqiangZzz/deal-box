package xiaoqiangZzz.api.dealBox.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.multipart.MultipartFile;
import xiaoqiangZzz.api.dealBox.entity.Chat;
import xiaoqiangZzz.api.dealBox.entity.User;
import xiaoqiangZzz.api.dealBox.repository.UserRepository;
import xiaoqiangZzz.api.dealBox.service.UserService;
import org.springframework.web.bind.annotation.*;
import xiaoqiangZzz.api.dealBox.vo.PasswordUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("user")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("login")
  @JsonView(LoginJsonView.class)
  public User loginByToken(@RequestBody User user, HttpServletResponse response) {
    return userService.login(user.getUsername(), user.getPassword(), response);
  }

  @GetMapping("currentLoginUser")
  @JsonView(GetCurrentLoginUserJsonView.class)
  public User getCurrentLoginUser() {
    return this.userService.getCurrentUser();
  }

  /**
   * 校验密码是否正确接口
   * @param user user
   * @return boolean
   */
  @PostMapping("checkPasswordIsRight")
  public boolean checkPasswordIsRight(@RequestBody User user) {
    return this.userService.checkPasswordIsRight(user.getPassword());
  }

  @GetMapping("test")
  public String test() {
    return "success";
  }

  @PutMapping("/changeImage")
  public String changeImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
    User user = userService.getCurrentUser(request);
    return userService.changeImage(file, user);
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

  private interface GetCurrentLoginUserJsonView extends User.ChatsJsonView {
  }

  private interface LoginJsonView {
  }

}
