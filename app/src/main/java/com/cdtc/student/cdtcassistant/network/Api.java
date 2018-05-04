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
    public static final String HOME = "http://119.23.52.87:8087";
//    public static final String HOME = "http://192.168.1.102:8080";
//    public static final String HOME = "http://192.168.1.10:8080";
//    public static final String HOME = "http://192.168.1.110:8080";

    /**
     * 登陆 get/post
     */
    public static final String LOGIN = HOME + "/user/login";

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
     *
     */
    public static final String BUY = HOME + "/buy/allBuys";

    /**
     * 用户所有的跳蚤商品
     */
    public static final String BUY_USER_ALL = HOME + "/buy/showUserBuys";

    /**
     * 提交一个二手商品
     *  post json
     */
    public static final String CREATE_BUY = HOME + "/buy/createBuy";


    /**
     * 商品详细信息
     *   get goodsId
     */
    public static final String BUY_DETAIL = HOME + "/buy/showBuy";

    /**
     * 失物招领
     *   get
     */
    public static final String FIND = HOME + "/find/allFinds";

    /**
     * 用户的所有失物招领
     *   post/get userId
     */
    public static final String FIND_USER_ALL = HOME + "/find/showUserFinds";

    /**
     * 提交一个查找
     *   post json
     */
    public static final String CREATE_FIND = HOME + "/find/createFind";

    /**
     * 失物招领详细信息
     *  get
     */
    public static final String FIND_DETAIL = HOME + "/find/showFind";


    /**
     * 用户所有的表白
     *   get userId
     */
    public static final String LOVE_USER_ALL = HOME + "/love/showUserLoves";

    /**
     * 提交一个love
     *  post json
     */
    public static final String CREATE_LOVE = HOME + "/love/createLove";



}
