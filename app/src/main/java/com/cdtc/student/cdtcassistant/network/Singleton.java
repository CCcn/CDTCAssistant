package com.cdtc.student.cdtcassistant.network;

import android.content.Context;

import com.cdtc.student.cdtcassistant.network.bean.BannerBean;

import java.util.List;

/**
 * 单例模式
 *   首页的轮播
 * Created by pcc on 2018/4/21.
 *
 * @author pcc
 */
public class Singleton {

    private static Singleton singleton;

    private BannerBean bannerBean;
    private Singleton() {

    }

    public static Singleton getInstance(Context context) {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }


    public BannerBean getBannerBean() {
        return bannerBean;
    }

    public void setBannerBean(BannerBean bannerBean) {
        this.bannerBean = bannerBean;
    }
}
