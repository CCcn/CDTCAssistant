package com.cdtc.student.cdtcassistant;

import android.app.Application;
import android.util.Log;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

public class UMApplication extends Application {

    private final String TAG = "UMApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        UMConfigure.init(this, "5b1dd41ff29d984395000033", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "6568e2978aa1ef0bdc17aa86a7d81c37");

        //log开关
        UMConfigure.setLogEnabled(true);
        //日志加密
        UMConfigure.setEncryptEnabled(true);

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                Log.d(TAG, "onSuccess: ");
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });


    }
}
