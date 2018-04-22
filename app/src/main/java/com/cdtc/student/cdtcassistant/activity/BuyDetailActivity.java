package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cdtc.student.cdtcassistant.R;


/**
 * 跳蚤市场详细展示页
 *
 * Created by pcc on 2018/4/22.
 *
 * @author pcc
 */
public class BuyDetailActivity extends BaseTopActivity {

    private String title;

    private Activity activity;

    /**
     * 从列表传递过来的标题
     */
    private static final String TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_detail);

        initVariable();
        initView();

    }

    private void initVariable() {
        activity = this;

        title = getIntent().getStringExtra(TITLE);

    }

    private void initView() {
        initTopBar(title);
    }

    public static void startAction(Context context, String title) {
        Intent intent = new Intent(context,BuyDetailActivity.class);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }
}
