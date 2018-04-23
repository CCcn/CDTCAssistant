package com.cdtc.student.cdtcassistant.network;

/**
 *
 *
 * 所以的请求地址全部存放在这里
 *
 * Created by pcc on 2018/4/18.
 *
 * @author pcc
 */
public class Api {

    /**
     *服务器地址
     */
    public static final String HOME = "http://119.23.52.87:8080/Dian";

    /**
     * 加在初始化数据
     *   get
     *
     */
    public static final String INIT = HOME + "/banner";

    /**
     * 反馈
     *   post json
     */
    public static final String FEEDBACK = HOME + "/feedback";

    /**
     *跳蚤商品
     *  get
     */
    public static final String BUY = HOME + "/buy";

    /**
     * 商品详细信息
     *   get goodsId
     */
    public static final String BUY_DETAIL = HOME + "/buyDetail?goodsId=";



}
