package com.momeokji.moc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.momeokji.moc.Adapters.PagerAdapter_MenuReview;


public class RestaurantInfoFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_info, container, false);

        TextView restaurantPage_restaurantName_txt = view.findViewById(R.id.restaurantInfoPage_restaurantName_txt);
        restaurantPage_restaurantName_txt.setText(((MainActivity) getActivity()).restaurantDATA.KoreanRestaurantList.get(0).getRestaurantName());

        TextView restaurantInfoPage_restaurantRangePrice_txt = view.findViewById(R.id.restaurantInfoPage_restaurantRangePrice_txt);
        restaurantInfoPage_restaurantRangePrice_txt.setText(((MainActivity) getActivity()).restaurantDATA.KoreanRestaurantList.get(0).getMinMaxPrice());

        final ViewPager restaurantInfoPage_viewPager = view.findViewById(R.id.restaurantInfoPage_viewPager);
        restaurantInfoPage_viewPager.setAdapter(new PagerAdapter_MenuReview(getChildFragmentManager(), 1, getActivity()));

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.menuReviewTabBar_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                restaurantInfoPage_viewPager.setCurrentItem(tab.getPosition());
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
