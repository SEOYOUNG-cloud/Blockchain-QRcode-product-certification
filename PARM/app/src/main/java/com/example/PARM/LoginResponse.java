package com.example.PARM;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("result")
    private boolean result;

    @SerializedName("message")
    private String message;

    @SerializedName("id")
    private String id;

    @SerializedName("pwd")
    private String pwd;

    @SerializedName("name")
    private String name;

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

    public String getName() {
        return name;
    }
}
