package com.cdtc.student.cdtcassistant.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cdtc.student.cdtcassistant.R;

public class AddFindActivity extends BaseTopActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_find);

        initView();
    }

    private void initView() {
        initTopBar("添加");
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, AddFindActivity.class);
        context.startActivity(intent);
    }
}
