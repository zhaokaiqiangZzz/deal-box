package com.xiaoqiangzzz.deal_box.service;
import com.xiaoqiangzzz.deal_box.entity.User;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class UserService {
    private static final String LOCAL_URL = "api/";
    public static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public static final String TOKEN_HEADER = "Authorization";

    public BehaviorSubject<User> currentUser = BehaviorSubject.createDefault(new User());  // 当前登陆用户

    BaseHttpService httpService = BaseHttpService.getInstance();

    /**
     * 登陆
     *
     * @param callBack
     * @param user
     */
    public void login(BaseHttpService.CallBack callBack, User user) {
        httpService.post(LOCAL_URL + "user/login", user, callBack, User.class);
    }
}
