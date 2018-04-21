package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdtc.student.cdtcassistant.R;


public class BaseTopActivity extends AppCompatActivity {

    /**
     * 顶部标题
     */
    protected TextView tvTopTitle;
    /**
     * 右边的按钮
     */
    protected Button btnTopRight1;

    /**
     * 顶部返回
     */
    protected LinearLayout llTopBack;
    protected Button btnTopRight4;
    protected Button btnTopRight2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_top);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);


    }

    /**
     * 右上角的按钮
     * @param title 文字
     * @param act activity
     */
    protected void setBtnTopRight1(String title,final Activity act) {
        btnTopRight1.setVisibility(View.VISIBLE);
        btnTopRight1.setText(title);
        btnTopRight1.setOnClickListener(v -> act.finish());
    }

    /**
     * 顶部标题栏
     * @param title 标题
     */
    protected void initTopBar(String title) {
        llTopBack = findViewById(R.id.llTopBack);
        tvTopTitle =  findViewById(R.id.tvTopTitle);
        btnTopRight4 =  findViewById(R.id.btnTopRight4);
        btnTopRight1 =  findViewById(R.id.btnTopRight1);
        btnTopRight2 = findViewById(R.id.btnTopRight2);

        tvTopTitle.setText(title);

        llTopBack.setOnClickListener(v -> {
            try {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(BaseTopActivity.this
                                        .getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
            }

            finish();
        });
    }


    /**
     * 绑定组件
     * @param id 组件id
     * @param <T> View及View的子类
     * @return 组件的实例
     */
    public <T extends View> T getView(int id) {
        View view = findViewById(id);
        return (T) view;
    }
}
