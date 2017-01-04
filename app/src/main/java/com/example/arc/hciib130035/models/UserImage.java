package com.example.arc.hciib130035.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arc on 12/18/2016.
 */

public class UserImage {

    @SerializedName("UserId")
    public int UserId;

    @SerializedName("FileName")
    @Expose
    public String FileName;

    @SerializedName("Extension")
    @Expose
    public String Extension;
}
