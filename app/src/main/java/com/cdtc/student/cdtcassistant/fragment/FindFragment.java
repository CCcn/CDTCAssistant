package com.cdtc.student.cdtcassistant.fragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.activity.FindDetailActivity;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.bean.FindBean;
import com.cdtc.student.cdtcassistant.network.request.BasePageRequest;
import com.cdtc.student.cdtcassistant.network.response.FindResponse;
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

public class FindFragment extends Fragment {

    private RecyclerView findRecycler;

    private Activity activity;

    private List<FindBean> finds = new ArrayList<>();

    /**
     * 下拉刷新
     */
    private MaterialRefreshLayout refreshLayout;

    private FindAdapter findAdapter;

    private static final String TAG = "FindFragment";

    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private boolean hasLoadAll = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_find_fragment, container, false);

        initVariable();

        initView(view);

        return view;
    }


    private void showRecycler() {
        if (finds == null) {
            initVariable();
        }
        if (findRecycler.getAdapter() == null) {
            findAdapter = new FindAdapter();
            findRecycler.setAdapter(findAdapter);
            return;
        }
        //刷新数据
        findAdapter.notifyDataSetChanged();
    }

    private void initVariable() {
        activity = getActivity();
        loadData();
    }

    /**
     * 初始化View
     *
     * @param view container
     */
    private void initView(View view) {
        findRecycler = view.findViewById(R.id.find_recycler);

        refreshLayout = view.findViewById(R.id.find_refresh);
        refreshLayout.setLoadMore(true);

        findRecycler.setLayoutManager(new LinearLayoutManager(activity));
        findRecycler.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));

        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            //加在中
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                pageNum = 1;
                if (!finds.isEmpty()) {
                    hasLoadAll = false;
                    finds.clear();
                }
                loadData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (hasLoadAll) {
                    T.showNoMoreData(activity);
                    refreshLayout.finishRefreshLoadMore();
                    return;
                }
                pageNum += 1;
                loadData();

            }
        });

        LoadDialogUtils.showDialogForLoading(activity);
    }

    private void loadData() {
        BasePageRequest pageRequest = new BasePageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);

        OkHttpUtil.doJsonPost(Api.FIND_ALL, new Gson().toJson(pageRequest), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 请求失败：" + e.getMessage());
                activity.runOnUiThread(() -> {
                    T.showError(activity);
                    refreshLayout.finishRefresh();
                    refreshLayout.finishRefreshLoadMore();
                    LoadDialogUtils.hide(activity);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();
                Log.i(TAG, "onResponse: " + responseString);
                activity.runOnUiThread(() -> {
                    FindResponse findResponse;

                    refreshLayout.finishRefreshLoadMore();
                    refreshLayout.finishRefresh();
                    LoadDialogUtils.hide(activity);
                    try {
                        findResponse = new Gson().fromJson(responseString, FindResponse.class);
                        if (findResponse.code == HttpConstant.OK) {
                            Log.i(TAG, "onResponse: 请求成功：" + findResponse.toString());
                            if (findResponse.getData().isEmpty()) {
                                T.showNoMoreData(activity);
                                hasLoadAll = true;
                                return;
                            }
                            hasLoadAll = false;
                            finds.addAll(findResponse.getData());
                            showRecycler();
                            LoadDialogUtils.hide(activity);
                            return;
                        }
                        Log.d(TAG, "onResponse: 响应异常 " + findResponse);
                        T.showShort(activity, findResponse.message);

                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: 请求失败：" + e.getMessage());
                        T.showError(activity);
                    }
                });
            }
        });
    }

    private class FindAdapter extends RecyclerView.Adapter<FindHolder> {

        @NonNull
        @Override
        public FindHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FindHolder(getLayoutInflater().inflate(R.layout.find_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull FindHolder holder, int position) {
            FindBean findBean = finds.get(position);
            holder.lostTitle.setText(findBean.getTitle());
            holder.lostPlace.setText(findBean.getPlace());
            holder.lostDate.setText(findBean.getDate());
            Glide.with(activity)
                    .load(Api.HOME + findBean.getImg())
                    .placeholder(R.drawable.holder)
                    .error(R.drawable.holder)
                    .into(holder.findImg);
        }

        @Override
        public int getItemCount() {
            return finds.size();
        }
    }

    private class FindHolder extends RecyclerView.ViewHolder {

        /**
         * 图片描述
         */
        private ImageView findImg;

        /**
         * 丢失地点
         */
        private TextView lostPlace;

        /**
         * 丢失时间
         */
        private TextView lostDate;

        /**
         * 标题
         */
        private TextView lostTitle;

        /**
         * 根布局，用于点击时间
         */
        private LinearLayout itemLayout;

        public FindHolder(View itemView) {
            super(itemView);

            initView(itemView);
        }

        private void initView(View view) {
            findImg = view.findViewById(R.id.find_img);
            lostTitle = view.findViewById(R.id.find_title);
            lostDate = view.findViewById(R.id.find_lost_date);
            lostPlace = view.findViewById(R.id.find_lost_place);
            itemLayout = view.findViewById(R.id.find_item_layout);

            itemLayout.setOnClickListener(v -> {
                FindBean findBean = finds.get(getAdapterPosition());
                FindDetailActivity.startAction(activity, findBean.getTitle(), findBean.getId());
            });
        }

    }
}
