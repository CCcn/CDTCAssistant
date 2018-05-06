package com.cdtc.student.cdtcassistant.network.bean;

import java.util.List;

/**
 * Created by pcc on 2018/5/4.
 *
 * @author pcc
 */
public class FindDetail {

    private FindDetailBean findDetail;

    private List<ContactBean> contacts;

    private List<String> imgs;

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public FindDetailBean getFindDetail() {
        return findDetail;
    }

    public void setFindDetail(FindDetailBean findDetail) {
        this.findDetail = findDetail;
    }

    public List<ContactBean> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactBean> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "FindDetail{" +
                "findDetail=" + findDetail +
                ", contacts=" + contacts +
                ", imgs=" + imgs +
                '}';
    }
}
