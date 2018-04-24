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
    private List<FindBean> finds;

    public List<FindBean> getFinds() {
        return finds;
    }

    public void setFinds(List<FindBean> finds) {
        this.finds = finds;
    }

    @Override
    public String toString() {
        return "FindResponse{" +
                "finds=" + finds +
                '}';
    }
}
