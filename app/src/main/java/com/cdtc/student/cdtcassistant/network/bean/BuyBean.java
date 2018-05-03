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
    private String id;

    /**
     * 展示的图片
     */
    private String img;

    /**
     * 描述信息
     */
    private String title;

    /**
     * 售价
     */
    private String price;

    /**
     * 交易完成状态
     */
    private Integer finished;

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "BuyBean{" +
                "id='" + id + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", finished=" + finished +
                '}';
    }
}
