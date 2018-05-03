package com.cdtc.student.cdtcassistant.network.response;

import com.cdtc.student.cdtcassistant.network.bean.MyBuyBean;

import java.util.List;

/**
 * Created by pcc on 2018/5/3.
 *
 * @author pcc
 */
public class MyBuyBeanResponse extends BaseResponse{

    private List<MyBuyBean> data;

    public List<MyBuyBean> getData() {
        return data;
    }

    public void setData(List<MyBuyBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MyBuyBeanResponse{" +
                "data=" + data +
                '}';
    }
}
