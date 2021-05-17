package com.xiaoqiangzzz.deal_box.ui.notifications;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import com.google.android.material.snackbar.Snackbar;
import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.entity.GoodsType;
import com.xiaoqiangzzz.deal_box.entity.User;
import com.xiaoqiangzzz.deal_box.service.BaseHttpService;
import com.xiaoqiangzzz.deal_box.service.DownloadImageTask;
import com.xiaoqiangzzz.deal_box.service.UserService;
import com.xiaoqiangzzz.deal_box.ui.goods_list.GoodsListActivity;

import java.io.File;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private UserService userService = UserService.getInstance();
    User currentUser;
    CircleImageView personImage;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 0;
    private static final int ImageCode = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        personImage = (CircleImageView) view.findViewById(R.id.my_image);

        userService.currentUser.subscribe((User user) -> {
            currentUser = user;
        });

        // 设置头像
        if (currentUser.getImageUrl() != null ) {
            String urlString = BaseHttpService.BASE_URL + currentUser.getImageUrl();
            new DownloadImageTask(personImage)
                    .execute(urlString);
        }

        TextView textView = view.findViewById(R.id.my_name);
        textView.setText(currentUser.getPetName());

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

        // 修改头像
        view.findViewById(R.id.visitor_image_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断是否有相册权限
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_CODE);
                } else {
                    openCamera();
                }

            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Snackbar.make(personImage, "请允许打开相册权限!", Snackbar.LENGTH_SHORT)
                            .show();
                }
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case ImageCode:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = intent.getData();

                    String filePath = getPath(selectedImage);
                    String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                    if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
                        if (filePath != null) {
                            File file = new File(filePath);
                            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
                            final RequestBody req = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file))
                                    .build();
                            userService.uploadImage(req, new BaseHttpService.CallBack() {
                                @Override
                                public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                                    currentUser.setImageUrl((String) result.getData());
                                    updateMessage(currentUser);
                                    userService.currentUser.onNext(currentUser);
                                }
                            });


                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);

        return imagePath;
    }

    /**
     * 打开相册
     */
    private void openCamera() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }


    /**
     * 更新用户信息
     * @param user
     */
    public void updateMessage(User user) {
        if (user.getImageUrl() != null && !user.getImageUrl().equals("")) {
            String urlString = BaseHttpService.BASE_URL + user.getImageUrl();
            new DownloadImageTask(personImage)
                    .execute(urlString);
        }
    }
}