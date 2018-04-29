package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.request.FeedbackRequest;
import com.cdtc.student.cdtcassistant.network.response.FeedbackResponse;
import com.cdtc.student.cdtcassistant.util.T;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BaseTopActivity {

    /**
     * 反馈内容
     */
    private EditText content;

    /**
     * 联系方式
     *   可选
     */
    private EditText contact;

    /**
     * 提交按钮
     */
    private Button submit;

    /**
     * 提示剩余多少字
     */
    private TextView count;

    /**
     * 最大输入长度
     */
    private final int MAX_LENGTH = 400;

    /**
     * 反馈最少输入限制
     */
    private final int MIN_CONTENT_INPUT = 10;

    private Activity activity;

    private static final String TAG = "FeedbackActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        initVariable();
        initView();
        initListener();
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        activity = this;
    }

    /**
     * 初始化组件
     */
    private void initView() {
        initTopBar("意见反馈");

        count = getView(R.id.feedback_count);
        submit = getView(R.id.feedback_submit);
        contact = getView(R.id.feedback_contact);
        content = getView(R.id.feedback_content);


    }

    /**
     * 初始化监听
     */
    private void initListener() {
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                count.setText("剩余字数：" + (MAX_LENGTH - s.length()));
            }
        });

        submit.setOnClickListener( v-> {
            String inputContent = content.getText().toString();
            String inputContact = contact.getText().toString();

            //有效反馈
            if (!TextUtils.isEmpty(inputContent) && inputContent.length() > MIN_CONTENT_INPUT) {
                if (TextUtils.isEmpty(inputContact)) {
                    inputContact = "";
                }

                FeedbackRequest feedbackBean = new FeedbackRequest();
                feedbackBean.setContact(inputContact);
                feedbackBean.setContent(inputContent);

                Log.i(TAG, "initListener: 提交反馈:" + feedbackBean.toString());



                OkHttpUtil.doJsonPost(Api.FEEDBACK,new Gson().toJson(feedbackBean) , new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                        runOnUiThread(() -> {
                            T.showError(activity);
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseString = response.body().string();
                        Log.i(TAG, "onResponse: " + responseString);
                        runOnUiThread(() -> {

                            FeedbackResponse feedbackResponse = null;
                            try{
                                feedbackResponse = new Gson().fromJson(responseString, FeedbackResponse.class);
                            }catch (Exception e) {
                                Log.d(TAG, "onResponse: 解析json失败" + e.getMessage());
                                return;
                            }

                            if (feedbackResponse != null && feedbackResponse.code == HttpConstant.OK) {
                                T.showShort(activity,"谢谢你的帮助，让我更快成长！");
                                finish();
                            } else {
                                T.showShort(activity, feedbackResponse.message);
                            }
                        });
                    }
                });

            } else {
                T.showShort(activity,"我们很重视你的意见，多输入一点哟");
            }

        });
    }

    /**
     * 打开反馈界面
     * @param context 上下文
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }
}
