package com.cdtc.student.cdtcassistant.network.bean;

/**
 * Created by pcc on 2018/5/3.
 *
 * @author pcc
 */
public class AddBuyBean {

    /**
     * 关联的用户id
     */
    private Integer userId;

    /**
     * 配图
     */
    private String img;

    /**
     * 标题
     */
    private String title;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private String price;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 商品发布者、联系人
     */
    private String owner;

    /**
     * 此商品状态
     *  标记删除：
     *  0:已删除；
     *  1：未删除
     */
    private Integer status = 1;

    /**
     * 是否完成交易
     *  0:未完成（待交易）
     *  1:已完成：（交易已完成）
     */
    private Integer finished = 0;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Integer getHasImg() {
        return hasImg;
    }

    public void setHasImg(Integer hasImg) {
        this.hasImg = hasImg;
    }

    @Override
    public String toString() {
        return "AddBuyBean{" +
                "userId=" + userId +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                ", status=" + status +
                ", finished=" + finished +
                ", hasImg=" + hasImg +
                '}';
    }
}
