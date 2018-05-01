package com.cdtc.student.cdtcassistant.network.response;

import com.cdtc.student.cdtcassistant.network.bean.FindBean;

import java.util.List;

/**
 * 失物招领响应
 * Created by pcc on 2018/4/24.
 *
 * @author pcc
 */
public class FindResponse extends BaseResponse{
    private List<FindBean> data;

    public List<FindBean> getData() {
        return data;
    }

    public void setData(List<FindBean> data) {
        this.data = data;
    }
}
