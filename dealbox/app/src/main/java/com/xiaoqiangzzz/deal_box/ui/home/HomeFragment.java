package com.xiaoqiangzzz.deal_box.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.xiaoqiangzzz.deal_box.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private RecyclerView.Adapter goodsListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 设置物品浏览瀑布列表
        RecyclerView goodsListView = view.findViewById(R.id.goods_cards_list);
        goodsListView.setHasFixedSize(true);
        this.staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        goodsListView.setLayoutManager(staggeredGridLayoutManager);
        // 设置adapter
        goodsListAdapter = new GoodsListAdapter(getData());
        goodsListView.setAdapter(this.goodsListAdapter);

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