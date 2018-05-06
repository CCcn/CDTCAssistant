package com.cdtc.student.cdtcassistant.network;

import com.google.gson.Gson;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 *
 * okHttp工具类
 *
 * Created by pcc on 2018/4/18.
 *
 * @author pcc
 */
public class OkHttpUtil {
    /**
     * OkHttpClient
     */
    private static OkHttpClient client = new OkHttpClient();

    /**
     * json请求头
     */
    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    /**
     * 异步get请求
     * @param url 请求的地址
     * @param callback 回调接口
     */
    public static void doGet(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(callback);

    }

    /**
     * 异步post请求
     * @param url 请求的地址
     * @param requestBody 请求体
     * @param callback 回调接口
     */
    public static void doPost(String url, RequestBody requestBody, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 异步Json Post
     * @param url 请求的地址
     * @param json json字符串
     * @param callback 回调接口
     */
    public static void doJsonPost(String url, String json, Callback callback) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 异步Json Post请求
     * @param url 请求的地址
     * @param jsonObject json字符串
     * @param callback 回调接口
     */
    private static void doJsonPost(String url, Object jsonObject, Callback callback) {
        RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(jsonObject));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }


    /**
     * 获取文件MimeType
     *
     * @param filename
     * @return
     */
    private static String getMimeType(String filename) {
        FileNameMap filenameMap = URLConnection.getFileNameMap();
        String contentType = filenameMap.getContentTypeFor(filename);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    /**
     * 通过上传的文件的完整路径生成RequestBody
     * @param fileName 完整的文件路径
     * @return
     */
    private static RequestBody getRequestBody(String fileName) {

        String[] filePath = fileName.split("/");

        File file = new File(fileName);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("img", filePath[filePath.length-1],
                        RequestBody.create(MediaType.parse("image/png"), file));
        return builder.build();
    }

    /**
     * 获得Request实例
     * @param url 服务器接口
     * @param fileNames 完整的文件路径
     * @return
     */
    private static Request getRequest(String url, String fileNames) {
        Request.Builder builder = new Request.Builder();
        builder.url(url)
                .post(getRequestBody(fileNames));
        return builder.build();
    }

    /**
     * 偿还文件
     * @param url 服务器接口
     * @param fileName 文件全名
     * @param callback
     */
    public static void upLoadFile(String url,String fileName,Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(getRequest(url,fileName)) ;
        call.enqueue(callback);
    }

}
