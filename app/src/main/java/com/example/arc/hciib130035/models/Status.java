package com.example.arc.hciib130035.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by arc on 02/01/17.
 */

public class Status implements Serializable {

    @SerializedName("StatusId")
    private int StatusId;

    @SerializedName("Title")
    @Expose
    private String Title;

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
