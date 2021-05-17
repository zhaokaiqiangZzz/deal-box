package com.xiaoqiangzzz.deal_box.ui.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.entity.Chat;
import com.xiaoqiangzzz.deal_box.entity.Goods;
import com.xiaoqiangzzz.deal_box.service.BaseHttpService;
import com.xiaoqiangzzz.deal_box.service.GoodsService;
import com.xiaoqiangzzz.deal_box.ui.chat.ChatActivity;
import com.xiaoqiangzzz.deal_box.ui.home.GoodsListAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GoodsActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager chatListLayoutManager;
    private RecyclerView.Adapter goodsImageListAdapter;
    private GoodsService goodsService = GoodsService.getInstance();
    private Goods goods = new Goods();
    private TextView userPetNameText;
    private TextView nameText;
    private TextView priceText;
    private TextView descriptionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods);
        userPetNameText = findViewById(R.id.Goods_createUser_name);
        nameText = findViewById(R.id.goods_name_text);
        priceText = findViewById(R.id.goods_price_text);
        descriptionText = findViewById(R.id.goods_description_text);

        Intent intent = getIntent();
        final Long id = Long.valueOf(intent.getStringExtra("id"));

        RecyclerView goodsImageListView = findViewById(R.id.goods_image_list);
        goodsImageListView.setHasFixedSize(true);
        chatListLayoutManager = new LinearLayoutManager(this);
        goodsImageListView.setLayoutManager(chatListLayoutManager);

        this.goodsImageListAdapter = new GoodsImageListAdapter(this.goods.getAttachments());

        goodsService.getById(new BaseHttpService.CallBack() {
            @Override
            public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                goods = (Goods)result.getData();
                userPetNameText.setText(goods.getCreateUser().getPetName());
                nameText.setText(goods.getName());
                priceText.setText(String.valueOf(goods.getPrice()));
                descriptionText.setText(goods.getDescription());
                ((GoodsImageListAdapter) goodsImageListAdapter).updateData(goods.getAttachments());
            }
        }, id);

        goodsImageListView.setAdapter(this.goodsImageListAdapter);

        Button button = (Button)findViewById(R.id.goods_to_chat);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // 给bnt1添加点击响应事件
                Intent intent =new Intent(GoodsActivity.this, ChatActivity.class);
                intent.putExtra("id", goods.getCreateUser().getId().toString());
                //启动
                startActivity(intent);
            }
        });
    }
}
