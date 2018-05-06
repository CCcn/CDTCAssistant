package com.cdtc.student.cdtcassistant.network.response;

import com.cdtc.student.cdtcassistant.network.bean.BannerBean;
import com.cdtc.student.cdtcassistant.network.bean.BuyBean;
import com.cdtc.student.cdtcassistant.network.bean.FindBean;
import com.cdtc.student.cdtcassistant.network.bean.LoveBean;

import java.util.List;

/**
 * Created by pcc on 2018/4/29.
 *
 * @author pcc
 */
public class InitResponse extends BaseResponse{
    private BannerBean banner;

    private List<LoveBean> loves;

    private List<FindBean> finds;

    private List<BuyBean> buys;

    public BannerBean getBanner() {
        return banner;
    }

    public void setBanner(BannerBean banner) {
        this.banner = banner;
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
