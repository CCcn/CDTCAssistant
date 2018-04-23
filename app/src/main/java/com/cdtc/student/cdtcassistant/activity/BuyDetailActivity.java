package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdtc.student.cdtcassistant.R;


/**
 * 跳蚤市场详细展示页
 *    后期增加展示图片
 *
 * Created by pcc on 2018/4/22.
 *
 * @author pcc
 */
public class BuyDetailActivity extends BaseTopActivity {

    /**
     * 标题
     */
    private String title;

    /**
     * 商品名称
     */
    private TextView name;

    /**
     * 价格
     */
    private TextView price;

    /**
     * 联系人
     */
    private TextView owner;

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

        qq = getView(R.id.buy_detail_contact_qq);
        tel = getView(R.id.buy_detail_contact_tel);
        name = getView(R.id.buy_detail_goods_name);
        price = getView(R.id.buy_detail_goods_price);
        owner = getView(R.id.buy_detail_goods_owner);
        weChat = getView(R.id.buy_detail_contact_wx);
        qqLayout = getView(R.id.buy_detail_layout_qq);
        telLayout = getView(R.id.buy_detail_layout_tel);
        weChatLayout = getView(R.id.buy_detail_layout_wx);
        description = getView(R.id.buy_detail_goods_description);
    }

    public static void startAction(Context context, String title) {
        Intent intent = new Intent(context,BuyDetailActivity.class);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }
}
