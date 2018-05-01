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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.bean.FindBean;
import com.cdtc.student.cdtcassistant.network.bean.MyFindBean;
import com.cdtc.student.cdtcassistant.network.response.FindResponse;
import com.cdtc.student.cdtcassistant.util.T;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 个人跳蚤管理
 * @author pcc
 */
public class MyFindActivity extends BaseTopActivity {

    private RecyclerView myFindRecycler;

    private Activity activity;

    private List<FindBean> myFinds;

    private Integer userId;

    private static final String TAG = "FeedbackActivity";

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
    }

    private void loadData() {
        OkHttpUtil.doGet(Api.FIND_USER_ALL + "?userId=" + userId, new Callback() {
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

                    FindResponse findResponse = null;

                    try {
                        findResponse = new Gson().fromJson(responseString, FindResponse.class);
                        if (findResponse.code == HttpConstant.OK) {
                            myFinds = findResponse.getData();
                            showDate();
                        } else {
                            T.showShort(activity,findResponse.message);
                        }
                    } catch (Exception e) {
                        T.showDataError(activity);
                    }

                });
            }
        });
    }

    private void showDate() {

        if (myFinds== null) {
            T.showShort(activity,"没有更多数据");
            return;
        }

        if (myFinds.isEmpty()) {
            T.showShort(activity,"没有数据");

            return;
        }
        myFindRecycler.setLayoutManager(new  LinearLayoutManager(activity));
        myFindRecycler.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
        myFindRecycler.setAdapter(new MyFindAdapter());
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

            img.setOnClickListener(v -> {
                T.showShort(activity, myFinds.get(getAdapterPosition()).getId() + "点击了");
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
