package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.momeokji.moc.RecyclerViewAdapter.RecyclerViewAdapter_MenuList;
import com.momeokji.moc.data.Shop;

public class ShopPage extends AppCompatActivity {

    private RecyclerViewAdapter_MenuList adapter;                                                           // menulist를 위한 리사이클러뷰 어딥터
    private Intent intent;                                                                      // shoplist에서 넘겨준 shop 정보를 받기 위한 intent
    private Shop shop;
    private TextView shopName_Txt, mainMenu1Name_Txt, mainMenu1Price_Txt, mainMenu2Name_Txt, mainMenu2Price_Txt, mainMenu3Name_Txt, mainMenu3Price_Txt;
    private RecyclerView recyclerView_menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_page);

        intent = getIntent();                                                                   // shoplist에서 넘겨준 shop 받음
        shop = (Shop) intent.getSerializableExtra("Shop");

        RecyclerView recyclerView = findViewById(R.id.recyclerView_menuList);                   // 리사이클러뷰 등록

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((this));              // 레이아웃 매니저 등록
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter_MenuList();                                                       // 어댑터 등록
        adapter.setMenuList(shop.getMenuList());
        recyclerView.setAdapter(adapter);

        shopName_Txt = findViewById(R.id.shopName_Txt);
        shopName_Txt.setText(shop.getShopName());
        mainMenu1Name_Txt = findViewById(R.id.mainMenu1Name_Txt);
        mainMenu1Name_Txt.setText(shop.getMainMenus()[0].getName());
        mainMenu1Price_Txt = findViewById(R.id.mainMenu1Price_Txt);
        mainMenu1Price_Txt.setText(Integer.toString(shop.getMainMenus()[0].getPrice()));         // int -> String 변환
        mainMenu2Name_Txt = findViewById(R.id.mainMenu2Name_Txt);
        mainMenu2Name_Txt.setText(shop.getMainMenus()[1].getName());
        mainMenu2Price_Txt = findViewById(R.id.mainMenu2Price_Txt);
        mainMenu2Price_Txt.setText(Integer.toString(shop.getMainMenus()[1].getPrice()));
        mainMenu3Name_Txt = findViewById(R.id.mainMenu3Name_Txt);
        mainMenu3Name_Txt.setText(shop.getMainMenus()[2].getName());
        mainMenu3Price_Txt = findViewById(R.id.mainMenu3Price_Txt);
        mainMenu3Price_Txt.setText(Integer.toString(shop.getMainMenus()[2].getPrice()));

    }
}
