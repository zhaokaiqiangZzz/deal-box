package com.xiaoqiangzzz.deal_box.ui.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.conf.Const;
import com.xiaoqiangzzz.deal_box.entity.Chat;
import com.xiaoqiangzzz.deal_box.entity.ChatMessage;
import com.xiaoqiangzzz.deal_box.entity.User;
import com.xiaoqiangzzz.deal_box.service.BaseHttpService;
import com.xiaoqiangzzz.deal_box.service.ChatService;
import com.xiaoqiangzzz.deal_box.service.UserService;
import com.xiaoqiangzzz.deal_box.ui.dashboard.ChatListAdapter;
import com.xiaoqiangzzz.deal_box.util.StompUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import androidx.appcompat.app.AppCompatActivity;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class ChatActivity extends AppCompatActivity {
    private ChatAdapter chatAdapter;
    private Chat chat;
    private ChatService chatService = ChatService.getInstance();
    private UserService userService = UserService.getInstance();
    private User currentUser;
    private ListView chatListView;
    private Button submitButton;
    private List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    /**
                     * ListView条目控制在最后一行
                     */
                    chatListView.setSelection(chatMessages.size());
                    break;

                default:
                    break;
            }
        };
    };

    private void init() {
        submitButton = findViewById(R.id.send_message_button);
        userService.currentUser.subscribe((User user) -> {
            currentUser = user;
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        Intent intent = getIntent();
        Long id = Long.valueOf(intent.getStringExtra("id"));

        init();
        StompClient stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, Const.address);
        Toast.makeText(this, "Start connecting to server", Toast.LENGTH_SHORT).show();
        stompClient.connect();
        StompUtils.lifecycle(stompClient);
        stompClient.topic(Const.chatResponse.replace(Const.placeholder, id.toString())).subscribe(stompMessage -> {
            JSONObject jsonObject = new JSONObject(stompMessage.getPayload());
            Log.i(Const.TAG, "Receive: " + jsonObject.toString());
            runOnUiThread(() -> {
                try {
                    receive(jsonObject.getString("response"), currentUser.getId(), id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        });

        this.chatListView = findViewById(R.id.chat_message_list);
        Button btn_chat_message_send = (Button) findViewById(R.id.send_message_button);
        final EditText et_chat_message = (EditText) findViewById(R.id.message_input);

        chatAdapter = new ChatAdapter(this, chatMessages, chat);
        this.chatListView.setAdapter(chatAdapter);

        chatService.getChatBySellerAndCurrentUser(new BaseHttpService.CallBack() {
            @Override
            public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                chat = (Chat) result.getData();
                ((ChatAdapter) chatAdapter).setChat(chat);
            }
        }, id);

        /**
         * 发送按钮的点击事件
         */
        btn_chat_message_send.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View arg0) {
                if (TextUtils.isEmpty(et_chat_message.getText().toString())) {
                    Toast.makeText(ChatActivity.this, "发送内容不能为空", 0).show();
                    return;
                }
                JSONObject jsonObject = new JSONObject();
                // 向后台发送数据
                try {
                    Long fromUserId = currentUser.getId();
                    jsonObject.put("userId", id.toString());
                    jsonObject.put("fromUserId", fromUserId.toString());
                    jsonObject.put("message", et_chat_message.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                stompClient.send(Const.chat, jsonObject.toString()).subscribe();

                ChatMessage chatMessage = new ChatMessage();
                //代表自己发送
                Long fromUserId = currentUser.getId();
                chatMessage.setFromUserId(fromUserId);
                chatMessage.setUserId(id);
                //得到发送内容
                chatMessage.setMessage(et_chat_message.getText().toString());
                //加入集合
                chatMessages.add(chatMessage);
                //清空输入框
                et_chat_message.setText("");
                //刷新ListView
                chatAdapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);
            }
        });
    }

    private void receive(String message, Long userId, Long fromUserId) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUserId(userId);
        chatMessage.setFromUserId(fromUserId);
        chatMessage.setMessage(message);
        chatMessages.add(chatMessage);
        chatAdapter.notifyDataSetChanged();
        handler.sendEmptyMessage(1);
    }
}
