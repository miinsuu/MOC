package com.momeokji.moc.shopList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.momeokji.moc.Opening;
import com.momeokji.moc.R;
import com.momeokji.moc.koreanShop.Nonddureong;

public class KoreanShopList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korean_shop_list);

        View nonddureong = findViewById(R.id.nonddureongBtn);
        nonddureong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KoreanShopList.this, Nonddureong.class);
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
