package com.cdtc.student.cdtcassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cdtc.student.cdtcassistant.R;

public class AboutActivity extends BaseTopActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initTopBar("关于我们");
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }
}
