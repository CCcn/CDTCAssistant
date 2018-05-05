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
import android.widget.TextView;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.bean.MyLoveBean;
import com.cdtc.student.cdtcassistant.network.response.LoveResponse;
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

public class MyLoveActivity extends BaseTopActivity {

    private RecyclerView myLoveRecycler;

    private List<MyLoveBean> myLoves;

    private MaterialRefreshLayout refreshLayout;

    private MyLoveAdapter myLoveAdapter;

    private Activity activity;

    private Integer userId;

    private static final String TAG = "MyLoveActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_love);

        initVariable();

        initView();

    }

    private void loadData() {
        OkHttpUtil.doGet(Api.LOVE_USER_ALL + "?userId=" + userId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                runOnUiThread(() -> {
                    T.showError(activity);
                    refreshLayout.finishRefresh();
                    LoadDialogUtils.hide(activity);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseString = response.body().string();
                runOnUiThread(() -> {
                    LoveResponse loveResponse = null;
                    LoadDialogUtils.hide(activity);
                    try {
                        loveResponse = new Gson().fromJson(responseString, LoveResponse.class);
                        if (loveResponse.code == HttpConstant.OK) {
                            myLoves = loveResponse.getData();
                            showData();
                            refreshLayout.finishRefresh();
                            return;
                        }
                        T.showShort(activity, loveResponse.message);
                        refreshLayout.finishRefresh();
                    } catch (Exception e) {
                        T.showDataError(activity);
                        refreshLayout.finishRefresh();
                    }
                });
            }
        });
    }

    private void showData() {
        if (myLoves == null || myLoves.isEmpty()) {
            T.showShort(activity, "没有更多数据");
            return;
        }

        if (myLoveAdapter == null) {
            myLoveAdapter = new MyLoveAdapter();
            myLoveRecycler.setAdapter(myLoveAdapter);
            return;
        }

        myLoveAdapter.notifyDataSetChanged();


    }

    private void initVariable() {
        activity = this;
        userId = Singleton.getInstance(activity).getUser().getId();

        loadData();
    }

    private void initView() {
        initTopBar("我的表白");
        setBtnTopRight1("添加");

        myLoveRecycler = getView(R.id.my_love_recycler);
        myLoveRecycler.setLayoutManager(new LinearLayoutManager(activity));
        myLoveRecycler.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));

        refreshLayout = getView(R.id.my_love_refresh);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                loadData();
            }
        });

        LoadDialogUtils.showDialogForLoading(activity);

    }

    private class MyLoveAdapter extends RecyclerView.Adapter<MyLoveHolder> {

        @NonNull
        @Override
        public MyLoveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyLoveHolder(getLayoutInflater().inflate(R.layout.my_love_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyLoveHolder holder, int position) {
            MyLoveBean myLoveBean = myLoves.get(position);
            holder.title.setText(myLoveBean.getTitle());
            holder.content.setText(myLoveBean.getContent());
        }

        @Override
        public int getItemCount() {
            return myLoves.size();
        }
    }

    private class MyLoveHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView content;

        public MyLoveHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            title = view.findViewById(R.id.my_love_title);
            content = view.findViewById(R.id.my_love_content);
        }
    }

    @Override
    protected void setBtnTopRight1(String title) {
        super.setBtnTopRight1(title);
        btnTopRight1.setOnClickListener(v -> {
            AddLoveActivity.startAction(activity);
        });
    }

    /**
     * @param context 上下文
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, MyLoveActivity.class);
        context.startActivity(intent);
    }
}
