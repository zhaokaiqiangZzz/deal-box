package com.xiaoqiangzzz.deal_box.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.entity.Chat;
import com.xiaoqiangzzz.deal_box.entity.User;
import com.xiaoqiangzzz.deal_box.service.BaseHttpService;
import com.xiaoqiangzzz.deal_box.service.DownloadImageTask;
import com.xiaoqiangzzz.deal_box.service.UserService;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder>{

    private List<Chat> mData;
    private OnItemClickListener mOnItemClickListener;
    private UserService userService = UserService.getInstance();
    private User currentUser;

    //设置回调接口
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ChatListAdapter(List<Chat> data) {
        userService.currentUser.subscribe((User user) -> {
            this.currentUser = user;
        });
        this.mData = data;
    }

    public void updateData(List<Chat> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_card, parent, false);
        // 实例化viewHolder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // 绑定数据
        User user = mData.get(position).getUsers().get(0).getId().equals(this.currentUser.getId()) ?
                mData.get(position).getUsers().get(1) : mData.get(position).getUsers().get(0);
        holder.mTv.setText(user.getPetName());

        // 设置头像
        if (user.getImageUrl() != null) {
            String urlString = BaseHttpService.BASE_URL + user.getImageUrl();
            new DownloadImageTask(holder.personImage)
                    .execute(urlString);
        }

        //通过为条目设置点击事件触发回调
        if (mOnItemClickListener != null) {
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout itemLayout;
        TextView mTv;
        CircleImageView personImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.friend_name);
            itemLayout = (RelativeLayout) itemView.findViewById(R.id.chat_card_relative_layout);
            personImage = (CircleImageView) itemView.findViewById(R.id.chat_card_person_image);
        }
    }
}
