package com.cdtc.student.cdtcassistant.network.bean;

import com.cdtc.student.cdtcassistant.network.bean.BuyDetailBean;
import com.cdtc.student.cdtcassistant.network.bean.ContactBean;

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
        return "BuyDetailResponse{" +
                "buyDetail=" + buyDetail +
                ", contacts=" + contacts +
                '}';
    }
}
