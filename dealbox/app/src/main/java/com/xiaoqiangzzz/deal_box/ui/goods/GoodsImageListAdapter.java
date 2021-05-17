package com.xiaoqiangzzz.deal_box.ui.goods;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.entity.Attachment;
import com.xiaoqiangzzz.deal_box.service.BaseHttpService;
import com.xiaoqiangzzz.deal_box.service.DownloadImageTask;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class GoodsImageListAdapter extends RecyclerView.Adapter<GoodsImageListAdapter.ViewHolder>{
    private List<Attachment> mData;

    public GoodsImageListAdapter(List<Attachment> data) {
        this.mData = data;
    }

    public void updateData(List<Attachment> data) {
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
        // 设置图片
        if (mData.get(position).getUrl() != null && !mData.get(position).getUrl().equals("")) {
            String urlString = BaseHttpService.BASE_URL + mData.get(position).getUrl();
            new DownloadImageTask(holder.imageView)
                    .execute(urlString);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.goods_image);
        }
    }
}
