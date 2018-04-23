package com.cdtc.student.cdtcassistant.network.bean;

import java.util.List;

/**
 *
 * 商品详细展示
 * Created by pcc on 2018/4/23.
 *
 * @author pcc
 */
public class BuyDetailBean {
    /**
     * 商品名称
     */
    private String name;

    /**
     * 价格
     */
    private String price;

    /**
     * 描述
     */
    private String description;

    /**
     * 联系人
     */
    private String owner;

    /**
     * 联系方式
     */
    private List<ContactBean> contacts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<ContactBean> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactBean> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "BuyDetailBean{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
