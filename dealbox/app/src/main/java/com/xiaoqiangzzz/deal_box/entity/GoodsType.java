package com.xiaoqiangzzz.deal_box.entity;

/**
 * 枚举类
 */
public enum GoodsType {
    MY_ISSUED("我发布的"),
    MY_BOLD("我卖出的"),
    MY_BOUGHT("我买到的");
    String description;
    GoodsType(String description) {
        this.description = description;
    }
    public String getDes() {
        return description;
    }
}
