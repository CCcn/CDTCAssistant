package com.cdtc.student.cdtcassistant.network.bean;

import java.util.List;

/**
 * Created by pcc on 2018/04/21.
 * @author pcc
 *
 * 轮播图的数据
 */

public class BannerBean {

    /**
     * 图片url
     */
    private List<String> imgs;

    /**
     * 配字
     */
    private List<String> tips;

    /**
     * 网页url
     */
    private List<String> urls;

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public List<String> getTips() {
        return tips;
    }

    public void setTips(List<String> tips) {
        this.tips = tips;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

}
