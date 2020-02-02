package com.momeokji.moc.shopList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.momeokji.moc.R;
import com.momeokji.moc.RecyclerViewAdapter.Adapter_shopList;
import com.momeokji.moc.ShopPage;
import com.momeokji.moc.data.Menu;
import com.momeokji.moc.data.Shop;
import com.momeokji.moc.koreanShop.Imjongrye;
import com.momeokji.moc.koreanShop.Myeongpum;
import com.momeokji.moc.koreanShop.Nondureong;

import java.util.ArrayList;

public class KoreanShopList extends AppCompatActivity {

    private Adapter_shopList adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korean_shop_list);

        RecyclerView recyclerView_shopList = findViewById(R.id.recyclerView_shopList);               // 리사이클러 뷰 등록

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((this));          // 레이아웃 매니저 등록
        recyclerView_shopList.setLayoutManager(linearLayoutManager);

        adapter = new Adapter_shopList();                                                   // 어댑터 등록
        recyclerView_shopList.setAdapter(adapter);

        //////////// 데이터 직접 등록/////////////
        ArrayList<Shop> tmpShopList = new ArrayList<>();
        // 가게 데이터 등록
        Menu[] MainMenus = new Menu[3];
        MainMenus[0] = new Menu("돼지불백", 5000) ;
        MainMenus[1] = new Menu("된장찌개", 5000);
        MainMenus[2] = new Menu("고추장불고기", 5500);
        ArrayList<Menu> NondureongMenuList = new ArrayList<>();
        NondureongMenuList.add(new Menu("돼지불백", 5000));
        NondureongMenuList.add(new Menu("된장찌개", 5000));
        NondureongMenuList.add(new Menu("고추장불고기", 5500));
        tmpShopList.add(new Shop("논두렁갈비", MainMenus , NondureongMenuList));

        final ArrayList<Shop> fianlShopList = tmpShopList;

        adapter.setShopList(fianlShopList);                                                      // 리스트 등록
        ///////////////////////////////////////////


        adapter.setOnItemClickListener(new Adapter_shopList.OnItemClickListener() {              // 리사이클러뷰 ViewHolder 에서의 OnClick을 OnItemClock을 통해 추가 정의
            @Override
            public void OnItemClick(View view, int targetPos) {
                Shop targetShop = fianlShopList.get(targetPos);

                Intent intent = new Intent(KoreanShopList.this, ShopPage.class);            // KoreaShopList -> ShopPage
                Bundle bundle = new Bundle();                                                               // 해당 shop 클래스 데이터를 넘기기 위해 번들 사용
                bundle.putSerializable("Shop", targetShop);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        View chicken = findViewById(R.id.chickenBtn);
        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KoreanShopList.this, ChickenShopList.class);
                startActivity(intent);
            }
        });

        View chinese = findViewById(R.id.chineseBtn);
        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KoreanShopList.this, ChineseShopList.class);
                startActivity(intent);
            }
        });

    }
}
