package com.cdtc.student.cdtcassistant.network.bean;

/**
 * 联系方式
 * Created by pcc on 2018/4/23.
 *
 * @author pcc
 */
public class ContactBean {


    /**
     * 联系方式 u类型
     *   wx
     *   qq
     *   phone
     */
    private String contactType;

    /**
     * 号码
     */
    private String number;
    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ContactBean{" +
                "contactType='" + contactType + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
