package com.cdtc.student.cdtcassistant.network.request;

import com.cdtc.student.cdtcassistant.network.bean.AddBuyBean;
import com.cdtc.student.cdtcassistant.network.bean.ContactBean;

import java.util.List;

/**
 * Created by pcc on 2018/5/3.
 *
 * @author pcc
 */
public class AddBuyRequest {


    private AddBuyBean buy;

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

    public AddBuyBean getBuy() {
        return buy;
    }

    public void setBuy(AddBuyBean buy) {
        this.buy = buy;
    }

    @Override
    public String toString() {
        return "AddBuyRequest{" +
                "buy=" + buy +
                ", contacts=" + contacts +
                ", imgs=" + imgs +
                '}';
    }
}
