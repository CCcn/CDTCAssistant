package com.cdtc.student.cdtcassistant.network.bean;

/**
 * Created by pcc on 2018/5/3.
 *
 * @author pcc
 */
public class AddLoveBean {

    /**
     * 关联的用户id
     */
    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 点赞人数
     */
    private Integer likes = 0;

    /**
     * 表白内容
     */
    private String content;

    /**
     * 评论数
     */
    private Integer comment = 0;

    /**
     * 此商品状态
     *  标记删除：
     *  0:已删除；
     *  1：未删除
     */
    private Integer status = 1;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AddLoveBean{" +
                "userId=" + userId +
                ", title='" + title + '\'' +
                ", likes=" + likes +
                ", content='" + content + '\'' +
                ", comment=" + comment +
                ", status=" + status +
                '}';
    }
}
