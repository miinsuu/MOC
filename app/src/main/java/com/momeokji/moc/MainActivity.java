package com.momeokji.moc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.momeokji.moc.Helper.DisplayedFragmentManager;

import com.momeokji.moc.Helper.FragmentStackManager;
import com.momeokji.moc.data.DATA;
import com.momeokji.moc.data.User;


public class MainActivity extends AppCompatActivity {

    public static MainActivity mainActivity;
    public static DisplayedFragmentManager displayedFragmentManager;
    public static FragmentStackManager fragmentStackManager;
    private FloatingActionButton myList_btn;

    public DATA restaurantDATA;

    public MainActivity() {
        this.mainActivity = this;
        displayedFragmentManager = new DisplayedFragmentManager(this);
        fragmentStackManager = new FragmentStackManager(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantDATA = new DATA();

        /*- 초기 Fragment 등록 -*/
        displayedFragmentManager.fragmentManagers[0] = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = displayedFragmentManager.fragmentManagers[0].beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment, R.anim.anim_slide_in_left_with_main_fragment, R.anim.anim_slide_out_right_with_main_fragment);
        fragmentTransaction.add(R.id.mainActivity_frameLayout, new MainContextAndNavigationBarFragment(this, new MainContextWithLocationSelectFragment(this, new HomeFragment())), MainContextAndNavigationBarFragment.class.getName()).addToBackStack(MainContextAndNavigationBarFragment.class.getName()).commit();

        //* 나의 리스트 버튼 등록 *//
        myList_btn = findViewById(R.id.myList_btn);
        myList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                MyListFragment myListFragment = MyListFragment.getInstance(mainActivity);
                myListFragment.show(fragmentTransaction, MyListFragment.TAG_MY_LIST_FRAGMENT);
            }
        });

    }

    @Override
    public void onBackPressed() {
        this.fragmentStackManager.onBackPressed();
    }

    public void BackToOpening() {
        Intent intent = new Intent(MainActivity.this, Opening.class);
        startActivity(intent);
    }

}