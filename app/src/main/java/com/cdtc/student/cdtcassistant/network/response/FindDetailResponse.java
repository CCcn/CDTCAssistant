package com.cdtc.student.cdtcassistant.network.response;

import com.cdtc.student.cdtcassistant.network.bean.FindDetailBean;

/**
 * 失物招领详细页面请求响应
 * Created by pcc on 2018/4/25.
 *
 * @author pcc
 */
public class FindDetailResponse extends BaseResponse{

    private FindDetailBean findDetail;

    public FindDetailBean getFindDetail() {
        return findDetail;
    }

    public void setFindDetail(FindDetailBean findDetail) {
        this.findDetail = findDetail;
    }

    @Override
    public String toString() {
        return "FindDetailResponse{" +
                "findDetail=" + findDetail +
                '}';
    }
}
