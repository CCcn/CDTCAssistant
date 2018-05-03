package com.cdtc.student.cdtcassistant.network.response;

import com.cdtc.student.cdtcassistant.network.bean.MyLoveBean;

import java.util.List;

/**
 * Created by pcc on 2018/5/3.
 *
 * @author pcc
 */
public class LoveResponse extends BaseResponse{

    private List<MyLoveBean> data;

    public List<MyLoveBean> getData() {
        return data;
    }

    public void setData(List<MyLoveBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoveResponse{" +
                "data=" + data +
                '}';
    }
}
