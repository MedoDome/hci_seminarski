package com.example.arc.hciib130035.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by arc on 12/18/2016.
 */

public class Product implements Serializable {

    @SerializedName("ProductId")
    @Expose
    public int ProductId;

    @SerializedName("Title")
    @Expose
    public String Title;

    @SerializedName("Description")
    @Expose
    public String Description;

    @SerializedName("Price")
    @Expose
    public float Price;

//    @SerializedName("Created")
//    @Expose
//    public Date Created;

    @SerializedName("CategoryId")
    @Expose
    public int CategoryId;

    @SerializedName("CategoryName")
    @Expose
    public String CategoryName;

    @SerializedName("OwnerId")
    @Expose
    public int OwnerId;

    @SerializedName("OwnerUsername")
    @Expose
    public String OwnerUsername;


    @SerializedName("Picure")
    @Expose
    public List<ProductImage> ProductImages;

    public List<ProductImage> getProductImages() {
        return ProductImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        ProductImages = productImages;
    }

    @SerializedName("IsDeleted")
    @Expose
    public int IsDeleted ;

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getUserId() {
        return this.OwnerId;
    }

    public void setUserId(int userId) {
        OwnerId = userId;
    }

    public String getOwnerUsername() {
        return OwnerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        OwnerUsername = ownerUsername;
    }

    public int getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        IsDeleted = isDeleted;
    }
}
