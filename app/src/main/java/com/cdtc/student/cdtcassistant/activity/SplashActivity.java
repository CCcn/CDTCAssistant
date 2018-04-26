package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.StringConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.bean.BannerBean;
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
     *   这时候应该加在app初始化数据
     */
    private void loadInitData() {
        OkHttpUtil.doGet(Api.INIT, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: 网络异常" + e.getMessage());
                runOnUiThread( ()-> {
                    MainActivity.startAction(activity, StringConstant.FAILED);
                    Log.d(TAG, "onResponse: 数据失败，启动到main，网络错误" );
                    finish();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseSting = response.body().string();
                Log.i(TAG, "onResponse: " + responseSting);
                runOnUiThread(() -> {
                    BannerBean bannerBean  = null;

                    try {
                       bannerBean = new Gson().fromJson(responseSting, BannerBean.class);
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: " + e.getMessage());
                        T.showError(activity);
                        Singleton.getInstance(activity).setBannerBean(bannerBean);
                        MainActivity.startAction(activity, StringConstant.FAILED);
                        return;
                    }

                    Singleton.getInstance(activity).setBannerBean(bannerBean);
                    MainActivity.startAction(activity, StringConstant.OK);
                    Log.i(TAG, "onResponse: 数据加载完成，启动到main" );
                    finish();
                });
            }
        });
    }
}
