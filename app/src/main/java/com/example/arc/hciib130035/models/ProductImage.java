package com.example.arc.hciib130035.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by arc on 12/18/2016.
 */

public class ProductImage {

    @SerializedName("ProductImageId")
    public int ProductImageId;

    @SerializedName("ProductId")
    @Expose
    public int ProductId;

    @SerializedName("FileName")
    @Expose
    public String FileName;

    @SerializedName("Extension")
    @Expose
    public String Extension;


    public int getProductImageId() {
        return ProductImageId;
    }

    public void setProductImageId(int productImageId) {
        ProductImageId = productImageId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }
}
