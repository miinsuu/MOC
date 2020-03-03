package com.momeokji.moc.data;

import com.momeokji.moc.Database.DataListener;
import com.momeokji.moc.Database.DatabaseQueryClass;

import java.util.ArrayList;

public class DATA {
    public ArrayList<Restaurant> KoreanRestaurantList;
    public ArrayList<Restaurant> ChineseRestaurantList;
    public ArrayList<Restaurant> JapaneseRestaurantList;
    public ArrayList<String> Location;
    public ArrayList<MyListMenu> MyListMenuList;
    public Restaurant selectedRestaurant ;

    public DATA() {
        Location = new ArrayList<>();
        Location.add("숭실대 중문");
        Location.add("숭실대 정문");

        //////////// 데이터 직접 등록/////////////
        KoreanRestaurantList = new ArrayList<>();



        DatabaseQueryClass.ShopFromDB.getKoreanShopList(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                //Log.e("데이터DB등록", data.toString());
                KoreanRestaurantList.add(new Restaurant(data.toString()));
            }
        });

        ChineseRestaurantList = new ArrayList<>();

        DatabaseQueryClass.ShopFromDB.getChineseShopList(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                //Log.e("데이터DB등록", data.toString());
                ChineseRestaurantList.add(new Restaurant(data.toString()));
            }
        });

        JapaneseRestaurantList = new ArrayList<>();

        DatabaseQueryClass.ShopFromDB.getJapaneseShopList(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                //Log.e("데이터DB등록", data.toString());
                JapaneseRestaurantList.add(new Restaurant(data.toString()));
            }
        });

        MyListMenuList = new ArrayList<>();
    }

    public ArrayList<Restaurant> getAllList() {
        ArrayList<Restaurant> tempList = new ArrayList<>();
        tempList.addAll(KoreanRestaurantList);
        tempList.addAll(ChineseRestaurantList);
        tempList.addAll(JapaneseRestaurantList);
        return tempList;
    }
}
