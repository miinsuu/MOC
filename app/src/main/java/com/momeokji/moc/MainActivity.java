package com.momeokji.moc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.momeokji.moc.Database.DataListener;
import com.momeokji.moc.Database.DatabaseQueryClass;
import com.momeokji.moc.Helper.BottomNavigationHelper;
import com.momeokji.moc.data.DATA;

public class MainActivity extends AppCompatActivity {

    private Fragment currPage;
    private NavigationBarFragment navigationBar;
    public DATA restaurantDATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantDATA = new DATA();

        /*- 초기 Fragment 등록 -*/
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainActivity_frameLayout, HomeFragment.newInstance());
        navigationBar = NavigationBarFragment.newInstance();
        fragmentTransaction.add(R.id.navigationBar_frameLayout, navigationBar);
        fragmentTransaction.commit();
        currPage = navigationBar;

        FloatingActionButton myList_btn = findViewById(R.id.myList_btn);
        myList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            }
        });
    }

    /*- Fragment 교체 함수 -*/
    public void ReplaceFragment(Fragment targetFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainActivity_frameLayout, targetFragment).commit();
        setCurrPage(targetFragment);
    }

    public void ShowNavigationBar(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animimator_slide_in_bottom, R.animator.animimator_slide_out_bottom);
        fragmentTransaction.show(navigationBar).commit();
    }
    public void HideNavigationBar(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animimator_slide_in_bottom, R.animator.animimator_slide_out_bottom);
        fragmentTransaction.hide(navigationBar).commit();
    }

    public Fragment getCurrPage(){
        return this.currPage;
    }

    public void setCurrPage(Fragment page){
        this.currPage = page;
    }

    public NavigationBarFragment getNavigationBar() { return this.navigationBar; }
}