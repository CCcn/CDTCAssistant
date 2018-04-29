package com.cdtc.student.cdtcassistant.network.response;

import com.cdtc.student.cdtcassistant.network.bean.BannerBean;

/**
 * Created by pcc on 2018/4/29.
 *
 * @author pcc
 */
public class InitResponse extends BaseResponse{
    private BannerBean banner;

    public BannerBean getBanner() {
        return banner;
    }

    public void setBanner(BannerBean banner) {
        this.banner = banner;
    }
}
