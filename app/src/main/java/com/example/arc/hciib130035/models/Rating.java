package com.example.arc.hciib130035.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arc on 12/18/2016.
 */

public class Rating {

    @SerializedName("RatingId")
    public int RatingId;

    @SerializedName("Description")
    @Expose
    public String Description;

    @SerializedName("Mark")
    @Expose
    public int Mark;

    @SerializedName("OrderId")
    @Expose
    public int OrderId;

    @SerializedName("UserId")
    @Expose
    public int UserId;

    @SerializedName("OrderCode")
    @Expose
    public String OrderCode;

    public int getRatingId() {
        return RatingId;
    }

    public void setRatingId(int ratingId) {
        RatingId = ratingId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getMark() {
        return Mark;
    }

    public void setMark(int mark) {
        Mark = mark;
    }


    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }
}
