package com.xiaoqiangzzz.deal_box.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.ui.goods.GoodsActivity;
import com.xiaoqiangzzz.deal_box.ui.issue_goods.IssueGoodsActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private RecyclerView.Adapter goodsListAdapter;

    private ArrayList<String> goodsListData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view.setPadding(0,0,0, 50);

        // 设置物品浏览瀑布列表
        RecyclerView goodsListView = view.findViewById(R.id.goods_cards_list);
        goodsListView.setHasFixedSize(true);
        this.staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        goodsListView.setLayoutManager(staggeredGridLayoutManager);
        // 设置adapter
        this.goodsListData = getData();
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
                Intent intent = new Intent(getActivity(), GoodsActivity.class);
                intent.putExtra("id", HomeFragment.this.goodsListData.get(position));
                startActivity(intent);
            }
        });
        goodsListView.setAdapter(this.goodsListAdapter);

        // 设置发布物品按钮
        FloatingActionButton floatingActionButton = view.findViewById(R.id.issue_goods_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 给bnt1添加点击响应事件
                Intent intent =new Intent(getActivity(), IssueGoodsActivity.class);
                //启动
                startActivity(intent);
            }
        });
        return view;
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