package com.xiaoqiangzzz.deal_box.ui.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaoqiangzzz.deal_box.MainActivity;
import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.ui.auth.Login;
import com.xiaoqiangzzz.deal_box.ui.chat.Chat;
import com.xiaoqiangzzz.deal_box.ui.dashboard.DashboardFragment;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Goods extends AppCompatActivity {
    private RecyclerView.LayoutManager chatListLayoutManager;

    private RecyclerView.Adapter goodsImageListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");

        RecyclerView goodsImageListView = findViewById(R.id.goods_image_list);
        goodsImageListView.setHasFixedSize(true);
        chatListLayoutManager = new LinearLayoutManager(this);
        goodsImageListView.setLayoutManager(chatListLayoutManager);

        this.goodsImageListAdapter = new GoodsImageListAdapter(this.getData());
        goodsImageListView.setAdapter(this.goodsImageListAdapter);

        Button button = (Button)findViewById(R.id.goods_to_chat);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 给bnt1添加点击响应事件
                Intent intent =new Intent(Goods.this, Chat.class);
                intent.putExtra("id", id);
                //启动
                startActivity(intent);
            }
        });
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = " goods";
        for(int i = 0; i < 3; i++) {
            data.add(i + temp);
        }

        return data;
    }
}
