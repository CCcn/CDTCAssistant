package com.cdtc.student.cdtcassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cdtc.student.cdtcassistant.R;

public class AddBuyActivity extends BaseTopActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buy);

        initView();
    }
    private void initView() {
        initTopBar("添加");
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AddBuyActivity.class);
        context.startActivity(intent);
    }
}
