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
import com.xiaoqiangzzz.deal_box.entity.Goods;
import com.xiaoqiangzzz.deal_box.service.BaseHttpService;
import com.xiaoqiangzzz.deal_box.service.GoodsService;
import com.xiaoqiangzzz.deal_box.ui.goods.GoodsActivity;
import com.xiaoqiangzzz.deal_box.ui.issue_goods.IssueGoodsActivity;
import com.xiaoqiangzzz.deal_box.ui.issue_goods.IssueGoodsImageListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private RecyclerView.Adapter goodsListAdapter;
    private GoodsService goodsService = GoodsService.getInstance();
    private ArrayList<Goods> goods = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view.setPadding(0, 0, 0, 50);

        // 设置物品浏览瀑布列表
        RecyclerView goodsListView = view.findViewById(R.id.goods_cards_list);
        goodsListView.setHasFixedSize(true);
        this.staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        goodsListView.setLayoutManager(staggeredGridLayoutManager);
        // 设置adapter
        goodsListAdapter = new GoodsListAdapter(this.goods);
        goodsListView.setAdapter(this.goodsListAdapter);
        ((GoodsListAdapter) goodsListAdapter).setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {

            /**
             * 设置点击条目触发方法
             * @param view view
             * @param position position
             */
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), GoodsActivity.class);
                intent.putExtra("id", goods.get(position).getId().toString());
                startActivity(intent);
            }
        });

        goodsService.getAll(new BaseHttpService.CallBack() {
            @Override
            public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                goods = new ArrayList<>(Arrays.asList((Goods[]) result.getData()));
                ((GoodsListAdapter) goodsListAdapter).updateData(goods);
            }
        });

        // 设置发布物品按钮
        FloatingActionButton floatingActionButton = view.findViewById(R.id.issue_goods_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 给bnt1添加点击响应事件
                Intent intent = new Intent(getActivity(), IssueGoodsActivity.class);
                //启动
                startActivity(intent);
            }
        });
        return view;
    }
}