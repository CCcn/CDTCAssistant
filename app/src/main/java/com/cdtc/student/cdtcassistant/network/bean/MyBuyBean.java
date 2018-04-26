package com.cdtc.student.cdtcassistant.network.bean;

/**
 * 我的跳蚤
 * Created by pcc on 2018/4/26.
 *
 * @author pcc
 */
public class MyBuyBean {
    /**
     * id
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 图片
     */
    private String img;

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
                ", description='" + description + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
