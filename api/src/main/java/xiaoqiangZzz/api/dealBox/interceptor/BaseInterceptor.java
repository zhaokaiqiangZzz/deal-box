package xiaoqiangZzz.api.dealBox.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import xiaoqiangZzz.api.dealBox.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BaseInterceptor implements HandlerInterceptor {

  private final UserService userService;

  public BaseInterceptor(UserService userService) {
    this.userService = userService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    // 从请求头获取token进行判断
    String token = request.getHeader("Authorization");
    if (token != null && !token.equals("")) {
      // 通过token获取登录用户
      this.userService.getCurrentUser(request);
      return true;  // 只有返回true才会继续向下执行，返回false取消当前请求.
    }
    return false;
  }
}
