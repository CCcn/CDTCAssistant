package com.cdtc.student.cdtcassistant.fragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cdtc.student.cdtcassistant.R;
import com.cdtc.student.cdtcassistant.activity.WebActivity;
import com.cdtc.student.cdtcassistant.network.Singleton;
import com.cdtc.student.cdtcassistant.network.bean.BannerBean;
import com.cdtc.student.cdtcassistant.util.T;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.transformer.TransitionEffect;


/**
 *
 * 首页
 *
 * Created by pcc on 2018/4/18.
 *
 * @author pcc
 */
public class IndexFragment extends Fragment implements BGABanner.Adapter<ImageView, String>{

    /**
     * 首页轮播
     */
    private BGABanner bgaBanner;

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
     * @param view container
     */
    private void initView(View view) {
        bgaBanner = view.findViewById(R.id.index_fragment_banner);
        bgaBanner.setTransitionEffect(TransitionEffect.Default);

    }

    private void initVariable() {
        activity = this.getActivity();
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
            banner.setAutoPlayAble(true);
            banner.setAdapter(IndexFragment.this);
            banner.setData(bannerBean.getImgs(), bannerBean.getTips());
        } else {
            Log.d(TAG, "loadBannerData: 轮播数据错误，从单例中取出的数据是空" );
            T.showShort(activity,"数据错误");
        }
    }

    /**
     * 轮播填充数据
     * @param banner
     * @param itemView
     * @param model
     * @param position
     */
    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
        Glide.with(itemView.getContext())
                .load(model)
                .placeholder(R.drawable.holder)
                .error(R.drawable.holder)
                .dontAnimate()
                .centerCrop()
                .into(itemView);

        itemView.setOnClickListener(view -> {
            String url = bannerBean.getUrls().get(position);
            Log.i(TAG, "fillBannerItem: url" + url);
            //简单验证链接的有效性，应该做一个HTPP工具类
            if (!TextUtils.isEmpty(url) && url.contains("http")) {
                WebActivity.startWeb(activity, url);
            } else {
                T.showShort(activity,"链接无效");
            }
        });
    }
}
