package com.xiaoqiangzzz.deal_box.service;

import com.xiaoqiangzzz.deal_box.entity.Attachment;
import com.xiaoqiangzzz.deal_box.entity.Goods;

import okhttp3.RequestBody;

public class GoodsService {

    private static final String LOCAL_URL = "api/";
    public static GoodsService goodsService;
    BaseHttpService httpService = BaseHttpService.getInstance();

    public static GoodsService getInstance() {
        if (goodsService == null) {
            goodsService = new GoodsService();
        }
        return goodsService;
    }

    public void uploadImage(RequestBody data, BaseHttpService.CallBack callBack) {
        httpService.putByForm( "attachment/uploadImage", data, callBack, Attachment.class);
    }
}
