package com.momeokji.moc.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
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

    Context context;

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
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
/*    private Context context = null;




    public PagerAdapter_RestaurantListPage(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        View view = null;
        if (context != null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.page_restaurant_list, container, false);

        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 8; //TODO 상수 클래스 만들어서 CategryNum 으로 변경
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View) object);
    }*/


}
