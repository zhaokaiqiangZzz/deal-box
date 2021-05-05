package com.xiaoqiangzzz.deal_box.ui.goods;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoqiangzzz.deal_box.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class GoodsImageListAdapter extends RecyclerView.Adapter<GoodsImageListAdapter.ViewHolder>{
    private ArrayList<String> mData;

    public GoodsImageListAdapter(ArrayList<String> data) {
        this.mData = data;
    }

    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public GoodsImageListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_image, parent, false);
        // 实例化viewHolder
        GoodsImageListAdapter.ViewHolder viewHolder = new GoodsImageListAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GoodsImageListAdapter.ViewHolder holder, final int position) {
        // 绑定数据
//        holder.mTv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        public ViewHolder(View itemView) {
            super(itemView);
//            mTv = (TextView) itemView.findViewById(R.id.friend_name);
        }
    }
}
