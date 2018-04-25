package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cdtc.student.cdtcassistant.R;

public class MyBuyActivity extends BaseTopActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_buy);

        initVariable();

        initView();
    }

    private void initView() {
        initTopBar("我的跳蚤");
        setBtnTopRight1("添加");
    }

    private void initVariable() {
        activity = this;
    }

    @Override
    protected void setBtnTopRight1(String title) {
        super.setBtnTopRight1(title);

        btnTopRight1.setOnClickListener( v -> {
            AddBuyActivity.startAction(activity);
        });
    }

    /**
     *
     * @param context 上下文
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, MyBuyActivity.class);
        context.startActivity(intent);
    }
}
