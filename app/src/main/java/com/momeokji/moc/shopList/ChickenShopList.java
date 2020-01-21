package com.momeokji.moc.shopList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.momeokji.moc.R;
import com.momeokji.moc.chickenShop.Real;
import com.momeokji.moc.chineseShop.Chwihyang;

public class ChickenShopList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicken_shop_list);

        View real = findViewById(R.id.realBtn);
        real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChickenShopList.this, Real.class);
                startActivity(intent);
            }
        });

        View korean = findViewById(R.id.koreanBtn);
        korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChickenShopList.this, KoreanShopList.class);
                startActivity(intent);
            }
        });

        View chinese = findViewById(R.id.chineseBtn);
        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChickenShopList.this, ChineseShopList.class);
                startActivity(intent);
            }
        });
    }
}
