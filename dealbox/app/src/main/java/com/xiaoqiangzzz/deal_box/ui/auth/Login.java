package com.xiaoqiangzzz.deal_box.ui.auth;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xiaoqiangzzz.deal_box.MainActivity;
import com.xiaoqiangzzz.deal_box.R;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button button = (Button)findViewById(R.id.login);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 给bnt1添加点击响应事件
                Intent intent =new Intent(Login.this, MainActivity.class);
                //启动
                startActivity(intent);
            }
        });
    }
}
