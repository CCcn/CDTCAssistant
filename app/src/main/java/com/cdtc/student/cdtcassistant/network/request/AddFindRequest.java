package com.cdtc.student.cdtcassistant.network.request;

import com.cdtc.student.cdtcassistant.network.bean.AddFindBean;
import com.cdtc.student.cdtcassistant.network.bean.ContactBean;

import java.util.List;

/**
 * Created by pcc on 2018/5/2.
 *
 * @author pcc
 */
public class AddFindRequest{

    private AddFindBean find;

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

    public AddFindBean getFind() {
        return find;
    }

    public void setFind(AddFindBean find) {
        this.find = find;
    }

    public List<ContactBean> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactBean> contacts) {
        this.contacts = contacts;
    }
}
