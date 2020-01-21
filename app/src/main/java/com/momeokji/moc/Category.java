package com.momeokji.moc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.momeokji.moc.shopList.ChickenShopList;
import com.momeokji.moc.shopList.ChineseShopList;
import com.momeokji.moc.shopList.KoreanShopList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_layout);
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(Category.this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

        View korean = findViewById(R.id.koreanBtn);
        korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category.this, KoreanShopList.class);
                startActivity(intent);
            }
        });

        View chinese = findViewById(R.id.chineseBtn);
        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category.this, ChineseShopList.class);
                startActivity(intent);
            }
        });

        View chicken = findViewById(R.id.chickenBtn);
        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category.this, ChickenShopList.class);
                startActivity(intent);
            }
        });

    }

}
