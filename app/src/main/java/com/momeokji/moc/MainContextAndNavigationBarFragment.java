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
import static com.momeokji.moc.MainActivity.fragmentStackManager;


public class MainContextAndNavigationBarFragment extends Fragment {
    private Fragment mainContent;
    private BottomNavigationView bottomNavigationView;

    public MainContextAndNavigationBarFragment() {
    }
    public MainContextAndNavigationBarFragment(Fragment mainContent) {
        this.mainContent = mainContent;
    }

    public static MainContextAndNavigationBarFragment getInstance(Fragment mainContent) {
            return new MainContextAndNavigationBarFragment(mainContent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_context_and_navigation_bar, container, false);

        bottomNavigationView = view.findViewById(R.id.bottomNavigationView_bottomNavigationView);
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(MakeOnNavigationItemSelectedListener());
        displayedFragmentManager.bottomNavigationView = this.bottomNavigationView;

        displayedFragmentManager.fragmentManagers[1] = getChildFragmentManager();

        if (getChildFragmentManager().findFragmentByTag(MainContextWithLocationSelectFragment.class.getName()) == null) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.mainContent_frameLayout, mainContent, mainContent.getClass().getName());
            fragmentTransaction.addToBackStack(MainContextWithLocationSelectFragment.class.getName());
            fragmentTransaction.commit();
        }

        return view;
    }

    public void setMainContextWithLocationSelect(Fragment mainContent) {
        if (this.mainContent == mainContent) {
            this.mainContent = mainContent;
        }
    }
    public Fragment getMainContextWithLocationSelect() {
        return this.mainContent;
    }

    public BottomNavigationView getBottomNavigationView() {
        return this.bottomNavigationView;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener MakeOnNavigationItemSelectedListener() {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (displayedFragmentManager.getIsAnimating())
                    return false;

                int animationDirection;

                Fragment curr_Level1_Fragment = displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContent_frameLayout);

                switch (menuItem.getItemId()) {
                    case R.id.navigationBar_home_btn:
                        animationDirection = Constants.ANIMATION_DIRECT.TO_LEFT;

                       if (!(curr_Level1_Fragment instanceof HomeFragment)) {
                            displayedFragmentManager.ReplaceFragment(1, HomeFragment.getInstance(), animationDirection);
                            fragmentStackManager.ClearStack();
                        }
                        break;

                    case R.id.navigationBar_shop_btn:
                            animationDirection = Constants.ANIMATION_DIRECT.TO_RIGHT;

                        if (!(curr_Level1_Fragment instanceof RestaurantListFragment)) {
                            displayedFragmentManager.ReplaceFragment(1, RestaurantListFragment.getInstance(), animationDirection);
                        }
                        break;

                    case R.id.navigationBar_roulette_btn:
                            animationDirection = Constants.ANIMATION_DIRECT.TO_RIGHT;

                        if (!(curr_Level1_Fragment instanceof RouletteFragment))
                            displayedFragmentManager.ReplaceFragment(1, RouletteFragment.getInstance(), animationDirection);
                        break;

                    case R.id.navigationBar_more_btn:
                        animationDirection = Constants.ANIMATION_DIRECT.TO_RIGHT;

                        if (!(curr_Level1_Fragment instanceof MoreInfoFragment))
                            displayedFragmentManager.ReplaceFragment(1, MoreInfoFragment.getInstance(), animationDirection);
                        break;
                    default:
                }
                return true;
            }
        };
    }
}
