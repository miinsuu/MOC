package com.momeokji.moc.data;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class Shop implements Serializable {
    private static final String TAG = "Data - Shop";

    private String shopName;
    private Menu[] mainMenu;
    //private int minPrice = Integer.MAX_VALUE, maxPrice = Integer.MIN_VALUE;
    ArrayList<Menu> menuList;

    public Shop(String shopName, Menu[] mainMenu, ArrayList<Menu> menuList){      // shop 생성자
         this.shopName = shopName;
         if (mainMenu.length != 3) {
             Log.w(TAG, "Shop: The number of main menu is lower than 3");
             return;
         }
         this.mainMenu = mainMenu;
         this.menuList = menuList;
         Iterator<Menu> iterator = menuList.iterator();

//         int tmpPrice;                                          // shoplist에 표시될 최소, 최대 가격 찾기 - shop의 갯수가 많지 않기에 minmaxPrice를 문자열로 저장하는 방법도 고려
//        if(menuList != null) {
//            while (iterator.hasNext()) {
//                tmpPrice = iterator.next().getPrice();
//                if (tmpPrice < minPrice) {
//                    this.minPrice = tmpPrice;
//                } else if (tmpPrice > maxPrice) {
//                    this.maxPrice = tmpPrice;
//                }
//            }
//        }
    }

    public String getShopName() {
        return this.shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
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
//
//    public int getMinPrice(){
//        return this.minPrice;
//    }
//    public int getMaxPrice(){
//        return this.maxPrice;
//    }

    public ArrayList<Menu> getMenuList(){
        return this.menuList;
    }
    public void setMenuList(ArrayList<Menu> menuList){
        this.menuList = menuList;
    }
}
