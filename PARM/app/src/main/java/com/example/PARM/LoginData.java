package com.example.PARM;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("id")
    String id;

    @SerializedName("pwd")
    String pwd;

    public LoginData(String id, String pwd){
        this.id = id;
        this.pwd = pwd;
    }
}
