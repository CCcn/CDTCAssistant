package com.cdtc.student.cdtcassistant.network.response;

import com.cdtc.student.cdtcassistant.network.bean.BuyDetail;

/**
 * 商品详情请求响应
 * Created by pcc on 2018/4/23.
 *
 * @author pcc
 */
public class BuyDetailResponse extends BaseResponse{

    private BuyDetail data;

    public BuyDetail getData() {
        return data;
    }

    public void setData(BuyDetail data) {
        this.data = data;
    }
}
