package com.cdtc.student.cdtcassistant.network.bean;

import java.util.List;

/**
 * Created by pcc on 2018/5/2.
 *
 * @author pcc
 */
public class AddFindBean {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 图片
     */
    private String img;

    /**
     * 物品名称
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 相关地点
     */
    private String place;

    /**
     * 相关时间
     */
    private String date;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 物品描述信息
     */
    private String description;

    /**
     * 是否完成
     *    0：未找到
     *    1：已找到
     */
    private Integer finished = 0;

    /**
     * 此商品状态
     *  标记删除：
     *  0:已删除；
     *  1：未删除
     */
    private Integer status = 1;

    /**
     * 0:无图片
     * 1:有图片
     */
    private Integer hasImg = 0;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
