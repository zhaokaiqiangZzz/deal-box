package com.xiaoqiangzzz.deal_box.ui.issue_goods;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.service.BaseHttpService;
import com.xiaoqiangzzz.deal_box.service.DownloadImageTask;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IssueGoodsImageListAdapter extends RecyclerView.Adapter<IssueGoodsImageListAdapter.ViewHolder>{
    /**
     * 展示数据
     */
    private ArrayList<String> mData;

    private IssueGoodsImageListAdapter.OnItemClickListener mOnItemClickListener;

    private IssueGoodsImageListAdapter.OnAddItemClickListener onAddItemClickListener;

    public void setOnItemClickListener(IssueGoodsImageListAdapter.OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnAddItemClickListener(OnAddItemClickListener onAddItemClickListener){
        this.onAddItemClickListener = onAddItemClickListener;
    }

    //设置回调接口
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    //设置增加图片按钮回调接口
    public interface OnAddItemClickListener{
        void onClickAddImage(View view, int position);
    }

    public IssueGoodsImageListAdapter(ArrayList<String> data) {
        this.mData = data;
        this.mData.add("add");
    }

    public void updateData(ArrayList<String> data) {
        this.mData = data;
        this.mData.add("add");
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 瀑布流样式外部设置spanCount为2，在这列设置两个不同的item type，以区分不同的布局
        return position % 2;
    }

    @Override
    public IssueGoodsImageListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_goods_image, parent, false);
        // 实例化viewholder
        IssueGoodsImageListAdapter.ViewHolder viewHolder = new IssueGoodsImageListAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IssueGoodsImageListAdapter.ViewHolder holder, final int position) {
        // 绑定数据
        if (mData.get(position) != null && !mData.get(position).equals("")) {
            String urlString = BaseHttpService.BASE_URL + mData.get(position);
            holder.imageView.setImageURI(Uri.parse(urlString));
        }
        //holder.mTv.setText(mData.get(position));
        if (position != this.mData.size() - 1) {
            holder.imageView.setImageResource(R.drawable.goods2);
            //通过为条目设置点击事件触发回调
            if (mOnItemClickListener != null) {
                holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(view, position);
                    }
                });
            }
        } else {
            holder.imageView.setImageResource(R.drawable.add);
            holder.imageView.setBackgroundColor(Color.argb(255, 238, 238, 238));
            holder.imageView.setPadding(130, 130, 130, 130);

            //通过为条目设置点击事件触发回调
            if (mOnItemClickListener != null) { 
                holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onAddItemClickListener.onClickAddImage(view, position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemLayout;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.issue_goods_image_layout);
            imageView = itemView.findViewById(R.id.issue_goods_image);
        }
    }

}
