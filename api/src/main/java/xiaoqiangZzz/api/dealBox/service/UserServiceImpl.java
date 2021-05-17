package xiaoqiangZzz.api.dealBox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import xiaoqiangZzz.api.dealBox.NotAuthenticationException;
import xiaoqiangZzz.api.dealBox.entity.User;
import xiaoqiangZzz.api.dealBox.repository.UserRepository;
import com.mengyunzhi.core.exception.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

@Service
public class UserServiceImpl implements UserService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final UserRepository userRepository;

  @Autowired
  HttpServletRequest request;

  @Autowired
  CommonService commonService;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public boolean checkPasswordIsRight(String password) {
    User currentLoginUser = this.getCurrentUser();
    return currentLoginUser.getPassword().equals(password);
  }

  @Override
  public void updatePassword(String password, String newPassword) throws ValidationException {
    if (!this.checkPasswordIsRight(password)) {
      throw new ValidationException("旧密码不正确");
    }
    User currentUser = this.getCurrentUser();
    currentUser.setPassword(newPassword);
    this.userRepository.save(currentUser);
  }

  @Override
  public User login(String username, String password, HttpServletResponse response) {
    // 进行用户名密码验证
    User user = userRepository.findByUsername(username);
    if (user == null || !user.getPassword().equals(password)) {
      NotAuthenticationException exception = new NotAuthenticationException("用户名或密码错误");
      throw exception;
    }
    // 判断成功 返回头加入token
    String token = CommonService.createJwtToken(user.getId());
    response.setHeader(UserService.tokenHeader, token);
    return user;
  }

  @Override
  public String changeImage(MultipartFile file, User user) {
    String imageUrl = commonService.uploadImage(file);
    user.setImageUrl(imageUrl);
    userRepository.save(user);
    return imageUrl;
  }

  @Override
  public User getById(Long id) {
    return this.userRepository.findById(id).orElseThrow(() ->
        new ObjectNotFoundException("user未找到：" + id.toString()));
  }

  @Override
  public User getCurrentUser(HttpServletRequest request) {
    String token = request.getHeader(UserServiceImpl.tokenHeader);
    Long id = Long.valueOf(CommonService.parseJWT(token).getId());

    User user = userRepository.findById(id).get();
    if (user == null) {
      throw new NotAuthenticationException("请先登陆");
    }
    return user;
  }

  @Override
  public User getCurrentUser() {
    return getCurrentUser(request);
  }

  @Override
  public User register(User user, HttpServletResponse response) {
    // 保存用户
    user.setId(null);
    user = userRepository.save(user);
    // 生成token 返回token
    String token = CommonService.createJwtToken(user.getId());
    response.setHeader(tokenHeader, token);
    return user;
  }
}
