package com.sabry.muhammed.hyperdesign;

import android.graphics.Bitmap;
import android.provider.ContactsContract;


public class DataHolder {

    private int id;
    private String imageUrl, textData,price;
    private int imageHeigh;


    public DataHolder(String url, String data, int heigh) {
        imageUrl = url;
        textData = data;
        imageHeigh = heigh;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setImageUrl(String url) {
        imageUrl = url;
    }

    public void setTextData(String data) {
        textData = data;
    }

    public void setImageHeigh(int heigh) {
        imageHeigh = heigh;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public String getTextData() {
        return textData;
    }

    public int getImageHeight() {
        return imageHeigh;
    }

    public int getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }
}
