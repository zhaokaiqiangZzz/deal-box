package com.xiaoqiangzzz.deal_box.ui.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.entity.Message;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Chat extends AppCompatActivity {
    private ChatAdapter chatAdapter;

    /**
     * 声明ListView
     */
    private ListView chatListView;

    private List<Message> messages = new ArrayList<Message>();

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    /**
                     * ListView条目控制在最后一行
                     */
                    chatListView.setSelection(messages.size());
                    break;

                default:
                    break;
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        /**
         * 虚拟4条发送方的消息
         */
        for (int i = 0; i <= 3; i++) {
            Message message = new Message();
            message.setMeSend(false);
            this.messages.add(message);
        }

        this.chatListView = findViewById(R.id.chat_message_list);
        Button btn_chat_message_send = (Button) findViewById(R.id.send_message_button);
        final EditText et_chat_message = (EditText) findViewById(R.id.message_input);

        chatAdapter = new ChatAdapter(this, messages);
        this.chatListView.setAdapter(chatAdapter);

        /**
         * 发送按钮的点击事件
         */
        btn_chat_message_send.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(et_chat_message.getText().toString())) {
                    Toast.makeText(Chat.this, "发送内容不能为空", 0).show();
                    return;
                }
                Message message = new Message();
                //代表自己发送
                message.setMeSend(true);
                //得到发送内容
                message.setChatMessage(et_chat_message.getText().toString());
                //加入集合
                messages.add(message);
                //清空输入框
                et_chat_message.setText("");
                //刷新ListView
                chatAdapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);
            }
        });
    }
}
