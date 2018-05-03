package com.cdtc.student.cdtcassistant.network.request;

import com.cdtc.student.cdtcassistant.network.bean.AddLoveBean;

/**
 * Created by pcc on 2018/5/3.
 *
 * @author pcc
 */
public class AddLoveRequest {
    private AddLoveBean love;

    public AddLoveBean getLove() {
        return love;
    }

    public void setLove(AddLoveBean love) {
        this.love = love;
    }
}
