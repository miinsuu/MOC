package com.momeokji.moc.shopList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.momeokji.moc.Opening;
import com.momeokji.moc.R;
import com.momeokji.moc.koreanShop.Imjongrye;
import com.momeokji.moc.koreanShop.Myeongpum;
import com.momeokji.moc.koreanShop.Nondureong;

public class KoreanShopList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korean_shop_list);

        View nondureong = findViewById(R.id.nondureongBtn);
        nondureong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KoreanShopList.this, Nondureong.class);
                startActivity(intent);
            }
        });

        View myeongpum = findViewById(R.id.myeongpumBtn);
        myeongpum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KoreanShopList.this, Myeongpum.class);
                startActivity(intent);
            }
        });

        View imjongrye = findViewById(R.id.imjongryeBtn);
        imjongrye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KoreanShopList.this, Imjongrye.class);
                startActivity(intent);
            }
        });

//        View korean = findViewById(R.id.koreanBtn);
//        korean.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(KoreanShopList.this, KoreanShopList.class);
//                startActivity(intent);
//            }
//        });

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
