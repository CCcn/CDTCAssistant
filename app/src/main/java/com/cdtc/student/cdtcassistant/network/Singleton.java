package com.cdtc.student.cdtcassistant.network;

import android.content.Context;

import com.cdtc.student.cdtcassistant.network.bean.BannerBean;
import com.cdtc.student.cdtcassistant.network.bean.UserBean;

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

    private UserBean user;
    private Singleton() {

    }

    public static Singleton getInstance(Context context) {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public BannerBean getBannerBean() {
        return bannerBean;
    }

    public void setBannerBean(BannerBean bannerBean) {
        this.bannerBean = bannerBean;
    }
}
