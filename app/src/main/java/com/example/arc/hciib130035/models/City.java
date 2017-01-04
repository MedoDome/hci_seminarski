package com.example.arc.hciib130035.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arc on 12/18/2016.
 */

public class City {

    @SerializedName("CityId")
    public int CityId ;

    @SerializedName("Name")
    @Expose
    public String Name;

    @SerializedName("ZipCode")
    @Expose
    public int ZipCode;

}
