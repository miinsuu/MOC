package com.momeokji.moc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.momeokji.moc.Adapters.PagerAdapter_MenuTabPage;


public class RestaurantInfoMenuTabPage extends Fragment {

    private ViewPager restaurantInfo_menuTabPage_viewPager;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStage) {
        View view = inflater.inflate(R.layout.fragment_restaurant_info_menu_tab_page, container, false);

        restaurantInfo_menuTabPage_viewPager = view.findViewById(R.id.restaurantInfo_menuTabPage_viewPager);
        restaurantInfo_menuTabPage_viewPager.setAdapter(new PagerAdapter_MenuTabPage(getChildFragmentManager(),1,getActivity()));

        tabLayout = view.findViewById(R.id.menuclassificationTabBar_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                restaurantInfo_menuTabPage_viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;

    }
}
