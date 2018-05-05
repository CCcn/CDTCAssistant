package com.cdtc.student.cdtcassistant.network.request;

/**
 * Created by pcc on 2018/5/6.
 *
 * @author pcc
 */
public class LoveUsersPageRequest extends BasePageRequest{
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "LoveUsersPageRequest{" +
                "userId=" + userId +
                '}';
    }
}
