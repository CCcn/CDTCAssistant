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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.network.bean.MyFindBean;
import com.cdtc.student.cdtcassistant.util.T;

import java.util.ArrayList;
import java.util.List;

public class MyFindActivity extends BaseTopActivity {

    private RecyclerView myFindRecycler;

    private Activity activity;

    private List<MyFindBean> myFinds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_find);

        initVariable();

        initView();

        showDate();
    }


    private void initVariable() {
        activity = this;

        for (int i = 0; i < 20; i++) {
            MyFindBean myFindBean = new MyFindBean();
            myFindBean.setTitle("我是标题 " + i);
            myFinds.add(myFindBean);
        }
    }


    private void initView() {
        initTopBar("我的招领");
        setBtnTopRight1("添加");
        myFindRecycler = getView(R.id.my_find_recycler);
    }

    private void loadData() {

    }

    private void showDate() {
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
            MyFindBean myFindBean = myFinds.get(position);
            holder.title.setText(myFindBean.getTitle());
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

        public MyFindViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            img = view.findViewById(R.id.my_find_item_img);
            title = view.findViewById(R.id.my_find_item_title);
            description = view.findViewById(R.id.my_find_item_description);

            img.setOnClickListener(v -> {
                T.showShort(activity, getAdapterPosition() + "点击了");
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
