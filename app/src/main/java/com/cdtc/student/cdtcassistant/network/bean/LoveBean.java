package com.cdtc.student.cdtcassistant.network.bean;

/**
 * Created by pcc on 2018/4/29.
 *
 * @author pcc
 */
public class LoveBean {

    private Integer id;
    /**
     *标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "LoveBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
