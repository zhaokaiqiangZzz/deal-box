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
        httpService.putByForm( LOCAL_URL + "attachment/uploadImage", data, callBack, Attachment.class);
    }

    public void add(BaseHttpService.CallBack callBack, Goods goods) {
        httpService.post(LOCAL_URL + "goods/add", goods, callBack, Goods.class);
    }

    public void getAll(BaseHttpService.CallBack callBack) {
        httpService.get(LOCAL_URL + "goods/getAll", callBack, Goods[].class);
    }

    public void getById(BaseHttpService.CallBack callBack, Long id) {
        httpService.get(LOCAL_URL + "goods/getById/" + id.toString(), callBack, Goods.class);
    }
}
