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
//    public static final String HOME = "http://119.23.52.87:8080/Dian";
//    public static final String HOME = "http://192.168.1.102:8080";
    public static final String HOME = "http://192.168.1.110:8080";

    /**
     * 加在初始化数据
     *   get
     *
     */
    public static final String INIT = HOME + "/index";

    /**
     * 反馈
     *   post json
     */
    public static final String FEEDBACK = HOME + "/feedback/add";

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

    /**
     * 失物招领
     *   get
     */
    public static final String FIND = HOME + "/find";

    /**
     * 失物招领详细信息
     *   get findId
     */
    public static final String FIND_DETAIL = HOME + "/findDetail?findId=";



}
