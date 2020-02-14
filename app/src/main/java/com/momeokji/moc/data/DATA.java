package com.momeokji.moc.data;

import com.momeokji.moc.R;

import java.util.ArrayList;

public class DATA {
    public ArrayList<Restaurant> KoreanRestaurantList;
    public ArrayList<Restaurant> ChineseRestaurantList;
    public ArrayList<Restaurant> JapaneseRestaurantList;
    public ArrayList<String> Location;

    public DATA() {
        Location = new ArrayList<>();
        Location.add("숭실대 중문");
        Location.add("숭실대 정문");

        //////////// 데이터 직접 등록/////////////
        KoreanRestaurantList = new ArrayList<>();
        // 가게 데이터 등록
        Menu[] MainMenus = new Menu[3];
        MainMenus[0] = new Menu("돼지불백", 5000) ;
        MainMenus[1] = new Menu("된장찌개", 5000);
        MainMenus[2] = new Menu("고추장불고기", 5500);
        ArrayList<Menu> NondureongMenuList = new ArrayList<>();
        NondureongMenuList.add(new Menu("돼지불백", 5000));
        NondureongMenuList.add(new Menu("된장찌개", 5000));
        NondureongMenuList.add(new Menu("고추장불고기", 5500));
        KoreanRestaurantList.add(new Restaurant("논두렁갈비", MainMenus , NondureongMenuList));
        KoreanRestaurantList.add(new Restaurant("논두렁갈비", MainMenus , NondureongMenuList));
        KoreanRestaurantList.add(new Restaurant("논두렁갈비", MainMenus , NondureongMenuList));
        KoreanRestaurantList.add(new Restaurant("논두렁갈비", MainMenus , NondureongMenuList));
        KoreanRestaurantList.add(new Restaurant("논두렁갈비", MainMenus , NondureongMenuList));
        KoreanRestaurantList.add(new Restaurant("논두렁갈비", MainMenus , NondureongMenuList));
        KoreanRestaurantList.add(new Restaurant("논두렁갈비", MainMenus , NondureongMenuList));
        KoreanRestaurantList.add(new Restaurant("논두렁갈비", MainMenus , NondureongMenuList));
        KoreanRestaurantList.add(new Restaurant("논두렁갈비", MainMenus , NondureongMenuList));
        KoreanRestaurantList.add(new Restaurant("논두렁갈비", MainMenus , NondureongMenuList));
        KoreanRestaurantList.add(new Restaurant("논두렁갈비", MainMenus , NondureongMenuList));
        KoreanRestaurantList.add(new Restaurant("논두렁갈비", MainMenus , NondureongMenuList));
        KoreanRestaurantList.add(new Restaurant("논두렁갈비", MainMenus , NondureongMenuList));

        //////////// 데이터 직접 등록/////////////
        ChineseRestaurantList = new ArrayList<>();
        // 가게 데이터 등록
        MainMenus = new Menu[3];
        MainMenus[0] = new Menu("짜장면", 5000) ;
        MainMenus[1] = new Menu("짬뽕", 5000);
        MainMenus[2] = new Menu("탕수육", 5500);
        ArrayList<Menu> ChineseMenuList = new ArrayList<>();
        ChineseMenuList.add(new Menu("짜장면", 5000));
        ChineseMenuList.add(new Menu("짬뽕", 5000));
        ChineseMenuList.add(new Menu("탕수육", 5500));
        ChineseRestaurantList.add(new Restaurant("중국집", MainMenus , ChineseMenuList));

        //////////// 데이터 직접 등록/////////////
        JapaneseRestaurantList = new ArrayList<>();
        // 가게 데이터 등록
        MainMenus = new Menu[3];
        MainMenus[0] = new Menu("가나다라마바사", 5000) ;
        MainMenus[1] = new Menu("가나다라마바사", 5000);
        MainMenus[2] = new Menu("가나다라마바사", 5500);
        ArrayList<Menu> JapaneseMenuList = new ArrayList<>();
        JapaneseMenuList.add(new Menu("덮밥", 5000));
        JapaneseMenuList.add(new Menu("초밥", 5000));
        JapaneseMenuList.add(new Menu("라멘", 5500));
        JapaneseRestaurantList.add(new Restaurant("일식집", MainMenus , JapaneseMenuList));
    }

    public ArrayList<Restaurant> getAllList() {
        ArrayList<Restaurant> tempList = new ArrayList<>();
        tempList.addAll(KoreanRestaurantList);
        tempList.addAll(ChineseRestaurantList);
        tempList.addAll(JapaneseRestaurantList);
        return tempList;
    }
}
