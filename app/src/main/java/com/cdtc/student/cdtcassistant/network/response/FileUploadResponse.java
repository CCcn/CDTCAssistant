package com.cdtc.student.cdtcassistant.network.response;

import java.util.List;

/**
 * Created by pcc on 2018/5/6.
 *
 * @author pcc
 */
public class FileUploadResponse extends BaseResponse{
    private ImageUrl data;

    public ImageUrl getData() {
        return data;
    }

    public void setData(ImageUrl data) {
        this.data = data;
    }
}
