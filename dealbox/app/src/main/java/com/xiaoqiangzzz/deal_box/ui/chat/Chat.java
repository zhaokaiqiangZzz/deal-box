package com.xiaoqiangzzz.deal_box.ui.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.xiaoqiangzzz.deal_box.R;

public class Chat extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        TextView textView = (TextView)findViewById(R.id.test_testView);


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        textView.setText(id);
    }
}
