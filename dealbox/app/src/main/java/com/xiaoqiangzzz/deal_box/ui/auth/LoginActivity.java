package com.xiaoqiangzzz.deal_box.ui.auth;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.xiaoqiangzzz.deal_box.MainActivity;
import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.entity.User;
import com.xiaoqiangzzz.deal_box.service.BaseHttpService;
import com.xiaoqiangzzz.deal_box.service.UserService;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button button = (Button)findViewById(R.id.login);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 进行用户登陆
                final String password = ((TextView) findViewById(R.id.password_text)).getText().toString();
                final String username = ((TextView) findViewById(R.id.username_text)).getText().toString();
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                userService.login(new BaseHttpService.CallBack() {
                    @Override
                    public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                        // 登陆成功
                        if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {
                            userService.currentUser.onNext((User) result.getData());
                            // 存储token 用户名 密码
                            String token = result.getResponse().header(UserService.TOKEN_HEADER);
                            SharedPreferences.Editor edit = LoginActivity.this.getSharedPreferences("user_message", MODE_PRIVATE).edit();
                            BaseHttpService.setToken(token);
                            edit.putString("token", token);
                            edit.putString("username", username);
                            edit.putString("password", password);
                            edit.apply();
                            // 进入主页面
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // 登陆失败 提示错误
                            Snackbar.make(button, "用户名或密码错误!", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }, user);
            }
        });
    }
}
