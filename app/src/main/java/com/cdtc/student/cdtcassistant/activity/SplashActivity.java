package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.common.StringConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.response.InitResponse;
import com.cdtc.student.cdtcassistant.util.T;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "SplashActivity";

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activity = this;

        loadInitData();
    }

    /**
     * 载入数据
     * 这时候应该加在app初始化数据
     */
    private void loadInitData() {
        OkHttpUtil.doGet(Api.INIT, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: 网络异常" + e.getMessage());
                runOnUiThread(() -> {
                    MainActivity.startAction(activity, StringConstant.FAILED);
                    finish();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseSting = response.body().string();
                Log.i(TAG, "onResponse: " + responseSting);
                runOnUiThread(() -> {
                    InitResponse initResponse = null;

                    try {
                        initResponse = new Gson().fromJson(responseSting, InitResponse.class);

                        if (initResponse.code == HttpConstant.OK) {

                            Singleton.getInstance(activity).setBannerBean(initResponse.getBanner());


                            Singleton.getInstance(activity).setBuys(initResponse.getBuys());
                            Singleton.getInstance(activity).setFinds(initResponse.getFinds());
                            Singleton.getInstance(activity).setLoves(initResponse.getLoves());
                            
                            MainActivity.startAction(activity, StringConstant.OK);
                            Log.i(TAG, "onResponse: 数据加载完成，启动到main");
                            finish();

                            return;
                        }

                        Log.d(TAG, "onResponse: " + initResponse.message);
                        MainActivity.startAction(activity, StringConstant.FAILED);
                        return;
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: " + e.getMessage());
                        MainActivity.startAction(activity, StringConstant.FAILED);
                    }
                });
            }
        });
    }
}
