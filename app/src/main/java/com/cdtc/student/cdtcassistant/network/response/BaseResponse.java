package com.cdtc.student.cdtcassistant.network.response;

/**
 * 所有的response应该继承自它
 * Created by pcc on 2018/4/22.
 *
 * @author pcc
 */
public class BaseResponse {
    /**
     * 返回代码
     */
    public int code;

    /**
     * 返回消息
     */
    public String message;
}
