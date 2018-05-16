package com.shadowws.offurz.Pojo;

/**
 * Created by Lenovo on 2/17/2018.
 */

public class ProductName {
    private String productName;
private int img;
    private String rate;

    public ProductName(String productName) {
        this.productName = productName;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
