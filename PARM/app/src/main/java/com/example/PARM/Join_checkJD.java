package com.example.PARM;

import com.google.gson.annotations.SerializedName;

public class Join_checkJD {
    @SerializedName("result")
    private boolean result;

    @SerializedName("message")
    private String message;

    public boolean getResult(){
        return result;
    }

    public String getMessage(){
        return message;
    }
}
