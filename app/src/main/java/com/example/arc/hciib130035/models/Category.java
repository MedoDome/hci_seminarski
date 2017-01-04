package com.example.arc.hciib130035.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arc on 12/18/2016.
 */

public class Category {

    @SerializedName("CategoryId")
    public int CategoryId;

    @SerializedName("Title")
    @Expose
    public String Title;

    @SerializedName("Description")
    @Expose
    public String Description;

}
