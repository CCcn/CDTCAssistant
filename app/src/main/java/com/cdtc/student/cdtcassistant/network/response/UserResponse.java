package com.cdtc.student.cdtcassistant.network.response;

import com.cdtc.student.cdtcassistant.network.bean.UserBean;

/**
 * 用户响应
 * Created by pcc on 2018/5/1.
 *
 * @author pcc
 */
public class UserResponse extends BaseResponse{

    private UserBean data;

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }
}
