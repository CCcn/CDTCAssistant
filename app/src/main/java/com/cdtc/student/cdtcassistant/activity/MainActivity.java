package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.StringConstant;
import com.cdtc.student.cdtcassistant.fragment.BuyFragment;
import com.cdtc.student.cdtcassistant.fragment.FindFragment;
import com.cdtc.student.cdtcassistant.fragment.IndexFragment;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.bean.UserBean;
import com.cdtc.student.cdtcassistant.util.T;
import com.hjm.bottomtabbar.BottomTabBar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    /**
     * 底部切换按钮
     */
    private BottomTabBar mBottomTabBar;

    /**
     * 左侧的头像和文字
     */
    private NavigationView navigationView;

    /**
     * floatingBtn
     */
    FloatingActionButton fab;

    /**
     * navigation 中的组件
     */
    private ImageView headImage;

    private TextView userName;

    private TextView className;

    private Activity activity;

    /**
     * 按下返回键时间
     */
    private long pressBack;


    /**
     * 状态
     */
    private String status;

    /**
     * 从初始页启动
     * ok    ：数据请求成功
     * failed：数据请求失败，进行相应提示
     */
    private static final String STATUS = "status";

    /**
     * 本地广播，登录后启动收广播更新用户数据
     */
    private IntentFilter intentFilter;
    private BroadcastReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //输入法弹出时防止改变当前页面的布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        initVariable();

        registerLoginBroadcast();
        initView();
        initListener();

    }


    /**
     * 注册监听登录广播
     */
    private void registerLoginBroadcast() {

        intentFilter = new IntentFilter(StringConstant.LOGIN_ACTION);
        localReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String status = intent.getStringExtra(StringConstant.STATUS);
                Log.i(TAG, "onReceive: " + "收到本地广播:" + intent.getAction() + " data " + status);
                //登陆
                if (StringConstant.LOGIN.equals(status)) {
                    Log.i(TAG, "onReceive: 用户登陆"  );
                    updateUser();
                //退出登陆
                } else if (StringConstant.LOGOUT.equals(status)){
                    Log.i(TAG, "onReceive: 用户退出登陆");
                    initUser();
                }
            }
        };
        localBroadcastManager = LocalBroadcastManager.getInstance(activity);

        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    private void initListener() {

        navigationView.setNavigationItemSelectedListener(this);
        //todo 控制fab 显隐
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(view -> {
            //未登录
            if (Singleton.getInstance(activity).getUser() == null) {
                Log.i(TAG, "fab initListener: 用户未登陆");
                LoginActivity.startAction(activity);
                return;
            }

            MyInfoActivity.startAction(activity);
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
        });
    }

    private void initVariable() {
        activity = this;
        status = getIntent().getStringExtra(STATUS);

        //没有网络，给用户提示
        if (StringConstant.FAILED.equals(status)) {
            T.showError(activity);
        }
    }


    /**
     * 当用户登录后需要更新头像、用户名和密码
     */
    private void updateUser() {

        UserBean user = Singleton.getInstance(activity).getUser();

        if (user != null) {
            //设置Navigation里面的组件要这样获取
            View headerView = navigationView.getHeaderView(0);
            headImage = headerView.findViewById(R.id.head_image);
            userName = headerView.findViewById(R.id.head_name);
            className = headerView.findViewById(R.id.head_class);

            Glide.with(activity)
                    .load(Api.HOME + user.getImg())
                    .placeholder(R.drawable.holder)
                    .error(R.drawable.holder)
                    .dontAnimate()
                    .centerCrop()
                    .into(headImage);
            userName.setText(user.getName());
            className.setText(user.getClassName());
        }

    }

    /**
     * 退出登录后清除数据
     */
    private void initUser() {
        headImage.setBackgroundResource(R.mipmap.icon);
        userName.setText("");
        className.setText("");
    }

    private void initView() {
        navigationView = findViewById(R.id.nav_view);

        fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        //设置Navigation里面的组件要这样获取
        View headerView = navigationView.getHeaderView(0);
        headImage = headerView.findViewById(R.id.head_image);

        mBottomTabBar = findViewById(R.id.bottom_tab_bar);
        mBottomTabBar.init(getSupportFragmentManager())
                .setImgSize(64, 64)
                .setFontSize(8)
                .setTabPadding(20, 6, 20)
                .setChangeColor(Color.parseColor("#00CBAB"), Color.DKGRAY)

                .addTabItem("首页", R.drawable.icon_index, IndexFragment.class)
                .addTabItem("跳蚤", R.drawable.icon_buy, BuyFragment.class)
                .addTabItem("招领", R.drawable.icon_find, FindFragment.class)


                .setTabBarBackgroundColor(Color.parseColor("#f9feff"))
                .isShowDivider(true);

        /**
         * 当table改变时会调用此接口
         */
        mBottomTabBar.setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
            @Override
            public void onTabChange(int position, String name, View view) {
//                //第二个页面，显示fab
//                if (position == 0 ) {
//                    fab.setVisibility(View.VISIBLE);
//                } else {
//                    fab.setVisibility(View.GONE);
//                }

            }
        });


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        //未登录
        if (Singleton.getInstance(activity).getUser() == null) {
            LoginActivity.startAction(activity);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        if (id == R.id.nav_find) {
            MyFindActivity.startAction(activity);
        } else if (id == R.id.nav_buy) {
            MyBuyActivity.startAction(activity);
        } else if (id == R.id.nav_love) {
            MyLoveActivity.startAction(activity);
        } else if (id == R.id.nav_feedback) {
            FeedbackActivity.startAction(activity);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {
            AboutActivity.startAction(activity);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 从初始化页面加在进来
     *
     * @param context 上下文
     * @param status  网络请求状态
     */
    public static void startAction(Context context, String status) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(STATUS, status);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //防止无误触
            if ((System.currentTimeMillis() - pressBack) > 2000) {
                T.showShort(activity, "再按一次退出程序");
                pressBack = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }

    /**
     * 注销广播
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        localBroadcastManager.unregisterReceiver(localReceiver);
    }
}
