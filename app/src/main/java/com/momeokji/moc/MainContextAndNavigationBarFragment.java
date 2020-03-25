package com.momeokji.moc;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.momeokji.moc.Helper.BottomNavigationHelper;
import com.momeokji.moc.Helper.Constants;

import static com.momeokji.moc.MainActivity.displayedFragmentManager;


public class MainContextAndNavigationBarFragment extends Fragment {

    private static MainContextAndNavigationBarFragment mainContextAndNavigationBarFragment = null;
    private Fragment mainContextWithLocationSelect;
    private BottomNavigationView bottomNavigationView;

    public MainContextAndNavigationBarFragment() {
    }
    public MainContextAndNavigationBarFragment(Fragment mainContextWithLocationSelect) {
        this.mainContextWithLocationSelect = mainContextWithLocationSelect;
    }

    public static MainContextAndNavigationBarFragment getInstance() {
        if (mainContextAndNavigationBarFragment == null) {
            Log.e("Usage Error", "객체의 속성값을 바꿀 때에만 getInstance()를 사용해야 합니다");
            return null;
        }
        return mainContextAndNavigationBarFragment;
    }
    public static MainContextAndNavigationBarFragment getInstance(Fragment mainContextWithLocationSelect) {
        if (mainContextAndNavigationBarFragment == null)
            mainContextAndNavigationBarFragment = new MainContextAndNavigationBarFragment();
        mainContextAndNavigationBarFragment.mainContextWithLocationSelect = mainContextWithLocationSelect;
        return mainContextAndNavigationBarFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_context_and_navigation_bar, container, false);

        bottomNavigationView = view.findViewById(R.id.bottomNavigationView_bottomNavigationView);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(MakeOnNavigationItemSelectedListener());

        displayedFragmentManager.fragmentManagers[1] = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        if (getChildFragmentManager().findFragmentByTag(MainContextWithLocationSelectFragment.class.getName()) == null)
            fragmentTransaction.add(R.id.mainContextWithLocationSelect_frameLayout, mainContextWithLocationSelect, mainContextWithLocationSelect.getClass().getName());

        fragmentTransaction.commit();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    public void setMainContextWithLocationSelect(Fragment mainContextWithLocationSelect) {
        if (this.mainContextWithLocationSelect == mainContextWithLocationSelect) {
            this.mainContextWithLocationSelect = mainContextWithLocationSelect;
        }
    }
    public Fragment getMainContextWithLocationSelect() {
        return this.mainContextWithLocationSelect;
    }

    public BottomNavigationView getBottomNavigationView() {
        return this.bottomNavigationView;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener MakeOnNavigationItemSelectedListener() {

        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int animationDirection;

                Fragment curr_Level1_Fragment = displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout);

                switch (menuItem.getItemId()) {
                    case R.id.navigationBar_home_btn:
                        animationDirection = Constants.ANIMATION_DIRECT.TO_LEFT;

                        // 현재 frame에 LocationSelectFragment가 없는 frame 이라면 MainContextWithLocationSelectFragment로 교체
                        if (!(curr_Level1_Fragment instanceof MainContextWithLocationSelectFragment)) {
                            //MainContextAndNavigationBarFragment.getInstance().setMainContextWithLocationSelect(MainContextWithLocationSelectFragment.getInstance());
                            displayedFragmentManager.ReplaceFragment(1, MainContextWithLocationSelectFragment.getInstance(HomeFragment.getInstance()), animationDirection);
                        }   // frame은 있으나 메인컨텐츠가 다른 경우 컨텐츠만 HomeFragment로 교체
                        else if (!(displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof HomeFragment)) {
                            //MainContextWithLocationSelectFragment.getInstance().setMainContext(HomeFragment.getInstance());
                            displayedFragmentManager.ReplaceFragment(2, HomeFragment.getInstance(), animationDirection);
                        }
                        break;

                    case R.id.navigationBar_shop_btn:
                        if (displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof HomeFragment)
                            animationDirection = Constants.ANIMATION_DIRECT.TO_RIGHT;
                        else
                            animationDirection = Constants.ANIMATION_DIRECT.TO_LEFT;

                        // 현재 frame에 LocationSelectFragment가 없는 frame 이라면 MainContextWithLocationSelectFragment로 교체
                        if (!(curr_Level1_Fragment instanceof MainContextWithLocationSelectFragment)) {
                            //MainContextAndNavigationBarFragment.getInstance().setMainContextWithLocationSelect(MainContextWithLocationSelectFragment.getInstance());
                            displayedFragmentManager.ReplaceFragment(1, MainContextWithLocationSelectFragment.getInstance(RestaurantListFragment.getInstance()), animationDirection);
                        }   // frame은 있으나 메인컨텐츠가 다른 경우 컨텐츠만 RestaurantListFragment로 교체
                        else if (!(displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof RestaurantListFragment)) {
                            //MainContextWithLocationSelectFragment.getInstance().setMainContext(RestaurantListFragment.getInstance());
                            displayedFragmentManager.ReplaceFragment(2, RestaurantListFragment.getInstance(), animationDirection);
                        }
                        break;

                    case R.id.navigationBar_roulette_btn:
                        if (curr_Level1_Fragment instanceof MoreInfoFragment)
                            animationDirection = Constants.ANIMATION_DIRECT.TO_LEFT;
                        else
                            animationDirection = Constants.ANIMATION_DIRECT.TO_RIGHT;

                        if (!(curr_Level1_Fragment instanceof RouletteFragment))
                            //MainContextAndNavigationBarFragment.getInstance().setMainContextWithLocationSelect(RouletteFragment.getInstance());
                            displayedFragmentManager.ReplaceFragment(1, RouletteFragment.getInstance(), animationDirection);
                        break;

                    case R.id.navigationBar_more_btn:
                        animationDirection = Constants.ANIMATION_DIRECT.TO_RIGHT;


                        if (!(curr_Level1_Fragment instanceof MoreInfoFragment))
                            //MainContextAndNavigationBarFragment.getInstance().setMainContextWithLocationSelect(MoreInfoFragment.getInstance());
                            displayedFragmentManager.ReplaceFragment(1, MoreInfoFragment.getInstance(), animationDirection);
                        break;
                    default:
                }
                return true;
            }
        };
    }
}
