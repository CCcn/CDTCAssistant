package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.MessagePattern;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.bean.AddLoveBean;
import com.cdtc.student.cdtcassistant.network.request.AddLoveRequest;
import com.cdtc.student.cdtcassistant.network.response.AddLoveResponse;
import com.cdtc.student.cdtcassistant.util.T;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddLoveActivity extends BaseTopActivity {


    private EditText title;

    private EditText content;

    private Button submit;

    private Activity activity;

    private Integer userId;

    private static final String TAG = "AddLoveActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_love);

        initVariable();
        initView();
    }

    private void initVariable() {
        activity = this;
        userId = Singleton.getInstance(activity).getUser().getId();

    }

    private void initView() {
        initTopBar("添加");

        title = getView(R.id.add_love_title);
        content = getView(R.id.add_love_content);

        submit = getView(R.id.add_love_submit);

        submit.setOnClickListener(view -> {
            submit.setEnabled(false);

            String inputTitle = title.getText().toString();
            String inputContent = content.getText().toString();

            if (TextUtils.isEmpty(inputTitle) || TextUtils.isEmpty(inputContent)) {
                T.showShort(activity, "请输入完整信息");
                submit.setEnabled(true);
                return;
            }

            AddLoveBean addLoveBean = new AddLoveBean();
            addLoveBean.setUserId(userId);
            addLoveBean.setTitle(inputTitle);
            addLoveBean.setContent(inputContent);

            AddLoveRequest addLoveRequest = new AddLoveRequest();
            addLoveRequest.setLove(addLoveBean);

            OkHttpUtil.doJsonPost(Api.CREATE_LOVE, new Gson().toJson(addLoveRequest), new Callback() {
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

                  runOnUiThread(() -> {
                      AddLoveResponse addLoveResponse = null;
                      try {
                          addLoveResponse = new Gson().fromJson(responseString, AddLoveResponse.class);
                          if (addLoveResponse.code == HttpConstant.OK) {
                              T.showShort(activity, "提交成功");
                              Log.i(TAG, "onResponse: " + addLoveResponse.message);
                              finish();
                              return;
                          }
                          //出错了
                          T.showShort(activity, addLoveResponse.message);
                          submit.setEnabled(true);
                      } catch (Exception e) {
                          T.showDataError(activity);
                          submit.setEnabled(true);
                      }
                  });

                }
            });
        });

    }

    /**
     *
     * @param context 上下文
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, AddLoveActivity.class);
        context.startActivity(intent);
    }
}
