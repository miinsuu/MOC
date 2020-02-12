package com.momeokji.moc.data;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class Restaurant implements Serializable {
    //private static final String TAG = "Data - Restaurant";

    private String restaurantName;
    //private Menu[] mainMenu;
    private String minMaxPrice;
    private String preview;
    //ArrayList<Menu> menuList;


    public Restaurant(String json){      // restaurant 생성자
//         if (mainMenu.length != 3) {
//             Log.w(TAG, "Restaurant: The number of main menu is lower than 3");
//             return;
//         }
         //this.mainMenu = mainMenu;

        JsonElement ele = new JsonParser().parse(json);
        JsonObject obj = ele.getAsJsonObject();
        Log.e("Restaurant", obj.toString());
        this.restaurantName = obj.get("name").getAsString(); // 가게이름
        this.minMaxPrice = obj.get("minMax").getAsString(); // 최저 최고 가격
        this.preview = obj.get("preview").getAsString(); // 짧은 가게소개

//        this.menuList = menuList;
//         Iterator<Menu> iterator = menuList.iterator();

//         int tmpPrice;                                          // restaurantlist에 표시될 최소, 최대 가격 찾기 - restaurant의 갯수가 많지 않기에 minmaxPrice를 문자열로 저장하는 방법도 고려
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

    public String getRestaurantName() {
        return this.restaurantName;
    }
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

//    public Menu getMainMenu() {
//        return mainMenu[0];
//    }
//    public Menu[] getMainMenus() {
//        return mainMenu;
//    }
//    public void setMainMenus(Menu[] mainMenu) {
//        this.mainMenu = mainMenu;
//    }

//    public int getMinPrice(){
//        return this.minPrice;
//    }
//    public int getMaxPrice(){
//        return this.maxPrice;
//    }

//    public ArrayList<Menu> getMenuList(){
//        return this.menuList;
//    }
//    public void setMenuList(ArrayList<Menu> menuList){
//        this.menuList = menuList;
//    }

    public String getMinMaxPrice() {
        return minMaxPrice;
    }

    public void setMinMaxPrice(String minMaxPrice) {
        this.minMaxPrice = minMaxPrice;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }
}
