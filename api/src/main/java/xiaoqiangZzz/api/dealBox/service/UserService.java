package xiaoqiangZzz.api.dealBox.service;

import org.springframework.web.multipart.MultipartFile;
import xiaoqiangZzz.api.dealBox.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

public interface UserService {

  String tokenHeader = "Authorization";

  /**
   * 检验密码是否正确.
   * @param password 密码
   * @return boolean
   */
  boolean checkPasswordIsRight(String password);

  User getById(Long id);

  User getCurrentUser(HttpServletRequest request);

  User getCurrentUser();

  User register(User user, HttpServletResponse response);

  /**
   * 修改密码.
   * @param password 密码
   * @param newPassword 新密码
   */
  void updatePassword(String password, String newPassword) throws ValidationException;

  User login(String username, String password, HttpServletResponse response);

  String changeImage(MultipartFile file, User user);
}
