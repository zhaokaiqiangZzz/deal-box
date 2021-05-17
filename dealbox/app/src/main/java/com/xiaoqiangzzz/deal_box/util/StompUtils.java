package com.xiaoqiangzzz.deal_box.util;

import android.util.Log;

import ua.naiksoftware.stomp.StompClient;


public class StompUtils {
    @SuppressWarnings({"ResultOfMethodCallIgnored", "CheckResult"})
    public static void lifecycle(StompClient stompClient) {
        stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.d("xlui", "Stomp connection opened");
                    break;

                case ERROR:
                    Log.e("xlui", "Error", lifecycleEvent.getException());
                    break;

                case CLOSED:
                    Log.d("xlui", "Stomp connection closed");
                    break;
            }
        });
    }
}
