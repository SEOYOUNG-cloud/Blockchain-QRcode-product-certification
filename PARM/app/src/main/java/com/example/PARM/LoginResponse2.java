package com.example.PARM;

import com.google.gson.annotations.SerializedName;

public class LoginResponse2 {
    @SerializedName("result")
    private boolean result;

    @SerializedName("message")
    private String message;

    @SerializedName("id")
    private String id;

    @SerializedName("pwd")
    private String pwd;

    @SerializedName("B_name")
    private String B_name;

    @SerializedName("Admin_name")
    private String Admin_name;

    @SerializedName("addr")
    private String addr;

    public boolean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public String getPwd() {
        return pwd;
    }

    public String getB_Name() {
        return B_name;
    }

    public String getA_Name() {
        return Admin_name;
    }

    public String getAddr() {
        return addr;
    }
}
