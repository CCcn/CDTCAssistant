package com.cdtc.student.cdtcassistant.network.request;

/**
 * 失物招领的分页请求
 * Created by pcc on 2018/5/5.
 *
 * @author pcc
 */
public class FindUsersPageRequest extends BasePageRequest{

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FindUsersPageRequest{" +
                "userId=" + userId +
                '}';
    }
}
