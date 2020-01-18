package com.momeokji.moc.shopList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.momeokji.moc.R;
import com.momeokji.moc.chineseShop.Chwihyang;

public class ChineseShopList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinese_shop_list);

        View chwihyang = findViewById(R.id.chwihyangBtn);
        chwihyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChineseShopList.this, Chwihyang.class);
                startActivity(intent);
            }
        });

        View korean = findViewById(R.id.koreanBtn);
        korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChineseShopList.this, KoreanShopList.class);
                startActivity(intent);
            }
        });

//        View chinese = findViewById(R.id.chineseBtn);
//        chinese.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ChineseShopList.this, ChineseShopList.class);
//                startActivity(intent);
//            }
//        });
    }
}
