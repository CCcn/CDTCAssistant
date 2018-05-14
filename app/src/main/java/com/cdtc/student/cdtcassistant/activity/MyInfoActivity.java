package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.StringConstant;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.bean.UserBean;

public class MyInfoActivity extends BaseTopActivity {

    /**
     * 修改密码
     */
    private LinearLayout changePassword;

    /**
     * 显示姓名
     */
    private TextView name;

    /**
     * 性别
     */
    private TextView gender;

    /**
     * 班级
     */
    private TextView className;

    /**
     * 学院
     */
    private TextView academy;

    /**
     * 年级
     */
    private TextView grade;

    /**
     * 退出登陆
     */
    private Button loginOut;


    private Activity activity;

    /**
     * 用户
     */
    private static UserBean user;

    /**
     * gender == 0表示为男生
     */
    private final int BOY = 0;

    /**
     * 发送本地广播
     */
    private LocalBroadcastManager localBroadcastManager;

    private static final String TAG = "MyInfoActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);


        initVariable();

        initView();
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        activity = this;


        localBroadcastManager = LocalBroadcastManager.getInstance(activity);

        user = Singleton.getInstance(activity).getUser();

        //双重验证，防止用户未登陆发生NPE
        if (user == null) {
            LoginActivity.startAction(activity);
        }
    }

    /**
     * 初始化View
     */
    private void initView() {


        name = getView(R.id.my_info_name);
        grade = getView(R.id.my_info_grade);
        gender = getView(R.id.my_info_gender);
        academy = getView(R.id.my_info_academy);
        className = getView(R.id.my_info_className);

        loginOut = getView(R.id.my_info_login_out);

        changePassword = getView(R.id.my_info_change_password);

        initTopBar("我的信息");

        name.setText(user.getName());
        gender.setText(user.getGender() == BOY ? "男" : "女");
        className.setText(user.getClassName());
        academy.setText(user.getAcademy());
        grade.setText(user.getGrade());

        changePassword.setOnClickListener(v -> {
            Log.i(TAG, "initView: 修改密码");
            ChangePasswordActivity.startAction(activity);
        });

        loginOut.setOnClickListener(v -> {
            Log.i(TAG, "initView: 退出登录 ");
            //清空数据，发送给MainActivity重置View中的数据
            Singleton.getInstance(activity).setUser(null);
            Intent intent = new Intent(StringConstant.LOGIN_ACTION);
            intent.putExtra(StringConstant.STATUS, StringConstant.LOGOUT);
            localBroadcastManager.sendBroadcast(intent);
            finish();
        });
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, MyInfoActivity.class);
        context.startActivity(intent);

    }
}
