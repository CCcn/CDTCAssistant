package com.cdtc.student.cdtcassistant.network;

import android.content.Context;

import com.cdtc.student.cdtcassistant.network.bean.BannerBean;
import com.cdtc.student.cdtcassistant.network.bean.BuyBean;
import com.cdtc.student.cdtcassistant.network.bean.FindBean;
import com.cdtc.student.cdtcassistant.network.bean.LoveBean;
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

    private List<LoveBean> loves;

    private List<FindBean> finds;

    private List<BuyBean> buys;

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

    public List<LoveBean> getLoves() {
        return loves;
    }

    public void setLoves(List<LoveBean> loves) {
        this.loves = loves;
    }

    public List<FindBean> getFinds() {
        return finds;
    }

    public void setFinds(List<FindBean> finds) {
        this.finds = finds;
    }

    public List<BuyBean> getBuys() {
        return buys;
    }

    public void setBuys(List<BuyBean> buys) {
        this.buys = buys;
    }
}
