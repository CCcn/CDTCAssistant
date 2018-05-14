package com.cdtc.student.cdtcassistant.network.request;

/**
 *
 * 反馈信息
 *   后期实现获取系统版本、设备型号
 * Created by pcc on 2018/4/22.
 *
 * @author pcc
 */
public class FeedbackRequest {

    /**
     * 当前登录用户id
     */
    private Integer userId;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 联系方式
     */
    private String contact;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "FeedbackRequest{" +
                "userId=" + userId +
                ", content='" + content + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
