package com.cdtc.student.cdtcassistant.network.bean;

/**
 * 失物招领中RecyclerView展示信息
 * Created by pcc on 2018/4/24.
 *
 * @author pcc
 */
public class FindBean {

    /**
     * id
     *
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
     * 是否已找到
     *  0:未完成（未找到）
     *  1:已完成（已找到）
     */
    private Integer finished;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public String getId() {
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

    @Override
    public String toString() {
        return "FindBean{" +
                "id='" + id + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", place='" + place + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
