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

    private List<BuyBean> data;

    public List<BuyBean> getData() {
        return data;
    }

    public void setData(List<BuyBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BuyResponse{" +
                "data=" + data +
                '}';
    }
}
