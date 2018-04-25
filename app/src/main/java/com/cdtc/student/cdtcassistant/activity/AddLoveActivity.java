package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cdtc.student.cdtcassistant.R;

public class AddLoveActivity extends BaseTopActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_love);

        initVariable();
        initView();
    }

    private void initVariable() {
        activity = this;
    }

    private void initView() {
        initTopBar("添加");
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
