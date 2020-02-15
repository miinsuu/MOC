package com.momeokji.moc.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Menu implements Serializable {
    private String name;
    private String price;
    ///사진 추후 추가///

    public Menu(String name, String price){
        this.name = name;
        this.price = price;
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
}
