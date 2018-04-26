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
import com.cdtc.student.cdtcassistant.network.bean.MyBuyBean;
import com.cdtc.student.cdtcassistant.network.bean.MyFindBean;
import com.cdtc.student.cdtcassistant.util.T;

import java.util.ArrayList;
import java.util.List;

public class MyBuyActivity extends BaseTopActivity {

    private RecyclerView myBuyRecycler;

    private List<MyBuyBean> myBuys = new ArrayList<>();
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_buy);

        initVariable();

        initView();

        showData();
    }



    private void loadData(){

    }

    private void showData() {
        myBuyRecycler.setLayoutManager(new LinearLayoutManager(activity));
        myBuyRecycler.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
        myBuyRecycler.setAdapter(new MyBuyAdapter());
    }

    private void initVariable() {
        activity = this;
        for (int i = 0 ; i < 20 ;i++) {
            MyBuyBean myBuyBean = new MyBuyBean();
            myBuyBean.setTitle("我是标题" + i);
            myBuys.add(myBuyBean);
        }
    }

    private void initView() {
        initTopBar("我的跳蚤");
        setBtnTopRight1("添加");
        myBuyRecycler = getView(R.id.my_buy_recycler);
    }



    @Override
    protected void setBtnTopRight1(String title) {
        super.setBtnTopRight1(title);

        btnTopRight1.setOnClickListener( v -> {
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
         * 描述
         */
        private TextView description;

        /**
         * 图片
         */
        private ImageView img;

        public MyBuyViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            img = view.findViewById(R.id.my_buy_item_img);
            title = view.findViewById(R.id.my_buy_item_title);
            description = view.findViewById(R.id.my_buy_item_description);

            img.setOnClickListener(v -> {
                T.showShort(activity, getAdapterPosition() + "点击了");
            });
        }

    }

    /**
     *
     * @param context 上下文
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, MyBuyActivity.class);
        context.startActivity(intent);
    }
}
