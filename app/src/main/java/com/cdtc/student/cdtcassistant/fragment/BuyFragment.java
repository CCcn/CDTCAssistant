package com.cdtc.student.cdtcassistant.fragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.activity.BuyDetailActivity;
import com.cdtc.student.cdtcassistant.network.bean.BuyBean;
import com.cdtc.student.cdtcassistant.util.T;

import java.util.ArrayList;
import java.util.List;

public class BuyFragment extends Fragment {

    private RecyclerView buyRecycler;

    private Activity activity;

    private List<BuyBean> buys = new ArrayList<>();

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
        showRecycler();
        return view;
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        activity = getActivity();

        for (int i = 0 ; i < 20; i++) {
            BuyBean buyBean = new BuyBean();
            buyBean.setDescription("描述信息 " + i);
            buyBean.setPrice("¥ " + i + "元");
            buys.add(buyBean);
        }
    }

    /**
     * 初始化View
     * @param view Container
     */
    private void initView(View view) {
        buyRecycler = view.findViewById(R.id.buy_recycler);
    }

    private void showRecycler() {
        if (buys == null) {
            //做数据请求
            initVariable();
        }
        buyRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
//        buyRecycler.addItemDecoration(new DividerItemDecoration(activity, LinearLayout.VERTICAL));
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
            holder.description.setText(buy.getDescription());
            holder.price.setText(buy.getPrice());
        }

        @Override
        public int getItemCount() {
            return buys.size();
        }
    }


    private class BuyHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView description;
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
            description = itemView.findViewById(R.id.buy_description);
            price = itemView.findViewById(R.id.buy_price);

            //点击后应该跳转到展示页面，后期可以实现ViewPager
            img.setOnClickListener(v -> {
                T.showShort(activity, "点击了"+getAdapterPosition());
                BuyDetailActivity.startAction(activity,buys.get(getAdapterPosition()).getDescription());

            });
        }
    }

}
