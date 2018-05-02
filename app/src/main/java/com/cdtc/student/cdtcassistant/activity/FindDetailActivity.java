package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.bean.ContactBean;
import com.cdtc.student.cdtcassistant.network.bean.FindDetailBean;
import com.cdtc.student.cdtcassistant.network.response.FindDetailResponse;
import com.cdtc.student.cdtcassistant.util.T;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FindDetailActivity extends BaseTopActivity {

    /**
     * 丢失/拾得地点
     */
    private TextView lostPlace;

    /**
     * 丢失/拾得时间
     */
    private TextView lostDate;

    /**
     * 联系人
     */
    private TextView contactPerson;

    /**
     * 微信
     */
    private TextView weChat;

    /**
     * qq
     */
    private TextView qq;

    /**
     * 电话
     */
    private TextView tel;

    /**
     * 详细描述
     */
    private TextView description;

    /**
     * 微信
     *  用于控制隐藏
     */
    private LinearLayout weChatLayout;

    /**
     * qq
     */
    private LinearLayout qqLayout;

    /**
     * tel
     */
    private LinearLayout telLayout;

    private Activity activity;

    /**
     * 标题
     */
    private String title;

    /**
     * id
     */
    private String findId;

    public static final String TITLE = "title";
    public static final String FIND_ID = "find_id";

    private static final String TAG = "FindDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_detail);

        initVariable();

        initView();

        loadData();
    }

    private void initView() {
        initTopBar(title);

        qq = getView(R.id.find_detail_contact_qq);
        tel = getView(R.id.find_detail_contact_tel);
        weChat = getView(R.id.buy_detail_contact_wx);
        qqLayout = getView(R.id.buy_detail_layout_qq);
        lostDate = getView(R.id.find_detail_lost_date);
        telLayout = getView(R.id.buy_detail_layout_tel);
        lostPlace = getView(R.id.find_detail_lost_place);
        weChatLayout = getView(R.id.buy_detail_layout_wx);
        contactPerson = getView(R.id.find_detail_contact_person);
        description = getView(R.id.buy_detail_goods_description);
    }

    /**
     * 做网络请求
     */
    private void loadData() {
        OkHttpUtil.doGet(Api.FIND_USER_ALL + findId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                runOnUiThread(() -> {
                    T.showError(activity);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "onResponse: " +response.code());
                String responseString = response.body().string();

                runOnUiThread(() -> {
                    FindDetailResponse findDetailResponse = null;

                    try {
                        findDetailResponse = new Gson().fromJson(responseString, FindDetailResponse.class);
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: 响应失败：" + e.getMessage());
                        T.showError(activity);
                        return;
                    }

                    if (findDetailResponse.code == HttpConstant.OK) {
                        Log.i(TAG, "onResponse: 响应成功" + findDetailResponse.getFindDetail());
                        FindDetailBean findDetailBean = findDetailResponse.getFindDetail();
                        showData(findDetailBean);
                    } else {
                        Log.i(TAG, "onResponse: 响应出错" + findDetailResponse.message);
                        T.showShort(activity,findDetailResponse.message);
                    }

                });
            }
        });
    }

    /**
     * 请求成功后展示数据
     * @param findDetailBean 请求得到的数据
     */
    private void showData(FindDetailBean findDetailBean) {

        contactPerson.setText(findDetailBean.getContactPerson());
        lostPlace.setText(findDetailBean.getLostPlace());
        lostDate.setText(findDetailBean.getLostDate());
        description.setText(findDetailBean.getDescription());

        List<ContactBean> contacts = findDetailBean.getContacts();
        for (int i = 0 ; i < contacts.size(); i++) {
            ContactBean contact = contacts.get(i);
            if ("wx".equals(contact.getContactType())) {
                weChatLayout.setVisibility(View.VISIBLE);
                weChat.setText(contact.getNumber());
            } else if ("qq".equals(contact.getContactType())) {
                qqLayout.setVisibility(View.VISIBLE);
                qq.setText(contact.getNumber());
            } else {
                telLayout.setVisibility(View.VISIBLE);
                tel.setText(contact.getNumber());
            }
        }
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        activity = this;

        title = getIntent().getStringExtra(TITLE);
        findId = getIntent().getStringExtra(FIND_ID);
    }

    /**
     * 打开失物招领详细页面
     * @param context 上下文
     * @param title 标题
     * @param findId id
     */
    public static void startAction(Context context, String title, String findId) {
        Intent intent = new Intent(context, FindDetailActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(FIND_ID, findId);
        context.startActivity(intent);
    }
}
