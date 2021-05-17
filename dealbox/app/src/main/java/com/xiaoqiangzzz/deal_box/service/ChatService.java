package com.xiaoqiangzzz.deal_box.service;

import com.xiaoqiangzzz.deal_box.entity.Chat;

public class ChatService {
    private static final String LOCAL_URL = "api/";
    public static ChatService chatService;
    BaseHttpService httpService = BaseHttpService.getInstance();

    public static ChatService getInstance() {
        if (chatService == null) {
            chatService = new ChatService();
        }
        return chatService;
    }

    public void getChatBySellerAndCurrentUser(BaseHttpService.CallBack callBack, Long id) {
        httpService.get(LOCAL_URL + "chat/getChatBySellerAndCurrentUser/" + id.toString(), callBack, Chat.class);
    }

    public void getAllByCurrentUser(BaseHttpService.CallBack callBack) {
        httpService.get(LOCAL_URL + "chat/getAllByCurrentUser", callBack, Chat[].class);
    }
}
