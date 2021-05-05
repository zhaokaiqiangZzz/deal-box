package com.xiaoqiangzzz.deal_box.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.xiaoqiangzzz.deal_box.MainActivity;
import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.entity.GoodsType;
import com.xiaoqiangzzz.deal_box.ui.auth.Login;
import com.xiaoqiangzzz.deal_box.ui.goods_list.GoodsListActivity;
import com.xiaoqiangzzz.deal_box.ui.home.HomeFragment;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        // 我发布的按钮绑定事件
        ImageView myIssuedImage = (ImageView) view.findViewById(R.id.my_issued_image);
        myIssuedImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 给bnt1添加点击响应事件
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goodsType", GoodsType.MY_ISSUED);
                intent.putExtras(bundle);
                //启动
                startActivity(intent);
            }
        });

        // 我卖出的的按钮绑定事件
        ImageView mySoldImage = (ImageView) view.findViewById(R.id.my_sold_image);
        mySoldImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 给bnt1添加点击响应事件
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goodsType", GoodsType.MY_BOLD);
                intent.putExtras(bundle);
                //启动
                startActivity(intent);
            }
        });

        // 我买到的按钮绑定事件
        ImageView myBoughtImage = (ImageView) view.findViewById(R.id.my_bought_image);
        myBoughtImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 给bnt1添加点击响应事件
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goodsType", GoodsType.MY_BOUGHT);
                intent.putExtras(bundle);
                //启动
                startActivity(intent);
            }
        });
        return view;
    }
}