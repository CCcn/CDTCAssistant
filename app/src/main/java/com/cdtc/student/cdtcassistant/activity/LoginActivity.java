package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.common.StringConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.response.UserResponse;
import com.cdtc.student.cdtcassistant.util.Md5Utils;
import com.cdtc.student.cdtcassistant.util.T;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * 登陆
 *
 * @author pcc
 */
public class LoginActivity extends BaseTopActivity implements View.OnClickListener{


    private EditText studentNumber;

    private EditText password;

    private Button login;

    private Activity activity;

    private LocalBroadcastManager localBroadcastManager;

    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initVariable();

        initView();
    }

    private void initVariable() {

        activity = this;

        studentNumber = getView(R.id.login_account);
        password = getView(R.id.login_password);
        login = getView(R.id.login_submit);

        login.setOnClickListener(this::onClick);

        localBroadcastManager = LocalBroadcastManager.getInstance(activity);
    }

    private void initView() {
        initTopBar("登陆");


    }

    @Override
    public void onClick(View v) {
        login.setEnabled(false);
        String inputAccount = studentNumber.getText().toString();
        String inputPassword = password.getText().toString();

        if (TextUtils.isEmpty(inputAccount) || TextUtils.isEmpty(inputPassword)) {
            T.showShort(activity,"请输入完整");
            return;
        }

        Log.i(TAG, "onClick: password " + inputPassword);

        //将密码加密
        inputPassword = Md5Utils.getMD5String(inputPassword);

        RequestBody requestBody = new FormBody.Builder()
                .add("studentNumber",inputAccount)
                .add("password",inputPassword)
                .build();


        OkHttpUtil.doPost(Api.LOGIN, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: 登陆请求出错" + e.getMessage() );
                runOnUiThread(() -> {
                    T.showError(activity);
                    login.setEnabled(true);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.i(TAG, "onResponse: 登陆请求响应：" + response.code());
                String responseSting = response.body().string();

                runOnUiThread(() -> {
                    UserResponse userResponse = null;

                    try {
                        userResponse = new Gson().fromJson(responseSting,UserResponse.class);
                        //登陆成功
                        if (userResponse.code == HttpConstant.OK) {
                            Log.i(TAG, "onResponse: 登陆成功" + userResponse);
                            Singleton.getInstance(activity).setUser(userResponse.getData());

                            //发广播
                            Intent intent = new Intent(StringConstant.LOGIN_ACTION);
                            intent.putExtra(StringConstant.STATUS,StringConstant.OK);
                            localBroadcastManager.sendBroadcast(intent);
                            finish();
                        } else {
                            Log.d(TAG, "onResponse: 响应代码：" + userResponse.code);
                            T.showShort(activity,userResponse.message);
                            login.setEnabled(true);
                            return;
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: 解析请求失败！");
                        T.showShort(activity,"遇到一些问题，请稍后再试");
                        login.setEnabled(true);
                        return;
                    }
                });
            }
        });
    }

    /**
     * 打开登陆页面
     * @param context
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
