package com.example.PARM;

import com.google.gson.annotations.SerializedName;

public class checkidData {
    @SerializedName("id")
    String id;

    public checkidData(String id){
        this.id = id;
    }
}
