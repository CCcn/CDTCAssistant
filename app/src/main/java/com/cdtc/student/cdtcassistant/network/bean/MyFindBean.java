package com.cdtc.student.cdtcassistant.network.bean;

/**
 * 我的招领
 *
 * Created by pcc on 2018/4/25.
 *
 * @author pcc
 */
public class MyFindBean {
    /**
     * id
     */
    private String id;

    /**
     * 图片
     */
    private String img;


    /**
     * 标题
     */
    private String title;

    /**
     * 地点
     */
    private String place;

    /**
     * 时间
     */
    private String date;

    /**
     * 描述
     */
    private String description;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "MyFindBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", place='" + place + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
