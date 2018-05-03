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
                '}';
    }
}
