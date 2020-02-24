package com.momeokji.moc.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.momeokji.moc.MenuClassificationTab;

public class PagerAdapter_MenuTabPage extends FragmentPagerAdapter {

    private Context context = null;

    public PagerAdapter_MenuTabPage(FragmentManager fm, int behavior, Context context) {
        super(fm,behavior);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MenuClassificationTab();
            case 1:
                return new MenuClassificationTab();
            case 2:
                return new MenuClassificationTab();
            default:
                return null;

        }
    }
    @Override
    public int getCount() {
        return 3;
    }
}
