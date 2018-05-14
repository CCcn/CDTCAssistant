package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.bean.BuyDetailBean;
import com.cdtc.student.cdtcassistant.network.bean.ContactBean;
import com.cdtc.student.cdtcassistant.network.bean.BuyDetail;
import com.cdtc.student.cdtcassistant.network.response.BuyDetailResponse;
import com.cdtc.student.cdtcassistant.util.LoadDialogUtils;
import com.cdtc.student.cdtcassistant.util.T;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 跳蚤市场详细展示页
 * 后期增加展示图片
 * <p>
 * Created by pcc on 2018/4/22.
 *
 * @author pcc
 */
public class BuyDetailActivity extends BaseTopActivity {

    /**
     * 商品id
     */
    private String goodsId;

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
     * 用于控制隐藏
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


    private LinearLayout imgLayout;


    private Activity activity;

    /**
     * 从列表传递过来的标题
     */
    private static final String TITLE = "title";

    /**
     * 从列表传递过来的商品id
     */
    private static final String GOODS_ID = "goods_id";

    private static final String TAG = "BuyDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_detail);

        initVariable();
        initView();

        loadData();
    }

    /**
     * 开始请求数据
     */
    private void loadData() {
        OkHttpUtil.doGet(Api.BUY_DETAIL + "?id=" + goodsId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 请求失败" + e.getMessage());
                runOnUiThread(() -> {
                    LoadDialogUtils.hide(activity);
                    T.showError(activity);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();
                runOnUiThread(() -> {
                    LoadDialogUtils.hide(activity);
                    BuyDetailResponse buyDetailResponse = null;
                    try {
                        buyDetailResponse = new Gson().fromJson(responseString, BuyDetailResponse.class);
                        if (buyDetailResponse.code == HttpConstant.OK) {
                            BuyDetail buyDetail = buyDetailResponse.getData();
                            Log.i(TAG, "onResponse: 响应成功：" + buyDetail);

                            showData(buyDetail.getBuyDetail(), buyDetail.getContacts(), buyDetail.getImgs());
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: " + e.getMessage());
                        T.showError(activity);
                    }
                });
            }
        });
    }

    /**
     * 展示数据
     *
     * @param buyDetail
     */
    private void showData(BuyDetailBean buyDetail, List<ContactBean> contacts, List<String> imgs) {
        name.setText(buyDetail.getName());
        price.setText("¥ " + buyDetail.getPrice());
        owner.setText(buyDetail.getOwner());
        description.setText(buyDetail.getDescription());
        for (int i = 0; i < contacts.size(); i++) {
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
        //clear linearlayout
        imgLayout.removeAllViews();
        /**
         * 有多张图片
         */
        if (imgs != null && !imgs.isEmpty()) {

            for (int i = 0; i < imgs.size(); i++) {
                ImageView imageView = new ImageView(activity);
                //设置图片宽高
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                //动态添加图片
                imgLayout.addView(imageView);
                Glide.with(activity)
                        .load(Api.HOME + imgs.get(i))
                        .placeholder(R.drawable.holder)
                        .error(R.drawable.holder)
                        .into(imageView);
            }
        } else {
            ImageView imageView = new ImageView(activity);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            //动态添加图片
            imgLayout.addView(imageView);
            Glide.with(activity)
                    .load(Api.HOME + buyDetail.getImg())
                    .placeholder(R.drawable.holder)
                    .error(R.drawable.holder)
                    .into(imageView);
        }
    }

    private void initVariable() {
        activity = this;

        title = getIntent().getStringExtra(TITLE);
        goodsId = getIntent().getStringExtra(GOODS_ID);
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

        imgLayout = getView(R.id.buy_detail_imgs);

        LoadDialogUtils.showDialogForLoading(activity);
    }

    /**
     * 跳转到此页面
     *
     * @param context 上下文
     * @param title   商品标题
     * @param goodsId 商品id
     */
    public static void startAction(Context context, String title, String goodsId) {
        Intent intent = new Intent(context, BuyDetailActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(GOODS_ID, goodsId);
        context.startActivity(intent);
    }
}
