package com.cdtc.student.cdtcassistant.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.activity.BuyDetailActivity;
import com.cdtc.student.cdtcassistant.activity.FindDetailActivity;
import com.cdtc.student.cdtcassistant.activity.WebActivity;
import com.cdtc.student.cdtcassistant.common.HttpConstant;
import com.cdtc.student.cdtcassistant.network.Api;
import com.cdtc.student.cdtcassistant.network.OkHttpUtil;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.bean.BannerBean;
import com.cdtc.student.cdtcassistant.network.bean.BuyBean;
import com.cdtc.student.cdtcassistant.network.bean.FindBean;
import com.cdtc.student.cdtcassistant.network.bean.LoveBean;

import com.cdtc.student.cdtcassistant.network.response.InitResponse;
import com.cdtc.student.cdtcassistant.util.T;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.transformer.TransitionEffect;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 首页
 * <p>
 * Created by pcc on 2018/4/18.
 *
 * @author pcc
 */
public class IndexFragment extends Fragment implements BGABanner.Adapter<ImageView, String>, View.OnClickListener {

    /**
     * 首页轮播
     */
    private BGABanner bgaBanner;

    /**
     * 功能按钮
     */
    private LinearLayout eCardLayout;
    private LinearLayout libraryLayout;
    private LinearLayout electricityLayout;
    private LinearLayout moreLayout;

    private MaterialRefreshLayout refreshLayout;

    private RecyclerView loveRecycler;

    private List<LoveBean> loves = new ArrayList<>();

    private RecyclerView buyRecycler;

    private List<BuyBean> buys = new ArrayList<>();

    private RecyclerView findRecycler;

    private List<FindBean> finds = new ArrayList<>();

    private LoveAdapter loveAdapter;
    private FindAdapter findAdapter;
    private BuyAdapter buyAdapter;


    private Activity activity;

    /**
     * 轮播图数据
     */
    private BannerBean bannerBean;

    private static final String TAG = "IndexFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_index_fragment, container, false);

        initVariable();

        initView(view);

        loadBannerData(bgaBanner);

        return view;
    }

    /**
     * 初始化View
     *
     * @param view container
     */
    private void initView(View view) {
        bgaBanner = view.findViewById(R.id.index_fragment_banner);
        bgaBanner.setTransitionEffect(TransitionEffect.Default);
        bgaBanner.setAutoPlayAble(true);
        bgaBanner.setAdapter(IndexFragment.this);

        moreLayout = view.findViewById(R.id.index_more);
        eCardLayout = view.findViewById(R.id.index_e_card);
        libraryLayout = view.findViewById(R.id.index_library);
        electricityLayout = view.findViewById(R.id.index_electricity);

        loveRecycler = view.findViewById(R.id.index_love_recycler);
        buyRecycler = view.findViewById(R.id.index_buy_recycler);
        findRecycler = view.findViewById(R.id.index_find_recycler);


        LinearLayoutManager managerLove = new LinearLayoutManager(activity);
        managerLove.setOrientation(LinearLayoutManager.HORIZONTAL);
        loveRecycler.setLayoutManager(managerLove);

        LinearLayoutManager managerBuy = new LinearLayoutManager(activity);
        managerBuy.setOrientation(LinearLayoutManager.HORIZONTAL);
        buyRecycler.setLayoutManager(managerBuy);

        LinearLayoutManager managerFind = new LinearLayoutManager(activity);
        managerFind.setOrientation(LinearLayoutManager.HORIZONTAL);
        findRecycler.setLayoutManager(managerFind);

        refreshLayout = view.findViewById(R.id.index_refresh);

        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                loadInitData();
            }
        });

        moreLayout.setOnClickListener(this::onClick);
        eCardLayout.setOnClickListener(this::onClick);
        libraryLayout.setOnClickListener(this::onClick);
        electricityLayout.setOnClickListener(this::onClick);

        if (loves == null || buys == null || finds == null) {
            T.showError(activity);
            return;
        }

        showData();
    }

    private void loadInitData() {
        OkHttpUtil.doGet(Api.INIT, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: 网络异常" + e.getMessage());
                activity.runOnUiThread(() -> {
                    T.showError(activity);
                    refreshLayout.finishRefresh();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseSting = response.body().string();
                Log.i(TAG, "onResponse: " + responseSting);
                activity.runOnUiThread(() -> {
                    InitResponse initResponse = null;
                    try {
                        initResponse = new Gson().fromJson(responseSting, InitResponse.class);
                        if (initResponse.code == HttpConstant.OK) {
                            bannerBean = initResponse.getBanner();
                            buys = initResponse.getBuys();
                            finds = initResponse.getFinds();
                            loves = initResponse.getLoves();

                            showRefreshData();

                            refreshLayout.finishRefresh();
                            return;
                        }
                        T.showError(activity);
                        refreshLayout.finishRefresh();
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: " + e.getMessage());
                        T.showError(activity);
                        refreshLayout.finishRefresh();
                    }
                });
            }
        });
    }

    private void showRefreshData() {
        bgaBanner.setData(bannerBean.getImgs(), bannerBean.getTips());

        if (loveRecycler.getAdapter() == null || findRecycler.getAdapter() == null || buyRecycler.getAdapter() == null) {
            showData();
            return;
        }
        findAdapter.notifyDataSetChanged();
        loveAdapter.notifyDataSetChanged();
        buyAdapter.notifyDataSetChanged();
    }

    private void showData() {
        loveAdapter = new LoveAdapter();
        findAdapter = new FindAdapter();
        buyAdapter = new BuyAdapter();

        loveRecycler.setAdapter(loveAdapter);


        buyRecycler.setAdapter(buyAdapter);

        findRecycler.setAdapter(findAdapter);

    }

    private void initVariable() {
        activity = this.getActivity();

        loves = Singleton.getInstance(activity).getLoves();

        buys = Singleton.getInstance(activity).getBuys();

        finds = Singleton.getInstance(activity).getFinds();

    }

    /**
     * 加载轮播图数据
     * 设置是否开启自动轮播，需要在 setData 方法之前调用，并且调了该方法后必须再调用一次 setData 方法
     * 例如根据图片当图片数量大于 1 时开启自动轮播，等于 1 时不开启自动轮播
     *
     * @param banner
     */
    private void loadBannerData(BGABanner banner) {
        bannerBean = Singleton.getInstance(getContext()).getBannerBean();


        if (bannerBean != null) {
            banner.setData(bannerBean.getImgs(), bannerBean.getTips());
        } else {
            Log.d(TAG, "loadBannerData: 轮播数据错误，从单例中取出的数据是空");
        }
    }

    private class LoveAdapter extends RecyclerView.Adapter<LoveHolder> {

        @NonNull
        @Override
        public LoveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new LoveHolder(getLayoutInflater().inflate(R.layout.my_love_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull LoveHolder holder, int position) {
            if (position % 2 == 0) {
                holder.root.setBackgroundColor(Color.parseColor("#fffed955"));
            }
            LoveBean loveBean = loves.get(position);
            holder.title.setText(loveBean.getTitle());
            holder.content.setText("  " + loveBean.getContent());

        }

        @Override
        public int getItemCount() {
            return loves.size();
        }
    }

    private class LoveHolder extends RecyclerView.ViewHolder {

        private LinearLayout root;

        private TextView title;
        private TextView content;

        public LoveHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.my_love_root);
            title = itemView.findViewById(R.id.my_love_title);
            content = itemView.findViewById(R.id.my_love_content);
        }
    }


    private class BuyAdapter extends RecyclerView.Adapter<BuyHolder> {

        @NonNull
        @Override
        public BuyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BuyHolder(getLayoutInflater().inflate(R.layout.my_buy_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull BuyHolder holder, int position) {
            if (position % 1 == 0) {
                holder.root.setBackgroundColor(Color.parseColor("#fffed955"));
            }

            BuyBean buyBean = buys.get(position);
            holder.price.setText("¥ " + buyBean.getPrice());
            holder.title.setText(buyBean.getTitle());
            Glide.with(activity)
                    .load(Api.HOME + buyBean.getImg())
                    .placeholder(R.drawable.holder)
                    .error(R.drawable.holder)
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return buys.size();
        }
    }

    private class BuyHolder extends RecyclerView.ViewHolder {

        private LinearLayout root;

        private TextView title;
        private TextView price;
        private ImageView imageView;

        public BuyHolder(View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.my_buy_root);
            title = itemView.findViewById(R.id.my_buy_item_title);
            price = itemView.findViewById(R.id.my_buy_item_price);
            imageView = itemView.findViewById(R.id.my_buy_item_img);

            root.setOnClickListener(view -> {
                BuyBean buyBean = buys.get(getAdapterPosition());
                BuyDetailActivity.startAction(activity, buyBean.getTitle(), buyBean.getId());
            });
        }
    }

    private class FindAdapter extends RecyclerView.Adapter<FindHolder> {

        @NonNull
        @Override
        public FindHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FindHolder(getLayoutInflater().inflate(R.layout.my_find_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull FindHolder holder, int position) {
            if (position % 2 == 0) {
                holder.root.setBackgroundColor(Color.parseColor("#fffed955"));
            }
            FindBean findBean = finds.get(position);
            holder.title.setText(findBean.getTitle());
            holder.place.setText(findBean.getPlace());
            holder.date.setText(findBean.getDate());
            holder.description.setText(findBean.getDescription());
            Glide.with(activity)
                    .load(Api.HOME + findBean.getImg())
                    .placeholder(R.drawable.holder)
                    .error(R.drawable.holder)
                    .into(holder.img);
        }

        @Override
        public int getItemCount() {
            return finds.size();
        }
    }

    private class FindHolder extends RecyclerView.ViewHolder {

        private TextView title;

        private TextView place;
        private TextView date;
        private TextView description;
        private ImageView img;


        private LinearLayout root;

        public FindHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.my_find_root);

            title = itemView.findViewById(R.id.my_find_item_title);
            place = itemView.findViewById(R.id.my_find_item_place);
            date = itemView.findViewById(R.id.my_find_item_date);
            description = itemView.findViewById(R.id.my_find_item_description);
            img = itemView.findViewById(R.id.my_find_item_img);

            root.setOnClickListener(view -> {
                FindBean findBean = finds.get(getAdapterPosition());
                FindDetailActivity.startAction(activity, findBean.getTitle(), findBean.getId());
            });
        }
    }

    /**
     * 轮播填充数据
     *
     * @param banner
     * @param itemView
     * @param model
     * @param position
     */
    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {

        Glide.with(itemView.getContext())
                .load(Api.HOME + model)
                .placeholder(R.drawable.holder)
                .error(R.drawable.holder)
                .dontAnimate()
                .centerCrop()
                .into(itemView);

        itemView.setOnClickListener(view -> {


            String url = bannerBean.getUrls().get(position);
            String title = bannerBean.getTips().get(position);
            Log.i(TAG, "fillBannerItem: url" + url);
            //简单验证链接的有效性，应该做一个HTPP工具类
            if (!TextUtils.isEmpty(url) && url.contains("http")) {

                WebActivity.startWeb(activity, url, title);
            } else {
                T.showShort(activity, "链接无效");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                T.showShort(activity, "敬请期待！");
                break;
        }
    }
}
