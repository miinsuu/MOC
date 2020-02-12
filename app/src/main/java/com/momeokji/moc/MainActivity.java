package com.momeokji.moc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.momeokji.moc.Helper.BottomNavigationHelper;
import com.momeokji.moc.data.DATA;

public class MainActivity extends AppCompatActivity {

    private Fragment currPage;
    private Fragment navigationBar;
    public DATA restaurantDATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantDATA = new DATA();

        /*- 초기 Fragment 등록 -*/
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainActivity_frameLayout, HomeFragment.newInstance());
        //navigationBar = NavigationBarFragment.newInstance();
        //fragmentTransaction.add(R.id.navigationBar_frameLayout, navigationBar);
        fragmentTransaction.commit();
        currPage = navigationBar;

        FloatingActionButton myList_btn = findViewById(R.id.myList_btn);
        myList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView_bottomNavigationView);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener((new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigationBar_home_btn:
                        ReplaceFragment(new HomeFragment());
                        break;
                    case R.id.navigationBar_shop_btn:
                        ReplaceFragment(new RestaurantListFragment());
                        break;
                    case R.id.navigationBar_roulette_btn:
                        break;
                    case R.id.navigationBar_more_btn:
                        break;
                    default:
                }
                return true;
            }
        }));
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
}