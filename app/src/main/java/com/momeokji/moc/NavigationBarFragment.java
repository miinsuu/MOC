package com.momeokji.moc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.momeokji.moc.Helper.BottomNavigationHelper;

public class NavigationBarFragment extends Fragment {
    final static private int MAIN_CONTEXT_WITHOUT_LOCATION_SELECT = 0;
    final static private int MAIN_CONTEXT_WITH_LOCATION_SELECT = 1;
    final static private int ANIMATION_DIRECT_RIGHT = 0;
    final static private int ANIMATION_DIRECT_LEFT = 1;

    private BottomNavigationView bottomNavigationView;
    private MainActivity mainActivity = null;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener;

    public static NavigationBarFragment newInstance() {
        return new NavigationBarFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();


        onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int animationDirection;

                MainContextAndNavigationBarFragment mainContextAndNavigationBarFragment
                        = (MainContextAndNavigationBarFragment) mainActivity.displayedFragmentManager.fragmentManagers[0].findFragmentByTag(MainContextAndNavigationBarFragment.class.getName());
                MainContextWithLocationSelectFragment constructedMainContextWithLocationSelectFragment
                    = (MainContextWithLocationSelectFragment) mainActivity.displayedFragmentManager.fragmentManagers[1].findFragmentByTag(MainContextWithLocationSelectFragment.class.getName());
                Fragment curr_Level1_Fragment = mainActivity.displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout);

                switch (menuItem.getItemId()) {
                    case R.id.navigationBar_home_btn:
                        animationDirection = ANIMATION_DIRECT_LEFT;

                        HomeFragment constructedHomeFragment = (HomeFragment) mainActivity.displayedFragmentManager.fragmentManagers[2].findFragmentByTag(HomeFragment.class.getName());
                        if (constructedHomeFragment == null) {
                            constructedHomeFragment = new HomeFragment();
                        }

                        // 현재 frame에 LocationSelectFragment가 없는 frame 이라면 MainContextWithLocationSelectFragment로 교체
                        if (!(curr_Level1_Fragment instanceof MainContextWithLocationSelectFragment)) {
                            mainContextAndNavigationBarFragment.setMainContextWithLocationSelect(constructedMainContextWithLocationSelectFragment);
                            constructedMainContextWithLocationSelectFragment.setMainContext(constructedHomeFragment);
                            mainActivity.ReplaceFragment(1, constructedMainContextWithLocationSelectFragment, animationDirection, true);
                        }   // frame은 있으나 메인컨텐츠가 다른 경우 컨텐츠만 HomeFragment로 교체
                        else if (!(mainActivity.displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof HomeFragment)) {
                            constructedMainContextWithLocationSelectFragment.setMainContext(constructedHomeFragment);
                            mainActivity.ReplaceFragment(2, constructedHomeFragment, animationDirection, true);
                        }
                        break;

                    case R.id.navigationBar_shop_btn:
                        if (mainActivity.displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof HomeFragment)
                            animationDirection = ANIMATION_DIRECT_RIGHT;
                        else
                            animationDirection = ANIMATION_DIRECT_LEFT;

                        RestaurantListFragment constructedRestaurantListFragment = (RestaurantListFragment) mainActivity.displayedFragmentManager.fragmentManagers[2].findFragmentByTag(RestaurantListFragment.class.getName());
                        if (constructedRestaurantListFragment == null) {
                            constructedRestaurantListFragment = new RestaurantListFragment();
                        }

                        // 현재 frame에 LocationSelectFragment가 없는 frame 이라면 MainContextWithLocationSelectFragment로 교체
                        if (!(curr_Level1_Fragment instanceof MainContextWithLocationSelectFragment)) {
                            mainContextAndNavigationBarFragment.setMainContextWithLocationSelect(constructedMainContextWithLocationSelectFragment);
                            constructedMainContextWithLocationSelectFragment.setMainContext(constructedRestaurantListFragment);
                            mainActivity.ReplaceFragment(1, constructedMainContextWithLocationSelectFragment, animationDirection, true);
                        }   // frame은 있으나 메인컨텐츠가 다른 경우 컨텐츠만 RestaurantListFragment로 교체
                        else if (!(mainActivity.displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof RestaurantListFragment)) {
                            constructedMainContextWithLocationSelectFragment.setMainContext(constructedRestaurantListFragment);
                            mainActivity.ReplaceFragment(2, constructedRestaurantListFragment, animationDirection, true);
                        }
                        break;

                    case R.id.navigationBar_roulette_btn:
                        if (curr_Level1_Fragment instanceof MoreInfoFragment)
                            animationDirection = ANIMATION_DIRECT_LEFT;
                        else
                            animationDirection = ANIMATION_DIRECT_RIGHT;

                        RouletteFragment constructedRouletteFragment = (RouletteFragment) mainActivity.displayedFragmentManager.fragmentManagers[1].findFragmentByTag(RouletteFragment.class.getName());
                        if (constructedRouletteFragment == null) {
                            constructedRouletteFragment = new RouletteFragment();
                        }

                        if (!(curr_Level1_Fragment instanceof RouletteFragment))
                            mainContextAndNavigationBarFragment.setMainContextWithLocationSelect(constructedRouletteFragment);
                            mainActivity.ReplaceFragment(1, constructedRouletteFragment, animationDirection, true);
                        break;

                    case R.id.navigationBar_more_btn:
                        animationDirection = ANIMATION_DIRECT_RIGHT;

                        MoreInfoFragment constructedMoreInfoFragment = (MoreInfoFragment) mainActivity.displayedFragmentManager.fragmentManagers[1].findFragmentByTag(MoreInfoFragment.class.getName());
                        if (constructedMoreInfoFragment == null) {
                            constructedMoreInfoFragment = new MoreInfoFragment();
                        }

                        if (!(curr_Level1_Fragment instanceof MoreInfoFragment))
                            mainActivity.ReplaceFragment(1, constructedMoreInfoFragment, animationDirection, true);
                        break;
                    default:
                }
                return true;
            }
        };


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_navigation_bar, container, false);

        bottomNavigationView = view.findViewById(R.id.bottomNavigationView_bottomNavigationView);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        return view;
    }

    public BottomNavigationView getBottomNavigationView() {
        return this.bottomNavigationView;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener getOnNavigationItemSelectedListener() {
        return this.onNavigationItemSelectedListener;
    }
}
