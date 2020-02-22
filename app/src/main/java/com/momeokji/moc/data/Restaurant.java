package com.momeokji.moc.data;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class Restaurant implements Serializable {
    private static final String TAG = "Data - RestaurantInfoFragment";

    private String restaurantName;
    private String address;
    private String phoneNumber;
    private Menu[] mainMenu;
    private String minMaxPrice;
    private String preview;
    private ArrayList<Map> menuList = new ArrayList<>();


    public Restaurant(String json){      // restaurant 생성자
//         if (mainMenu.length != 3) {
//             Log.w(TAG, "Restaurant: The number of main menu is lower than 3");
//             return;
//         }
         //this.mainMenu = mainMenu;

        JsonElement ele = new JsonParser().parse(json);
        JsonObject obj = ele.getAsJsonObject();
        //Log.e("Restaurant", obj.toString());

        this.restaurantName = obj.get("name").getAsString(); // 가게이름

        this.address = obj.get("address").getAsString(); // 가게주소

        this.phoneNumber = obj.get("phoneNumber").getAsString(); //가게전화번호

        this.minMaxPrice = obj.get("minMax").getAsString(); // 최저 최고 가격

        this.preview = obj.get("preview").getAsString(); // 짧은 가게소개

        this.mainMenu = new Menu[3]; // 메인메뉴 3가지
        JsonObject mainMenuMapJson = obj.get("topMenu").getAsJsonObject(); // 메인메뉴 HashMap
        String name;
        String price;
        int i = 0;
        for (Object o : mainMenuMapJson.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            name = entry.getKey().toString(); //메뉴이름
            price = entry.getValue().toString(); //메뉴가격
            mainMenu[i++] = new Menu(name, price);
        }

        JsonObject menuCategoryMapJson = obj.get("menu").getAsJsonObject(); // 메뉴카테고리리스트 HashMap
        String menuCategoryName;
        for (Object o : menuCategoryMapJson.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            menuCategoryName = entry.getKey().toString(); //메뉴카테고리 이름
            //Log.e("메뉴카테고리TEST",menuCategoryName);

            JsonObject menuMapJson = (JsonObject)entry.getValue();

            Map<String, Object> menuCategoryTmp  = new HashMap<>();
            ArrayList<Menu> menuTmp = new ArrayList<>();
            for (Object o2 : menuMapJson.entrySet()) {
                Map.Entry entry2 = (Map.Entry) o2;
                name = entry2.getKey().toString(); //메뉴 이름
                price = entry2.getValue().toString(); //메뉴 가격

                menuTmp.add(new Menu(name, price)); //이름/가격 메뉴로 묶기
                //Log.e("메뉴이름가격TEST",name+":"+price);
            }

            menuCategoryTmp.put(menuCategoryName, menuTmp); //한 카테고리 저장완료
//            if(!menuCategoryTmp.isEmpty())
//                Log.e("카테고리MAP저장체크", "저장성공!!!");
//            else
//                Log.e("카테고리MAP저장체크", "실패");

            this.menuList.add(menuCategoryTmp); //전체 메뉴에 완성된 카테고리 저장
//            if(!this.menuList.isEmpty())
//                Log.e("전체메뉴저장체크", "저장성공!!!");
//            else
//                Log.e("전체메뉴저장체크", "실패");
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

    public ArrayList<Map> getMenuList(){
        return this.menuList;
    }
    public void setMenuList(ArrayList<Map> menuList){
        this.menuList = menuList;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
