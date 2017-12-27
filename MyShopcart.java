package com.example.abdel.ecommerceproject;

import android.location.LocationListener;
import android.location.LocationManager;

import java.security.PrivateKey;
import java.util.ArrayList;

/**
 * Created by abdel on 12/7/2017.
 */

public class MyShopcart {

    private ArrayList<Product> MyProducts ;
    private ArrayList<Integer> MyProudctsCnt ;
    private ArrayList<String> ListTransId ;

    private String Email ;
    private  Double TotalPrice ;

    public ArrayList<Product> getMyProducts() {
        return MyProducts;
    }

    public void setMyProducts(ArrayList<Product> myProducts) {
        MyProducts = myProducts;
    }

    public ArrayList<Integer> getMyProudctsCnt() {
        return MyProudctsCnt;
    }

    public void setMyProudctsCnt(ArrayList<Integer> myProudctsCnt) {
        MyProudctsCnt = myProudctsCnt;
    }

    public ArrayList<String> getListTransId() {
        return ListTransId;
    }

    public void setListTransId(ArrayList<String> listTransId) {
        ListTransId = listTransId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Double getTotalPrice() {
        TotalPrice = 0.0 ;
        for(int i = 0 ; i<MyProducts.size();i++)
        {
            double productP = Double.parseDouble(MyProducts.get(i).getPrice());
            TotalPrice += ( productP * MyProudctsCnt.get(i));
        }
        return TotalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        TotalPrice = totalPrice;
    }
}
