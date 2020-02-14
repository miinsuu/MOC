package com.momeokji.moc.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.momeokji.moc.MenuTabPage;
import com.momeokji.moc.ReviewTabPage;

public class PagerAdapter_MenuReview extends FragmentPagerAdapter {
    Context context;

    public PagerAdapter_MenuReview(FragmentManager fm, int behavior, Context context) {
        super(fm,behavior);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MenuTabPage();
            case 1:
                return new ReviewTabPage();
            default:
                return null;

        }
    }
    @Override
    public int getCount() {
        return 2;
    }

}