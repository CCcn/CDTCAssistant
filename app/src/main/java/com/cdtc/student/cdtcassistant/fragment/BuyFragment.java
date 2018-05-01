package com.cdtc.student.cdtcassistant.fragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.activity.BuyDetailActivity;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.bean.BuyBean;
import com.cdtc.student.cdtcassistant.network.response.BuyResponse;
import com.cdtc.student.cdtcassistant.util.T;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BuyFragment extends Fragment {

    private RecyclerView buyRecycler;

    private Activity activity;

    private List<BuyBean> buys;

    private static final String TAG = "BuyFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_buy_fragment, container, false);

        initVariable();
        initView(view);
        loadData();
        return view;
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        activity = getActivity();
    }

    private void loadData() {
        OkHttpUtil.doGet(Api.BUY, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                activity.runOnUiThread(() -> {
                    T.showError(activity);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();
                Log.i(TAG, "onResponse: 响应：" + responseString);
                activity.runOnUiThread(() -> {
                    BuyResponse buyResponse = null;
                    try{
                        buyResponse = new Gson().fromJson(responseString, BuyResponse.class);
                    }catch (Exception e) {
                        Log.d(TAG, "onResponse: " + e.getMessage());
                        T.showError(activity);
                    }
                    if (buyResponse != null && buyResponse.code == HttpConstant.OK) {
                        Log.i(TAG, "onResponse: 请求成功" + buyResponse.getData());
                        buys = buyResponse.getData();
                        showRecycler();
                    } else {
                        T.showError(activity);
                    }

                });
            }
        });
    }

    /**
     * 初始化View
     * @param view Container
     */
    private void initView(View view) {
        buyRecycler = view.findViewById(R.id.buy_recycler);
    }

    private void showRecycler() {

        if (buys == null || buys.isEmpty()) {
            T.showShort(activity,"没有数据");
            return;
        }
        buyRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        buyRecycler.setAdapter(new BuyAdapter());
    }


    private class BuyAdapter extends RecyclerView.Adapter<BuyHolder> {

        @NonNull
        @Override
        public BuyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new BuyHolder(getLayoutInflater().inflate(R.layout.buy_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull BuyHolder holder, int position) {
            BuyBean buy = buys.get(position);
            holder.title.setText(buy.getTitle());
            holder.price.setText(buy.getPrice());
        }

        @Override
        public int getItemCount() {
            return buys.size();
        }
    }


    private class BuyHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView title;
        private TextView price;

        public BuyHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        /**
         * 初始化item中的view
         * @param itemView
         */
        private void initView(View itemView) {
            img = itemView.findViewById(R.id.buy_img);
            title = itemView.findViewById(R.id.buy_description);
            price = itemView.findViewById(R.id.buy_price);

            //点击后应该跳转到展示页面，后期可以实现ViewPager
            img.setOnClickListener(v -> {
               // T.showShort(activity, "点击了"+getAdapterPosition());
                BuyBean buyBean = buys.get(getAdapterPosition());
                BuyDetailActivity.startAction(activity, buyBean.getTitle(), buyBean.getId());
            });
        }
    }

}
