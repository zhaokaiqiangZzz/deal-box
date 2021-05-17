package com.xiaoqiangzzz.deal_box.ui.issue_goods;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.xiaoqiangzzz.deal_box.MainActivity;
import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_box.entity.Attachment;
import com.xiaoqiangzzz.deal_box.entity.Goods;
import com.xiaoqiangzzz.deal_box.entity.User;
import com.xiaoqiangzzz.deal_box.service.BaseHttpService;
import com.xiaoqiangzzz.deal_box.service.GoodsService;
import com.xiaoqiangzzz.deal_box.service.UserService;
import com.xiaoqiangzzz.deal_box.ui.auth.LoginActivity;

import java.io.File;
import java.util.ArrayList;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class IssueGoodsActivity extends Activity {
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private RecyclerView.Adapter issueGoodsImageList;
    private GoodsService goodsService = GoodsService.getInstance();
    private Goods goods = new Goods();

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 0;
    private static final int ImageCode = 1;
    ImageView issueGoodsImageView;
    private ArrayList<String> issueGoodsImageListData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.issue_goods);
        this.issueGoodsImageView = findViewById(R.id.issue_goods_image);

        // 设置物品浏览瀑布列表
        RecyclerView goodsListView = findViewById(R.id.issue_goods_image_list);
//        goodsListView.setHasFixedSize(true);
        this.staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
        goodsListView.setLayoutManager(staggeredGridLayoutManager);
        // 设置adapter
        this.issueGoodsImageListData = getData(this.goods);
        issueGoodsImageList = new IssueGoodsImageListAdapter(this.issueGoodsImageListData);
        ((IssueGoodsImageListAdapter) this.issueGoodsImageList).setOnItemClickListener(new IssueGoodsImageListAdapter.OnItemClickListener() {

            /**
             * 设置发布图片点击方法
             * @param view view
             * @param position position
             */
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(IssueGoodsActivity.this, Goods.class);
//                intent.putExtra("id", IssueGoodsActivity.this.goodsListData.get(position));
//                startActivity(intent);
            }
        });

        ((IssueGoodsImageListAdapter) this.issueGoodsImageList).setOnAddItemClickListener(new IssueGoodsImageListAdapter.OnAddItemClickListener() {
            /**
             * 设置点击增加图片按钮方法
             * @param view
             * @param position
             */
            @Override
            public void onClickAddImage(View view, int position) {
                // 判断是否有相册权限
                if (ContextCompat.checkSelfPermission(IssueGoodsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(IssueGoodsActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_CODE);
                } else {
                    openCamera();
                }
//                Intent intent = new Intent(IssueGoodsActivity.this, Goods.class);
//                intent.putExtra("id", IssueGoodsActivity.this.goodsListData.get(position));
//                startActivity(intent);
            }
        });
        goodsListView.setAdapter(this.issueGoodsImageList);

        // 设置发布按钮方法
        Button button = (Button)findViewById(R.id.submit_goods_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 进行用户登陆
                final String name = ((TextInputEditText) findViewById(R.id.issue_goods_name_input)).getText().toString();
                final String description = ((EditText) findViewById(R.id.description_input)).getText().toString();
                final String price = ((TextInputEditText) findViewById(R.id.issue_goods_price_input)).getText().toString();
                goods.setName(name);
                goods.setDescription(description);
                goods.setPrice(Integer.parseInt(price));
                goodsService.add(new BaseHttpService.CallBack() {
                    @Override
                    public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                        // 登陆成功
                        if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {

                            // 进入主页面
                            Intent intent = new Intent(IssueGoodsActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // 登陆失败 提示错误
                            Snackbar.make(button, "用户名或密码错误!", Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }, goods);
            }
        });
    }

    private ArrayList<String> getData(Goods goods) {
        ArrayList<String> data = new ArrayList<>();
        for (Attachment attachment : goods.getAttachments()) {
            data.add(attachment.getUrl());
        }
        return data;
    }

    /**
     * 打开相册
     */
    private void openCamera() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ImageCode);
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
                    Snackbar.make(this.issueGoodsImageView, "请允许打开相册权限!", Snackbar.LENGTH_SHORT)
                            .show();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
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
                            goodsService.uploadImage(req, new BaseHttpService.CallBack() {
                                @Override
                                public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                                    goods.getAttachments().add((Attachment) result.getData());
                                    updateImage(goods);
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
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);

        return imagePath;
    }

    public void updateImage(Goods goods) {
        ((IssueGoodsImageListAdapter) this.issueGoodsImageList).updateData(this.getData(goods));
    }
}
