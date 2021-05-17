package com.xiaoqiangzzz.deal_box.ui.chat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.entity.Chat;
import com.xiaoqiangzzz.deal_box.entity.ChatMessage;
import com.xiaoqiangzzz.deal_box.entity.User;
import com.xiaoqiangzzz.deal_box.service.BaseHttpService;
import com.xiaoqiangzzz.deal_box.service.DownloadImageTask;
import com.xiaoqiangzzz.deal_box.service.UserService;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private List<ChatMessage> lists;
    private UserService userService = UserService.getInstance();
    private User currentUser;
    private Chat chat;

    public ChatAdapter(Context context, List<ChatMessage> lists, Chat chat) {
        super();
        userService.currentUser.subscribe((User user) -> {
            currentUser = user;
        });
        this.chat = chat;
        this.context = context;
        this.lists = lists;
    }

    public void setChat(Chat chat)
    {
        this.chat = chat;
        notifyDataSetChanged();
    }
    /**
     * 是否是自己发送的消息
     *
     * @author cyf
     *
     */
    public static interface IMsgViewType {
        int IMVT_COM_MSG = 0;// 收到对方的消息
        int IMVT_TO_MSG = 1;// 自己发送出去的消息
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return lists.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return lists.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    /**
     * 得到Item的类型，是对方发过来的消息，还是自己发送出去的
     */
    public int getItemViewType(int position) {
        ChatMessage entity = lists.get(position);
        if (entity.getUserId().equals(currentUser.getId())) {// 收到的消息
            return IMsgViewType.IMVT_COM_MSG;
        } else {// 自己发送的消息
            return IMsgViewType.IMVT_TO_MSG;
        }
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        HolderView holderView = null;
        ChatMessage entity = lists.get(arg0);
        boolean isMeSend = entity.getFromUserId().equals(currentUser.getId());
        if (holderView == null) {
            User user = chat.getUsers().get(0).getId().equals(currentUser.getId()) ? chat.getUsers().get(1) : chat.getUsers().get(0);
            boolean isRight;
            if (isMeSend) {
                arg1 = View.inflate(context, R.layout.chat_item_right,
                        null);
                isRight = true;
            } else {
                arg1 = View.inflate(context, R.layout.chat_item_left,
                        null);
                isRight = false;
            }
            holderView = new HolderView(currentUser, user, arg1, isRight);
            if (isMeSend) {
                holderView.tv_chat_me_message = (TextView) arg1
                        .findViewById(R.id.tv_chat_me_message);
                holderView.tv_chat_me_message.setText(entity.getMessage());
            } else {
                holderView.tv_chat_me_message = (TextView)  arg1.findViewById(R.id.chat_other_message);
                holderView.tv_chat_me_message.setText(entity.getMessage());
            }
            arg1.setTag(holderView);
        } else {
            holderView = (HolderView) arg1.getTag();
        }

        return arg1;
    }

    class HolderView {
        CircleImageView leftPersonImage;
        CircleImageView rightPersonImage;
        TextView tv_chat_me_message;

        HolderView(User currentUser, User user, View itemView, boolean isRight) {
            if (isRight == true) {
                rightPersonImage = itemView.findViewById(R.id.chat_item_right_image);
                // 设置头像
                if (currentUser.getImageUrl() != null ) {
                    String urlString = BaseHttpService.BASE_URL + currentUser.getImageUrl();
                    new DownloadImageTask(rightPersonImage)
                            .execute(urlString);
                }
            } else {
                leftPersonImage = itemView.findViewById(R.id.chat_item_left_image);
                // 设置头像
                if (currentUser.getImageUrl() != null ) {
                    String urlString = BaseHttpService.BASE_URL + user.getImageUrl();
                    new DownloadImageTask(leftPersonImage)
                            .execute(urlString);
                }
            }
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
