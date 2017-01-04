package com.example.arc.hciib130035.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by arc on 30/12/16.
 */

public class Order  implements Serializable {

    @SerializedName("OrderId")
    @Expose
    public int OrderId;

    @SerializedName("OrderCode")
    @Expose
    public String OrderCode;
    @SerializedName("Message")
    @Expose
    public String Message;



    @SerializedName("StatusId")
    @Expose
    public int StatusId;

    @SerializedName("UserId")
    @Expose
    public int UserId;

    @SerializedName("ProductId")
    @Expose
    public int ProductId;

    @SerializedName("Price")
    @Expose
    public float Price;

    @SerializedName("isDeleted")
    @Expose
    public int isDeleted;


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

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
