package com.cdtc.student.cdtcassistant.network.bean;

/**
 * 二手商品中RecyclerView展示信息
 *
 * Created by pcc on 2018/4/22.
 *
 * @author pcc
 */
public class BuyBean {

    /**
     * id,用于详细页面的请求
     */
    private Integer id;

    /**
     * 展示的图片
     */
    private String img;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 售价
     */
    private String price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BuyBean{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
