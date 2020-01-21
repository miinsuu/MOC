package com.momeokji.moc.shopList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.momeokji.moc.R;
import com.momeokji.moc.chineseShop.Chwihyang;
import com.momeokji.moc.japaneseShop.Mendong;

public class JapaneseShopList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_japanese_shop_list);

        View mendong = findViewById(R.id.mendongBtn);
        mendong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JapaneseShopList.this, Mendong.class);
                startActivity(intent);
            }
        });

        View korean = findViewById(R.id.koreanBtn);
        korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JapaneseShopList.this, KoreanShopList.class);
                startActivity(intent);
            }
        });

        View chinese = findViewById(R.id.chineseBtn);
        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JapaneseShopList.this, ChineseShopList.class);
                startActivity(intent);
            }
        });
    }

}
