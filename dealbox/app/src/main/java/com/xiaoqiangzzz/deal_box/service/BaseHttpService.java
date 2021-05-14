package com.xiaoqiangzzz.deal_box.service;

import android.os.AsyncTask;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 主要的http请求服务
 */
public class BaseHttpService {

    public static BaseHttpService baseHttpService;

    public static String BASE_URL = "http://192.168.43.75:8002/";


    public static BaseHttpService getInstance() {
        if (baseHttpService == null) {
            baseHttpService = new BaseHttpService();
        }
        return baseHttpService;
    }

    public static String token = "";

    public static void setToken(String token) {
        BaseHttpService.token = token;
    }

    /**
     * 用于发起get请求
     *
     * @param url      请求的url
     * @param callBack 回调函数，将在子线程中发起请求，在主线程中执行回调函数
     * @param params   请求参数
     */
    public <T> void get(String url, CallBack callBack, Class<T> type, Pair<String, String>... params) {
        HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(BASE_URL + url).newBuilder();
        if (params != null) {
            for (Pair<String, String> param :
                    params) {
                httpUrlBuilder.addQueryParameter(param.first, param.second);
            }
        }
        Request request = new Request.Builder().url(httpUrlBuilder.build())
                .addHeader("Authorization", token).build();
        new HttpTask<T>(callBack, type).execute(request);
    }

    /**
     * 发起post请求
     *
     * @param url
     * @param data
     * @param callback
     * @param type
     * @param <T>
     */
    public <T> void post(String url, Object data, BaseHttpService.CallBack callback, Class<T> type) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(data));
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .post(body)
                .addHeader("Authorization", token)
                .build();
        new HttpTask<T>(callback, type).execute(request);
    }

    public <T> void putByForm(String url, RequestBody body, BaseHttpService.CallBack callback, Class<T> type) {
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .put(body)
                .addHeader("Authorization", token)
                .build();
        new HttpTask<T>(callback, type).execute(request);
    }

    public <T> void postByForm(String url, RequestBody body, BaseHttpService.CallBack callback, Class<T> type) {
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .post(body)
                .addHeader("Authorization", token)
                .build();
        new HttpTask<T>(callback, type).execute(request);
    }

    /**
     * 发送put请求
     *
     * @param url
     * @param data
     * @param callback
     * @param type
     * @param <T>
     */
    public <T> void put(String url, Object data, BaseHttpService.CallBack callback, Class<T> type) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(data));
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .put(body)
                .addHeader("Authorization", token)
                .build();
        new HttpTask<T>(callback, type).execute(request);
    }

    /**
     * 自定义异步任务 用于发送http请求
     * 在子线程中执行请求操作
     * 在主线程中调用回调函数 以便修改ui
     *
     * @param <E>
     */
    public class HttpTask<E> extends AsyncTask<Request, Void, HttpTask.CustomerResponse> {

        CallBack callBack;

        Class type; // 类型

        Gson gson;

        public HttpTask(CallBack callBack, Class type) {
            this.callBack = callBack;
            this.type = type;
            GsonBuilder builder = new GsonBuilder();

            // Register an adapter to manage the date types as long values
            builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            });
            gson = builder.create();
        }

        @Override
        protected CustomerResponse doInBackground(Request... requests) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build();
                Response response = okHttpClient.newCall(requests[0]).execute();
                // 在这里将返回流转化为需要的范型数据并返回
                CustomerResponse customerResponse = new CustomerResponse();
                customerResponse.response = response;
                if (assertSuccessResponse(response) && type != null) {
                    String body = response.body().string();
                    customerResponse.data = gson.fromJson(body, (Type) type);
                }
                return customerResponse;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(HttpTask.CustomerResponse response) {
            this.callBack.onSuccess(response);
        }


        public class CustomerResponse {
            Response response;
            E data;

            public Response getResponse() {
                return response;
            }

            public E getData() {
                return data;
            }
        }
    }

    /**
     * 请求回调接口
     */
    public interface CallBack {
        void onSuccess(HttpTask.CustomerResponse result);
    }

    /**
     * 判断请求是否成功
     *
     * @param response
     * @return
     */
    public static boolean assertSuccessResponse(Response response) {
        return response.code() >= 200 && response.code() < 300;
    }

    /**
     * 格式化日期输出
     * MM/dd/yyyy HH:mm:ss
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateFormat(Date date, String format) {
        SimpleDateFormat bjSdf = new SimpleDateFormat(format);     // 北京
        bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return bjSdf.format(date);
    }
}
