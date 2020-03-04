package com.momeokji.moc.data;

import android.media.Image;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Menu implements Serializable {
    private String name;
    private String price;
    private Image photo;

    public Menu(String name, String price){
        this.name = name;
        this.price = price;
        this.photo = null;
    }
    public Menu(String name, String price, Image photo){
        this.name = name;
        this.price = price;
        this.photo = photo;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getPrice(){
        return this.price;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public Image getPhoto() {
        return photo;
    }
    public void setPhoto(Image targetPhoto) {
        this.photo = targetPhoto;
    }
}
