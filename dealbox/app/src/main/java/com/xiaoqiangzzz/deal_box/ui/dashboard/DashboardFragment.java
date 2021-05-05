package com.xiaoqiangzzz.deal_box.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoqiangzzz.deal_box.MainActivity;
import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.ui.auth.Login;
import com.xiaoqiangzzz.deal_box.ui.chat.Chat;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private RecyclerView.LayoutManager chatListLayoutManager;

    private RecyclerView.Adapter chatListAdapter;

    private ArrayList<String> chatListData;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView chatListView = view.findViewById(R.id.chat_list);
        chatListView.setHasFixedSize(true);
        chatListLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        chatListView.setLayoutManager(chatListLayoutManager);

        this.chatListData = getData();
        this.chatListAdapter = new ChatListAdapter(this.chatListData);
        ((ChatListAdapter) this.chatListAdapter).setOnItemClickListener(new ChatListAdapter.OnItemClickListener() {

            /**
             * 设置点击条目触发方法
             * @param view view
             * @param position position
             */
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(DashboardFragment.this.getContext(),"这是条目"
//                        +DashboardFragment.this.chatListData.get(position),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), Chat.class);
                intent.putExtra("id", DashboardFragment.this.chatListData.get(position));
                startActivity(intent);
            }
        });
        chatListView.setAdapter(this.chatListAdapter);
        return view;
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " friend";
        for(int i = 0; i < 20; i++) {
            data.add(i + temp);
        }

        return data;
    }
}