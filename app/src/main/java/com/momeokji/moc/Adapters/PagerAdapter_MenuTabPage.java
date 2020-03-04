package com.momeokji.moc.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.momeokji.moc.MainActivity;
import com.momeokji.moc.MenuClassificationTab;
import com.momeokji.moc.data.Menu;
import com.momeokji.moc.data.Restaurant;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class PagerAdapter_MenuTabPage extends FragmentPagerAdapter {

    private Context context = null;
    private Restaurant selectedRestaurant;
    private ArrayList<Map> AllMenuList;

    public PagerAdapter_MenuTabPage(FragmentManager fm, int behavior, Context context) {
        super(fm,behavior);
        this.context = context;
        this.selectedRestaurant = ((MainActivity) context).restaurantDATA.selectedRestaurant;
        this.AllMenuList = ((MainActivity) context).restaurantDATA.selectedRestaurant.getMenuList();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ArrayList<Menu> tempArray = new ArrayList<>();
                for(int i = 0; i < 3; i++) {
                    tempArray.add(selectedRestaurant.getMainMenus()[i]);
                }
                return new MenuClassificationTab(tempArray);
            default:
                String menuCategoryName = "";
                Set<String> menuCategoryNameSet = AllMenuList.get(position-1).keySet();
                for(String name : menuCategoryNameSet)
                    menuCategoryName = name;

                return new MenuClassificationTab((ArrayList<Menu>) AllMenuList.get(position-1).get(menuCategoryName));

        }

    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "대표";
            default:
                Set<String> menuCategoryNameSet;
                ArrayList<String> categoryArray = new ArrayList<>();
                for(int n = 0; n < AllMenuList.size(); n++) {
                    menuCategoryNameSet = AllMenuList.get(n).keySet();
                    for (String name : menuCategoryNameSet) {
                        categoryArray.add(name);
                    }
                }
                return categoryArray.get(position-1);

        }
    }


    @Override
    public int getCount() {
        return AllMenuList.size()+1;
    }
}
