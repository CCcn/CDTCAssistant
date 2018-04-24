package com.cdtc.student.cdtcassistant.network.bean;

import java.util.List;

/**
 *
 * 失物招领详细页面
 * Created by pcc on 2018/4/25.
 *
 * @author pcc
 */
public class FindDetailBean {

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 丢失/拾得时间
     */
    private String lostDate;

    /**
     * 丢失/拾得地点
     */
    private String lostPlace;

    /**
     * 联系方式
     */
    private List<ContactBean> contacts;

    /**
     * 描述
     */
    private String description;

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getLostDate() {
        return lostDate;
    }

    public void setLostDate(String lostDate) {
        this.lostDate = lostDate;
    }

    public String getLostPlace() {
        return lostPlace;
    }

    public void setLostPlace(String lostPlace) {
        this.lostPlace = lostPlace;
    }

    public List<ContactBean> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactBean> contacts) {
        this.contacts = contacts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "FindDetailBean{" +
                "contactPerson='" + contactPerson + '\'' +
                ", lostDate='" + lostDate + '\'' +
                ", lostPlace='" + lostPlace + '\'' +
                ", contacts=" + contacts +
                ", description='" + description + '\'' +
                '}';
    }
}
