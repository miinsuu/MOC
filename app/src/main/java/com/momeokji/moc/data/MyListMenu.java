package com.momeokji.moc.data;
import androidx.annotation.NonNull;


public class MyListMenu extends Menu {

    private String restaurantName;

    public MyListMenu(@NonNull Menu targetMenu, String restaurantName) {
        super(targetMenu.getName(), targetMenu.getPrice(), targetMenu.getPhoto());
        this.restaurantName = restaurantName;
    }

    public String getRestaurantName() {
        return this.restaurantName;
    }
}
