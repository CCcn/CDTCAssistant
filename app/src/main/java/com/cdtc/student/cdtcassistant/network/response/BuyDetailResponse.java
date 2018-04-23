package com.cdtc.student.cdtcassistant.network.response;

import com.cdtc.student.cdtcassistant.network.bean.BuyDetailBean;

/**
 * 商品详情请求响应
 * Created by pcc on 2018/4/23.
 *
 * @author pcc
 */
public class BuyDetailResponse extends BaseResponse{
    private BuyDetailBean buyDetail;

    public BuyDetailBean getBuyDetail() {
        return buyDetail;
    }

    public void setBuyDetail(BuyDetailBean buyDetail) {
        this.buyDetail = buyDetail;
    }
}
