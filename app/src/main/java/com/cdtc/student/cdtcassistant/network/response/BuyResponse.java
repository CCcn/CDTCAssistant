package com.cdtc.student.cdtcassistant.network.response;

import com.cdtc.student.cdtcassistant.network.bean.BuyBean;

import java.util.List;

/**
 *
 * 跳蚤商品请求响应
 * Created by pcc on 2018/4/23.
 *
 * @author pcc
 */
public class BuyResponse extends BaseResponse{

    private List<BuyBean> buys;

    public List<BuyBean> getBuys() {
        return buys;
    }

    public void setBuys(List<BuyBean> buys) {
        this.buys = buys;
    }

    @Override
    public String toString() {
        return "BuyResponse{" +
                "buys=" + buys +
                '}';
    }
}
