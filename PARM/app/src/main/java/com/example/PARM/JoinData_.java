package com.example.PARM;

import com.google.gson.annotations.SerializedName;

public class JoinData_ {
    @SerializedName("id")
    private String id;

    @SerializedName("pwd")
    private String pwd;

    @SerializedName("B_name")
    private String B_name;

    @SerializedName("Admin_name")
    private String Admin_name;

    @SerializedName("tel")
    private String tel;

    @SerializedName("email")
    private String email;

    @SerializedName("addr")
    private String addr;

    public JoinData_(String id, String pwd, String B_name, String Admin_name, String tel, String email, String addr){
        this.id = id;
        this.pwd = pwd;
        this.B_name = B_name;
        this.Admin_name = Admin_name;
        this.tel = tel;
        this.email = email;
        this.addr = addr;
    }
}
