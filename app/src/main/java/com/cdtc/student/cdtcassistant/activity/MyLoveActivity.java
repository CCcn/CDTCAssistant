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
import android.widget.TextView;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.network.bean.MyLoveBean;

import java.util.ArrayList;
import java.util.List;

public class MyLoveActivity extends BaseTopActivity {



    private RecyclerView myLoveRecycler;

    private List<MyLoveBean> myLoves = new ArrayList<>();

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_love);

        initVariable();
        initView();
        showData();
    }


    private void showData() {
        myLoveRecycler.setLayoutManager(new LinearLayoutManager(activity));
        myLoveRecycler.addItemDecoration(new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL));
        myLoveRecycler.setAdapter(new MyLoveAdapter());
    }
    private void initVariable() {
        activity = this;

        for (int i = 0; i < 20; i++) {
            MyLoveBean myLoveBean = new MyLoveBean();
            myLoveBean.setTitle("我是标题：" + i);
            myLoves.add(myLoveBean);
        }
    }

    private void initView() {
        initTopBar("我的表白");
        setBtnTopRight1("添加");

        myLoveRecycler = getView(R.id.my_love_recycler);
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
        btnTopRight1.setOnClickListener( v -> {
            AddLoveActivity.startAction(activity);
        });
    }

    /**
     *
     * @param context 上下文
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, MyLoveActivity.class);
        context.startActivity(intent);
    }
}
