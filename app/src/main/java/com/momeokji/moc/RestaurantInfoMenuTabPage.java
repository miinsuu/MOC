package com.momeokji.moc;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.momeokji.moc.Adapters.PagerAdapter_MenuTabPage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class RestaurantInfoMenuTabPage extends Fragment {

    private ViewPager restaurantInfo_menuTabPage_viewPager;
    private TabLayout tabLayout;
    private ArrayList<Map> AllMenuList ;
    private MainActivity mainActivity;
    private Context mcontext;

    public  RestaurantInfoMenuTabPage() {

    }
    public RestaurantInfoMenuTabPage(MainActivity mActivity) {
        mainActivity = mActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStage) {
        View view = inflater.inflate(R.layout.fragment_restaurant_info_menu_tab_page, container, false);

        this.AllMenuList = mainActivity.restaurantDATA.selectedRestaurant.getMenuList();
//        this.AllMenuList = ((MainActivity)getActivity()).restaurantDATA.selectedRestaurant.getMenuList();

        String menuCategoryName = "";
        Set<String> menuCategoryNameSet;


        restaurantInfo_menuTabPage_viewPager = view.findViewById(R.id.restaurantInfo_menuTabPage_viewPager);
        restaurantInfo_menuTabPage_viewPager.setAdapter(new PagerAdapter_MenuTabPage(getChildFragmentManager(),1,mainActivity));

        tabLayout = view.findViewById(R.id.menuclassificationTabBar_layout);
        for(int n = 0; n < AllMenuList.size(); n++){
            menuCategoryNameSet = AllMenuList.get(n).keySet();
            for(String name : menuCategoryNameSet)
                menuCategoryName = name;
            tabLayout.addTab(tabLayout.newTab().setText(menuCategoryName));
        }


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
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;

//        if (context instanceof MainActivity) {
//            mainActivity = (MainActivity) context;
//        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mcontext = null;
    }

 */
}
