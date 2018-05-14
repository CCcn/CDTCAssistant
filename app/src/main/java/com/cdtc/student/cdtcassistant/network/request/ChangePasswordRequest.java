package com.cdtc.student.cdtcassistant.network.request;

/**
 * 修改密码
 * Created by pcc on 2018/5/14.
 *
 * @author pcc
 */
public class ChangePasswordRequest {
    private String  studentNumber;

    private String oldPassword;

    private String newPassword;

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "studentNumber='" + studentNumber + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
