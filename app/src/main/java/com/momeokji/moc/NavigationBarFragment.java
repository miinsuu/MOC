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
                switch (menuItem.getItemId()) {
                    case R.id.navigationBar_home_btn:
                        if (mainActivity.getLocationSelect() == null) {
                            mainActivity.ReplaceMainContextWithLocationSelect(new MainContextWithLocationSelectFragment(mainActivity, new HomeFragment()));
                        }
                        else if (!(mainActivity.getMainContext() instanceof HomeFragment)) {
                            mainActivity.ReplaceMainContext(new HomeFragment());
                        }
                        break;
                    case R.id.navigationBar_shop_btn:
                        if (!(mainActivity.getCurrFragmentType() instanceof MainContextAndNavigationBarFragment)) {
                            mainActivity.ReplaceFragmentType(new MainContextAndNavigationBarFragment((MainActivity) getActivity(), MAIN_CONTEXT_WITH_LOCATION_SELECT, new RestaurantListFragment()));
                        }
                        else {
                            if (mainActivity.getMainContext() instanceof RestaurantListFragment){
                                ((RestaurantListFragment)mainActivity.getMainContext()).setTab(0);
                            }
                            else {
                                mainActivity.ReplaceMainContext(new RestaurantListFragment());
                            }
                        }
                        break;
                    case R.id.navigationBar_roulette_btn:
                        break;
                    case R.id.navigationBar_more_btn:
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
