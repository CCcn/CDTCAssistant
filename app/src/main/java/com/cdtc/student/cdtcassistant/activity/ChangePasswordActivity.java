package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.request.ChangePasswordRequest;
import com.cdtc.student.cdtcassistant.network.response.BaseResponse;
import com.cdtc.student.cdtcassistant.util.LoadDialogUtils;
import com.cdtc.student.cdtcassistant.util.Md5Utils;
import com.cdtc.student.cdtcassistant.util.T;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChangePasswordActivity extends BaseTopActivity {

    /**
     * 原密码
     */
    private EditText oldPassword;

    /**
     * 新密码
     */
    private EditText newPassword;

    /**
     * 确认新密码
     */
    private EditText confirmNewPassword;

    /**
     * 提交请求
     */
    private Button submit;

    private Activity activity;

    /**
     * 密码最低位数
     */
    private final int MIN_PASSWORD_LENGTH = 6;

    private static final String TAG = "ChangePasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initVariable();
        initView();

    }

    private void initVariable() {
        activity = this;
    }

    private void initView() {
        newPassword = getView(R.id.change_password_new);
        oldPassword = getView(R.id.change_password_old_password);
        confirmNewPassword = getView(R.id.change_password_new_confirm);

        submit = getView(R.id.change_password_submit);

        initTopBar("修改密码");

        submit.setOnClickListener(v -> {
            submit.setEnabled(false);

            String inputOldPassword = oldPassword.getText().toString();
            String inputNewPassword = newPassword.getText().toString();
            String inputConfirmPassword = confirmNewPassword.getText().toString();

            //验证输入是否为空
            if (TextUtils.isEmpty(inputOldPassword) || TextUtils.isEmpty(inputNewPassword) || TextUtils.isEmpty(inputConfirmPassword)) {
                T.showShort(activity, "请出入完整");
                submit.setEnabled(true);
                return;
            }

            if (inputNewPassword.length() < MIN_PASSWORD_LENGTH) {
                T.showShort(activity, "密码最少六位");
                submit.setEnabled(true);
                return;
            }

            if (!inputNewPassword.equals(inputConfirmPassword)) {
                T.showShort(activity, "两次密码输入不一致");
                submit.setEnabled(true);
                return;
            }

            ChangePasswordRequest request = new ChangePasswordRequest();
            request.setStudentNumber(Singleton.getInstance(activity).getUser().getStudentNumber());
            request.setOldPassword(Md5Utils.getMD5String(inputOldPassword));
            request.setNewPassword(Md5Utils.getMD5String(inputNewPassword));

            LoadDialogUtils.showDialogForWaiting(activity);

            OkHttpUtil.doJsonPost(Api.UPDATE_PASSWORD, request, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i(TAG, "onFailure: 网络链接是被" + e.getMessage());
                    runOnUiThread(() -> {
                        LoadDialogUtils.hide(activity);
                        T.showError(activity);
                        submit.setEnabled(true);
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseString = response.body().string();
                    runOnUiThread(() -> {
                        LoadDialogUtils.hide(activity);
                        submit.setEnabled(true);
                        try {
                            BaseResponse baseResponse = new Gson().fromJson(responseString, BaseResponse.class);
                            if (baseResponse.code == HttpConstant.OK) {
                                T.showShort(activity, "修改密码成功");
                                finish();
                            } else {
                                T.showShort(activity, baseResponse.message);
                            }
                        } catch (Exception e) {
                            T.showError(activity);
                        }
                    });
                }
            });

            T.showShort(activity, "修改密码");
        });
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(intent);
    }
}
