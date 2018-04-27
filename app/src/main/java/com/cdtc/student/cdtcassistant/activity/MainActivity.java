package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.StringConstant;
import com.cdtc.student.cdtcassistant.fragment.BuyFragment;
import com.cdtc.student.cdtcassistant.fragment.FindFragment;
import com.cdtc.student.cdtcassistant.fragment.IndexFragment;
import com.cdtc.student.cdtcassistant.fragment.MineFragment;
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
     * navigation 中的图片
     */
    private ImageView headImage;


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
     *   ok    ：数据请求成功
     *   failed：数据请求失败，进行相应提示
     */
    private static final String STATUS = "status";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //输入法弹出时防止改变当前页面的布局
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        initVariable();
        initView();
        initListener();

    }

    private void initListener() {

        navigationView.setNavigationItemSelectedListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
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


    private void initView() {
        navigationView  = findViewById(R.id.nav_view);

        fab =  findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        //设置Navigation里面的组件要这样获取
        View headerView= navigationView.getHeaderView(0);
        headImage = headerView.findViewById(R.id.head_image);

        mBottomTabBar = findViewById(R.id.bottom_tab_bar);
        mBottomTabBar.init(getSupportFragmentManager())
                .setImgSize(64,64)
                .setFontSize(8)
                .setTabPadding(20,6,20)
                .setChangeColor(Color.parseColor("#00CBAB"),Color.DKGRAY)
                .addTabItem("首页", R.drawable.icon_index, IndexFragment.class)
                .addTabItem("跳蚤", R.drawable.icon_buy, BuyFragment.class)
                .addTabItem("招领", R.drawable.icon_find, FindFragment.class)
//                .addTabItem("我的", R.drawable.icon_mine, MineFragment.class)
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
     * @param context 上下文
     * @param status 网络请求状态
     */
    public static void startAction(Context context, String status) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(STATUS,status);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //防止无误触
            if ((System.currentTimeMillis() - pressBack) > 2000) {
                T.showShort(activity,"再按一次退出程序");
                pressBack = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }
}
