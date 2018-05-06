package com.cdtc.student.cdtcassistant.network.bean;

import java.util.List;

/**
 * Created by pcc on 2018/5/4.
 *
 * @author pcc
 */
public class BuyDetail {


    private BuyDetailBean buyDetail;

    /**
     * 联系方式
     */
    private List<ContactBean> contacts;

    private List<String> imgs;

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public List<ContactBean> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactBean> contacts) {
        this.contacts = contacts;
    }

    public BuyDetailBean getBuyDetail() {
        return buyDetail;
    }

    public void setBuyDetail(BuyDetailBean buyDetail) {
        this.buyDetail = buyDetail;
    }

    @Override
    public String toString() {
        return "BuyDetail{" +
                "buyDetail=" + buyDetail +
                ", contacts=" + contacts +
                ", imgs=" + imgs +
                '}';
    }
}
