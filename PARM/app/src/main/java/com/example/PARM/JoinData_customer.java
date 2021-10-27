package com.example.PARM;

import com.google.gson.annotations.SerializedName;

public class JoinData_customer {
    @SerializedName("id")
    private String id;

    @SerializedName("pwd")
    private String pwd;

    @SerializedName("name")
    private String name;

    @SerializedName("tel")
    private String tel;

    @SerializedName("email")
    private String email;

    @SerializedName("addr")
    private String addr;

    public JoinData_customer(String id, String pwd, String name, String tel, String email, String addr){
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.addr = addr;
    }
}
