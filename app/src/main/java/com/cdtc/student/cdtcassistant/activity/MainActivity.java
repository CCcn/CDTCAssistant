package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
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

import com.bumptech.glide.Glide;
import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.fragment.IndexFragment;
import com.cdtc.student.cdtcassistant.fragment.SecondFragment;
import com.cdtc.student.cdtcassistant.util.T;
import com.hjm.bottomtabbar.BottomTabBar;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


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
     * 底部切换按钮
     */
    private BottomTabBar mBottomTabBar;

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
    }

    private void initView() {
        navigationView  = findViewById(R.id.nav_view);

        fab =  findViewById(R.id.fab);

        fab.setVisibility(View.INVISIBLE);
        //设置Navigation里面的组件要这样获取
        View headerView= navigationView.getHeaderView(0);
        headImage = headerView.findViewById(R.id.head_image);

        mBottomTabBar = findViewById(R.id.bottom_tab_bar);
        mBottomTabBar.init(getSupportFragmentManager())
                .setImgSize(64,64)
                .setFontSize(8)
                .setTabPadding(20,6,20)
                .setChangeColor(Color.parseColor("#ffb900"),Color.DKGRAY)
                .addTabItem("首页", R.mipmap.ic_launcher, IndexFragment.class)
                .addTabItem("我的", R.mipmap.ic_launcher_round, SecondFragment.class)
                .setTabBarBackgroundColor(Color.parseColor("#f9feff"))
                .isShowDivider(true);

        /**
         * 当table改变时会调用此接口
         */
        mBottomTabBar.setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
            @Override
            public void onTabChange(int position, String name, View view) {
                //第二个页面，显示fab
                if (position == 1 ) {
                    fab.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.GONE);
                }

            }
        });

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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {

//        } else
        if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
