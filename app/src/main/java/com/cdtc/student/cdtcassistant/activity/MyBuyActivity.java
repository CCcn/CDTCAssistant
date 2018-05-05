package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.bean.MyBuyBean;
import com.cdtc.student.cdtcassistant.network.bean.MyFindBean;
import com.cdtc.student.cdtcassistant.network.request.BuyUsersPageRequest;
import com.cdtc.student.cdtcassistant.network.response.MyBuyBeanResponse;
import com.cdtc.student.cdtcassistant.util.LoadDialogUtils;
import com.cdtc.student.cdtcassistant.util.T;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyBuyActivity extends BaseTopActivity {

    private RecyclerView myBuyRecycler;

    private Integer userId;

    private List<MyBuyBean> myBuys = new ArrayList<>();

    private MyBuyAdapter myBuyAdapter;

    private MaterialRefreshLayout refreshLayout;

    private Activity activity;

    private static final String TAG = "MyBuyActivity";


    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private boolean hasLoadAll = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_buy);

        initVariable();

        initView();
    }

    private void initVariable() {
        activity = this;

        userId = Singleton.getInstance(activity).getUser().getId();

        loadData();
    }

    private void initView() {
        initTopBar("我的跳蚤");
        setBtnTopRight1("添加");
        myBuyRecycler = getView(R.id.my_buy_recycler);
        myBuyRecycler.setLayoutManager(new LinearLayoutManager(activity));
        myBuyRecycler.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));

        refreshLayout = getView(R.id.my_buy_refresh);
        refreshLayout.setLoadMore(true);

        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                pageNum = 1;
                if (!myBuys.isEmpty()) {
                    myBuys.clear();
                    hasLoadAll = false;
                }
                loadData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (hasLoadAll) {
                    refreshLayout.finishRefreshLoadMore();
                    T.showNoMoreData(activity);
                    return;
                }

                pageNum += 1;
                loadData();

            }
        });

        LoadDialogUtils.showDialogForLoading(activity);
    }


    private void loadData() {
        BuyUsersPageRequest pageRequest = new BuyUsersPageRequest();
        pageRequest.setUserId(userId);
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);

        OkHttpUtil.doJsonPost(Api.BUY_USER_ALL, new Gson().toJson(pageRequest), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: " + e.getMessage());

                runOnUiThread(() -> {
                    T.showError(activity);
                    refreshLayout.finishRefresh();
                    refreshLayout.finishRefreshLoadMore();
                    LoadDialogUtils.hide(activity);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();

                runOnUiThread(() -> {
                    MyBuyBeanResponse buyBeanResponse = null;
                    refreshLayout.finishRefreshLoadMore();
                    refreshLayout.finishRefresh();
                    LoadDialogUtils.hide(activity);

                    try {
                        buyBeanResponse = new Gson().fromJson(responseString, MyBuyBeanResponse.class);

                        if (buyBeanResponse.code == HttpConstant.OK) {
                            if (buyBeanResponse.getData().isEmpty()) {
                                hasLoadAll = true;
                                T.showNoMoreData(activity);
                                return;
                            }
                            hasLoadAll = false;
                            myBuys.addAll(buyBeanResponse.getData());
                            showData();
                            return;
                        }
                        T.showShort(activity, buyBeanResponse.message);

                        LoadDialogUtils.hide(activity);
                    } catch (Exception e) {
                        T.showDataError(activity);
                    }
                });
            }
        });

    }

    private void showData() {
        if (myBuyAdapter == null) {
            myBuyAdapter = new MyBuyAdapter();
            myBuyRecycler.setAdapter(myBuyAdapter);
            return;
        }
        myBuyAdapter.notifyDataSetChanged();
    }




    @Override
    protected void setBtnTopRight1(String title) {
        super.setBtnTopRight1(title);

        btnTopRight1.setOnClickListener(v -> {
            AddBuyActivity.startAction(activity);
        });
    }


    private class MyBuyAdapter extends RecyclerView.Adapter<MyBuyActivity.MyBuyViewHolder> {

        @NonNull
        @Override
        public MyBuyActivity.MyBuyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyBuyActivity.MyBuyViewHolder(getLayoutInflater().inflate(R.layout.my_buy_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyBuyActivity.MyBuyViewHolder holder, int position) {
            MyBuyBean myBuyBean = myBuys.get(position);
            holder.title.setText(myBuyBean.getTitle());
            holder.price.setText("¥ " + myBuyBean.getPrice());
            Glide.with(activity)
                    .load(Api.HOME + myBuyBean.getImg())
                    .placeholder(R.drawable.holder)
                    .error(R.drawable.holder)
                    .into(holder.img);
        }

        @Override
        public int getItemCount() {
            return myBuys.size();
        }
    }

    private class MyBuyViewHolder extends RecyclerView.ViewHolder {

        /**
         * 标题
         */
        private TextView title;

        /**
         * 图片
         */
        private ImageView img;

        /**
         * 价格
         */
        private TextView price;

        public MyBuyViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            img = view.findViewById(R.id.my_buy_item_img);
            title = view.findViewById(R.id.my_buy_item_title);
            price = view.findViewById(R.id.my_buy_item_price);
            img.setOnClickListener(v -> {
                T.showShort(activity, getAdapterPosition() + "点击了");
            });
        }

    }

    /**
     * @param context 上下文
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, MyBuyActivity.class);
        context.startActivity(intent);
    }
}
