package com.momeokji.moc.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.momeokji.moc.MainActivity;
import com.momeokji.moc.RestaurantInfoMenuTabPage;
import com.momeokji.moc.RestaurantInfoReviewTabPage;
import com.momeokji.moc.data.Restaurant;
import com.momeokji.moc.data.Review;

import java.util.ArrayList;

public class PagerAdapter_MenuReview extends FragmentPagerAdapter {
    Context context;
    Restaurant selectedRestaurant;
    ArrayList< Review > reviews;

    public PagerAdapter_MenuReview(FragmentManager fm, int behavior, Context context) {
        super(fm,behavior);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RestaurantInfoMenuTabPage((MainActivity)context);
            case 1:
                return new RestaurantInfoReviewTabPage((MainActivity)context);
            default:
                return null;

        }
    }
    @Override
    public int getCount() {
        return 2;
    }

}