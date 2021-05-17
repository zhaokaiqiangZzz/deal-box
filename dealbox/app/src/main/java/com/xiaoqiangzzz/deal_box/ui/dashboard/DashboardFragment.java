package com.xiaoqiangzzz.deal_box.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.entity.Chat;
import com.xiaoqiangzzz.deal_box.entity.Goods;
import com.xiaoqiangzzz.deal_box.entity.User;
import com.xiaoqiangzzz.deal_box.service.BaseHttpService;
import com.xiaoqiangzzz.deal_box.service.ChatService;
import com.xiaoqiangzzz.deal_box.service.UserService;
import com.xiaoqiangzzz.deal_box.ui.chat.ChatActivity;
import com.xiaoqiangzzz.deal_box.ui.home.GoodsListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private UserService userService = UserService.getInstance();
    private ChatService chatService = ChatService.getInstance();
    private RecyclerView.LayoutManager chatListLayoutManager;

    private RecyclerView.Adapter chatListAdapter;

    private List<Chat> chats;
    private User currentUser;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView chatListView = view.findViewById(R.id.chat_list);
        chatListView.setHasFixedSize(true);
        chatListLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        chatListView.setLayoutManager(chatListLayoutManager);

        this.chatListAdapter = new ChatListAdapter(this.chats);
        ((ChatListAdapter) this.chatListAdapter).setOnItemClickListener(new ChatListAdapter.OnItemClickListener() {

            /**
             * 设置点击条目触发方法
             * @param view view
             * @param position position
             */
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                Long id = chats.get(position).getUsers().get(0).getId().equals(currentUser.getId()) ?
                        chats.get(position).getUsers().get(1).getId() : chats.get(position).getUsers().get(0).getId();
                intent.putExtra("id", id.toString());
                startActivity(intent);
            }
        });

        userService.getCurrentUser(new BaseHttpService.CallBack() {
            @Override
            public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                User user = (User) result.getData();

                currentUser = user;
            }
        });
        chatService.getAllByCurrentUser(new BaseHttpService.CallBack() {
            @Override
            public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                chats = new ArrayList<>(Arrays.asList((Chat[]) result.getData()));
                ((ChatListAdapter) chatListAdapter).updateData(chats);
            }
        });
        chatListView.setAdapter(this.chatListAdapter);
        return view;
    }

}