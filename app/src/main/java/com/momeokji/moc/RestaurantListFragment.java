package com.momeokji.moc;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.momeokji.moc.Adapters.PagerAdapter_RestaurantListPage;


public class RestaurantListFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager restaurantListPage_viewPager;

    private int lastTabPos;

    public RestaurantListFragment() {
        this.lastTabPos = 0;
    }

    public RestaurantListFragment(int initTab) {
        this.lastTabPos = initTab;
    }

    public static RestaurantListFragment getInstance() {
        return new RestaurantListFragment();
    }
    public static RestaurantListFragment getInstance(int initTab) {
            return new RestaurantListFragment(initTab);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        //* 페이저어댑터 등록 *//
        restaurantListPage_viewPager = view.findViewById(R.id.restaurantListPage_viewPager);
        restaurantListPage_viewPager.setAdapter(new PagerAdapter_RestaurantListPage(getChildFragmentManager(), 1, getActivity()));
        restaurantListPage_viewPager.setOffscreenPageLimit(7);
        //------------------------------------------------------//

        //* 탭 레이아웃 설정 *//
        tabLayout = view.findViewById(R.id.categoryTabBar_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                restaurantListPage_viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //--------------------------------------------------------------------//
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        //* 초기 탭 설정
        //    - Fragment가 완전히 생성된 후 설정해줘야 탭 스크롤이 선택한 탭 쪽으로 움직임. 그래서 임시방편으로 딜레이 걸어줌
        setTab(lastTabPos);
        Handler mHander = new Handler();    // TODO 임시방편..... 더 나은 코드 있으면 교체하자
        mHander.postDelayed(new Runnable() {
            @Override
            public void run() {
                tabLayout.setScrollPosition(lastTabPos, 0f, true); // 딜레이 후 setTab을 해주면 0번째 탭에서부터 스크롤되어 눈아픔
            }
        }, 10);
    }

    @Override
    public void onPause() {
        super.onPause();
        lastTabPos = tabLayout.getSelectedTabPosition();
    }

    public void setTab(int tabPos) {
        tabLayout.getTabAt(tabPos).select();
    }

    public void setLastTabPos(int lastTabPos) {
        this.lastTabPos = lastTabPos;
    }
}
