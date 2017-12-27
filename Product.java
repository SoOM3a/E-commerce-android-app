package com.example.abdel.ecommerceproject;

/**
 * Created by abdel on 12/7/2017.
 */

public class Product {
    private String id;
    private String Pname;
    private String Catid;
    private String Count;
    private String Price;
    private String Description;
    private  byte[] img ;

    public Product(String id, String pname, String catid, String count, String price, String description, byte[] img) {
        this.id = id;
        Pname = pname;
        Catid = catid;
        Count = count;
        Price = price;
        Description = description;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getCatid() {
        return Catid;
    }

    public void setCatid(String catid) {
        Catid = catid;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
