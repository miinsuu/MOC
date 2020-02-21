package com.momeokji.moc;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class MenuTabPage extends Fragment {

    private ViewPager restaurantInfo_menuTabPage_viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStage) {
        View view = inflater.inflate(R.layout.fragment_menu_tab_page, container, false);

        restaurantInfo_menuTabPage_viewPager = view.findViewById(R.id.restaurantInfo_menuTabPage_viewPager);
        restaurantInfo_menuTabPage_viewPager.setAdapter();

    }
}
