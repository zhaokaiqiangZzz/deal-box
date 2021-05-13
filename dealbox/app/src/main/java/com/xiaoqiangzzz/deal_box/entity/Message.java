package com.xiaoqiangzzz.deal_box.entity;

public class Message {
    /**
     * id
     */
    private Long id;
    /**
     * 聊天内容
     */
    private String chatMessage;
    /**
     *
     * @return 是否为本人发送
     */
    private boolean isMeSend;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public boolean isMeSend() {
        return isMeSend;
    }

    public void setMeSend(boolean meSend) {
        isMeSend = meSend;
    }
}
