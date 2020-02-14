package com.momeokji.moc.data;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class Restaurant implements Serializable {
    private static final String TAG = "Data - RestaurantInfoFragment";

    private String restaurantName;
    private Menu[] mainMenu;
    private int minPrice = Integer.MAX_VALUE, maxPrice = Integer.MIN_VALUE;
    ArrayList<Menu> menuList;

    public Restaurant(String restaurantName, Menu[] mainMenu, ArrayList<Menu> menuList){      // restaurant 생성자
         this.restaurantName = restaurantName;
         if (mainMenu.length != 3) {
             Log.w(TAG, "RestaurantInfoFragment: The number of main menu is lower than 3");
             return;
         }
         this.mainMenu = mainMenu;
         this.menuList = menuList;
         Iterator<Menu> iterator = menuList.iterator();

         int tmpPrice;                                          // restaurantlist에 표시될 최소, 최대 가격 찾기 - restaurant의 갯수가 많지 않기에 minmaxPrice를 문자열로 저장하는 방법도 고려
        if(menuList != null) {
            while (iterator.hasNext()) {
                tmpPrice = iterator.next().getPrice();
                if (tmpPrice < minPrice) {
                    this.minPrice = tmpPrice;
                } else if (tmpPrice > maxPrice) {
                    this.maxPrice = tmpPrice;
                }
            }
        }
    }

    public String getRestaurantName() {
        return this.restaurantName;
    }
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Menu getMainMenu() {
        return mainMenu[0];
    }
    public Menu[] getMainMenus() {
        return mainMenu;
    }
    public void setMainMenus(Menu[] mainMenu) {
        this.mainMenu = mainMenu;
    }

    public int getMinPrice(){
        return this.minPrice;
    }
    public int getMaxPrice(){
        return this.maxPrice;
    }

    public ArrayList<Menu> getMenuList(){
        return this.menuList;
    }
    public void setMenuList(ArrayList<Menu> menuList){
        this.menuList = menuList;
    }
}
