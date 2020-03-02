package com.momeokji.moc.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.momeokji.moc.MainActivity;
import com.momeokji.moc.R;
import com.momeokji.moc.RestaurantListPage;

public class PagerAdapter_RestaurantListPage extends FragmentPagerAdapter {

    final static private int CategoryNum = 8; // TODO 민수와 데이터 받는 법 상의 후 getItem 메소드 수정
    private Context context = null;
    private boolean isEnableSwipe = false;


    public PagerAdapter_RestaurantListPage(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.context = context;
    }
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new RestaurantListPage(((MainActivity)context).restaurantDATA.getAllList());
            case 1:
                return new RestaurantListPage(((MainActivity)context).restaurantDATA.KoreanRestaurantList);
            case 2:
                return new RestaurantListPage(((MainActivity)context).restaurantDATA.ChineseRestaurantList);
            case 3:
                return new RestaurantListPage(((MainActivity)context).restaurantDATA.JapaneseRestaurantList);
            case 4:
                return new RestaurantListPage(((MainActivity)context).restaurantDATA.WesternRestaurantList);
            case 5:
                return new RestaurantListPage(((MainActivity)context).restaurantDATA.SnackRestaurantList);
            case 6:
                return new RestaurantListPage(((MainActivity)context).restaurantDATA.ChickenRestaurantList);
            case 7:
                return new RestaurantListPage(((MainActivity)context).restaurantDATA.AsianRestaurantList);
            case 8:
                return new RestaurantListPage(((MainActivity)context).restaurantDATA.FastRestaurantList);
//            case 9:
//                return new RestaurantListPage(((MainActivity)context).restaurantDATA.NightRestaurantList);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return CategoryNum;
    }
}
