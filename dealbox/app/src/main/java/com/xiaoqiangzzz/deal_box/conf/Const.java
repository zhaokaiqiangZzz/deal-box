package com.xiaoqiangzzz.deal_box.conf;

public class Const {
    public static final String TAG = "xlui";
    public static final String placeholder = "placeholder";

    /**
     * <code>im</code> in address is the endpoint configured in server.
     * If you are using AVD provided by Android Studio, you should uncomment the upper address.
     * If you are using Genymotion, nothing else to do.
     * If you are using your own phone, just change the server address and port.
     */
    public static final String address = "ws://192.168.3.19:8002/im/websocket";

    public static final String broadcast = "/broadcast";
    public static final String broadcastResponse = "/b";
    // replace {@code placeholder} with group id
    public static final String group = "/group/" + placeholder;
    public static final String groupResponse = "/g/" + placeholder;
    public static final String chat = "/chat";
    // replace {@code placeholder} with user id
    public static final String chatResponse = "/user/" + placeholder + "/msg";
}
