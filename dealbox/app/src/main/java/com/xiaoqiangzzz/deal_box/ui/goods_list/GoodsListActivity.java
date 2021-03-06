package com.xiaoqiangzzz.deal_box.ui.goods_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.entity.Goods;
import com.xiaoqiangzzz.deal_box.entity.GoodsType;
import com.xiaoqiangzzz.deal_box.ui.goods.GoodsActivity;
import com.xiaoqiangzzz.deal_box.ui.home.GoodsListAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class GoodsListActivity extends AppCompatActivity {
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private RecyclerView.Adapter goodsListAdapter;

    private ArrayList<Goods> goodsListData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        Bundle bundle = getIntent().getExtras();
        GoodsType goodsType = (GoodsType)bundle.getSerializable("goodsType");
        setTitle(goodsType.getDes());

        // 设置悬浮按钮隐藏
        FloatingActionButton floatingActionButton = findViewById(R.id.issue_goods_button);
        floatingActionButton.hide();

        // 设置物品浏览瀑布列表
        RecyclerView goodsListView = findViewById(R.id.goods_cards_list);
        goodsListView.setHasFixedSize(true);
        this.staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        goodsListView.setLayoutManager(staggeredGridLayoutManager);
        // 设置adapter
        goodsListAdapter = new GoodsListAdapter(this.goodsListData);
        ((GoodsListAdapter) this.goodsListAdapter).setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {

            /**
             * 设置点击条目触发方法
             * @param view view
             * @param position position
             */
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(DashboardFragment.this.getContext(),"这是条目"
//                        +DashboardFragment.this.chatListData.get(position),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(GoodsListActivity.this, GoodsActivity.class);
                intent.putExtra("id", GoodsListActivity.this.goodsListData.get(position).getId());
                startActivity(intent);
            }
        });
        goodsListView.setAdapter(this.goodsListAdapter);
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = "goods";
        for(int i = 0; i < 20; i++) {
            data.add(i + temp);
        }

        return data;
    }
}
