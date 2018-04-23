package com.cdtc.student.cdtcassistant.network.bean;

/**
 * 联系方式
 * Created by pcc on 2018/4/23.
 *
 * @author pcc
 */
public class ContactBean {

    /**
     * 类别
     *  电话
     *  qq
     *  微信
     */
    private String type;

    /**
     * 号码
     */
    private String number;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
                "type='" + type + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
