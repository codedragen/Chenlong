package com.eetrust.securedoc.bean;

/**
 * Created by android on 2016/6/17.
 */
public class UserInfo {

    public int result;
    public  String loginName;
    public  String userName;
    public String configdential;
    public String email;
    public String phone;
    public int error;

    @Override
    public String toString() {
        return "UserInfo{" +
                "result=" + result +
                ", loginName='" + loginName + '\'' +
                ", userName='" + userName + '\'' +
                ", configdential='" + configdential + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", error=" + error +
                '}';
    }
}
