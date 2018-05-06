package com.cdtc.student.cdtcassistant.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.bean.FindBean;
import com.cdtc.student.cdtcassistant.network.request.FindUsersPageRequest;
import com.cdtc.student.cdtcassistant.network.response.BaseResponse;
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

/**
 * 个人跳蚤管理
 *
 * @author pcc
 */
public class MyFindActivity extends BaseTopActivity {

    private RecyclerView myFindRecycler;

    private Activity activity;

    private MaterialRefreshLayout refreshLayout;

    private MyFindAdapter myFindAdapter;

    private List<FindBean> myFinds = new ArrayList<>();

    private Integer userId;

    private static final String TAG = "FeedbackActivity";

    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private boolean hasLoadAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_find);

        initVariable();

        initView();
    }


    private void initVariable() {
        activity = this;
        userId = Singleton.getInstance(activity).getUser().getId();

        //开始请求数据
        loadData();

    }


    private void initView() {
        initTopBar("我的招领");
        setBtnTopRight1("添加");

        myFindRecycler = getView(R.id.my_find_recycler);
        myFindRecycler.setLayoutManager(new LinearLayoutManager(activity));
        myFindRecycler.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));

        refreshLayout = getView(R.id.my_find_refresh);
        refreshLayout.setLoadMore(true);

        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                pageNum = 1;
                if (!myFinds.isEmpty()) {
                    myFinds.clear();
                    hasLoadAll = false;
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
        FindUsersPageRequest pageRequest = new FindUsersPageRequest();
        pageRequest.setUserId(userId);
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);

        OkHttpUtil.doJsonPost(Api.FIND_USER_ALL, new Gson().toJson(pageRequest), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
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

                    FindResponse findResponse = null;
                    LoadDialogUtils.hide(activity);
                    refreshLayout.finishRefreshLoadMore();
                    refreshLayout.finishRefresh();

                    try {
                        findResponse = new Gson().fromJson(responseString, FindResponse.class);
                        if (findResponse.code == HttpConstant.OK) {
                            if (findResponse.getData().isEmpty()) {
                                hasLoadAll = true;
                                T.showNoMoreData(activity);
                                return;
                            }
                            hasLoadAll = false;
                            myFinds.addAll(findResponse.getData());
                            showDate();
                            return;
                        }
                        T.showShort(activity, findResponse.message);

                    } catch (Exception e) {
                        T.showDataError(activity);
                    }

                });
            }
        });
    }

    private void showDate() {
        if (myFinds.isEmpty()) {
            T.showShort(activity, "没有数据");
            return;
        }

        if (myFindAdapter == null) {
            myFindAdapter = new MyFindAdapter();
            myFindRecycler.setAdapter(myFindAdapter);
            return;
        }
        myFindAdapter.notifyDataSetChanged();
    }


    @Override
    protected void setBtnTopRight1(String title) {
        super.setBtnTopRight1(title);
        btnTopRight1.setOnClickListener(v -> {
            AddFindActivity.startAction(activity);
        });
    }


    private class MyFindAdapter extends RecyclerView.Adapter<MyFindViewHolder> {

        @NonNull
        @Override
        public MyFindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyFindViewHolder(getLayoutInflater().inflate(R.layout.my_find_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyFindViewHolder holder, int position) {
            FindBean find = myFinds.get(position);
            holder.title.setText(find.getTitle());
            Glide.with(activity)
                    .load(Api.HOME + find.getImg())
                    .placeholder(R.drawable.holder)
                    .error(R.drawable.holder)
                    .into(holder.img);
            holder.description.setText(find.getDescription());
            holder.place.setText(find.getPlace());
            holder.date.setText(find.getDate());
        }

        @Override
        public int getItemCount() {
            return myFinds.size();
        }

        public void removeItem(int position) {
            myFinds.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, myFinds.size() - position);
        }

    }

    private class MyFindViewHolder extends RecyclerView.ViewHolder {

        /**
         * 标题
         */
        private TextView title;

        /**
         * 描述
         */
        private TextView description;

        /**
         * 图片
         */
        private ImageView img;

        private TextView date;

        private TextView place;

        private LinearLayout myFindRoot;

        public MyFindViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            img = view.findViewById(R.id.my_find_item_img);
            title = view.findViewById(R.id.my_find_item_title);
            description = view.findViewById(R.id.my_find_item_description);
            date = view.findViewById(R.id.my_find_item_date);
            place = view.findViewById(R.id.my_find_item_place);
            myFindRoot = view.findViewById(R.id.my_find_root);

            myFindRoot.setOnLongClickListener(v -> {
                Integer position = getAdapterPosition();

                String buyId = myFinds.get(position).getId();

                AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                        .setTitle("删除")
                        .setMessage("删除该记录？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OkHttpUtil.doGet(Api.FIND_DELETE + "?id=" + buyId, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        Log.d(TAG, "onFailure: " + e.getMessage());
                                        runOnUiThread(() -> {
                                            T.showError(activity);
                                        });
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        String responseString = response.body().string();
                                        runOnUiThread(() -> {
                                            try {
                                                BaseResponse baseResponse = new Gson().fromJson(responseString, BaseResponse.class);
                                                if (baseResponse.code == HttpConstant.OK) {
                                                    T.showShort(activity, "删除成功");
                                                    myFindAdapter.removeItem(position);
                                                    return;
                                                }
                                                T.showShort(activity, baseResponse.message);
                                            } catch (Exception e) {
                                                T.showError(activity);
                                            }
                                        });
                                    }
                                });
                            }
                        });
                builder.setPositiveButton("取消", null);
                builder.create().show();


                return false;
            });

        }

    }


    /**
     * @param context 上下文
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, MyFindActivity.class);
        context.startActivity(intent);
    }

}
