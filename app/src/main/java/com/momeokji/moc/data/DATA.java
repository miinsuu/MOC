package com.momeokji.moc.data;

import android.util.Log;

import com.momeokji.moc.Database.DataListener;
import com.momeokji.moc.Database.DatabaseQueryClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DATA {
    public ArrayList<Restaurant> KoreanRestaurantList;
    public ArrayList<Restaurant> ChineseRestaurantList;
    public ArrayList<Restaurant> JapaneseRestaurantList;
    public ArrayList<Restaurant> ChickenRestaurantList;
    public ArrayList<Restaurant> SnackRestaurantList;
    public ArrayList<Restaurant> WesternRestaurantList;
    public ArrayList<Restaurant> NightRestaurantList;
    public ArrayList<Restaurant> FastRestaurantList;

    public ArrayList<String> Location;
    public ArrayList<MyListMenu> MyListMenuList;
    public Restaurant selectedRestaurant ;

    public DATA() {
        Location = new ArrayList<>();
        Location.add("숭실대 중문");
        Location.add("숭실대 정문");

        //////////// 데이터 등록/////////////
        KoreanRestaurantList = new ArrayList<>();

        DatabaseQueryClass.ShopFromDB.getKoreanShopList(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                Log.e("데이터DB등록", data.toString());
                KoreanRestaurantList.add(new Restaurant(data.toString()));
            }
        });

        ChineseRestaurantList = new ArrayList<>();

        DatabaseQueryClass.ShopFromDB.getChineseShopList(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                Log.e("데이터DB등록", data.toString());
                ChineseRestaurantList.add(new Restaurant(data.toString()));
            }
        });

        JapaneseRestaurantList = new ArrayList<>();

        DatabaseQueryClass.ShopFromDB.getJapaneseShopList(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                Log.e("데이터DB등록", data.toString());
                JapaneseRestaurantList.add(new Restaurant(data.toString()));
            }
        });

        ChickenRestaurantList = new ArrayList<>();

        DatabaseQueryClass.ShopFromDB.getChickenShopList(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                Log.e("데이터DB등록", data.toString());
                ChickenRestaurantList.add(new Restaurant(data.toString()));
            }
        });

        SnackRestaurantList = new ArrayList<>();

        DatabaseQueryClass.ShopFromDB.getSnackShopList(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                Log.e("데이터DB등록", data.toString());
                SnackRestaurantList.add(new Restaurant(data.toString()));
            }
        });

        WesternRestaurantList = new ArrayList<>();

        DatabaseQueryClass.ShopFromDB.getWesternShopList(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                Log.e("데이터DB등록", data.toString());
                WesternRestaurantList.add(new Restaurant(data.toString()));
            }
        });

        NightRestaurantList = new ArrayList<>();

        DatabaseQueryClass.ShopFromDB.getNightShopList(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                Log.e("데이터DB등록", data.toString());
                NightRestaurantList.add(new Restaurant(data.toString()));
            }
        });

        FastRestaurantList = new ArrayList<>();

        DatabaseQueryClass.ShopFromDB.getFastShopList(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                Log.e("데이터DB등록", data.toString());
                FastRestaurantList.add(new Restaurant(data.toString()));
            }
        });

      
        MyListMenuList = new ArrayList<>();
    }

    public ArrayList<Restaurant> getAllList() {
        ArrayList<Restaurant> tempList = new ArrayList<>();
        tempList.addAll(KoreanRestaurantList);
        tempList.addAll(ChineseRestaurantList);
        tempList.addAll(JapaneseRestaurantList);
        tempList.addAll(ChickenRestaurantList);
        tempList.addAll(SnackRestaurantList);
        tempList.addAll(WesternRestaurantList);
        tempList.addAll(FastRestaurantList);
        tempList.addAll(NightRestaurantList);

        ArrayList<Restaurant> ResultRestaurantList = new ArrayList<>(); // 중복제거된 가게리스트
        ArrayList<String> RestaurantNameList = new ArrayList<>();
        ArrayList<String> RestaurantNameListTmp = new ArrayList<>();

        for(int i = 0; i < tempList.size(); i++) // 가게 중복검사
        {
            RestaurantNameList.add(tempList.get(i).getRestaurantName());
        }

        for(int i = 0; i < RestaurantNameList.size(); i++) // 가게 중복검사
        {
            if(!RestaurantNameListTmp.contains(RestaurantNameList.get(i)))
            {
                RestaurantNameListTmp.add(RestaurantNameList.get(i));
                ResultRestaurantList.add(tempList.get(i));
            }

        }

        // 중복제거된 가게리스트를 가게이름 기준 오름차순 정렬
        Collections.sort(ResultRestaurantList, new Restaurant.NameAscCompare());

        return ResultRestaurantList;
    }
}
