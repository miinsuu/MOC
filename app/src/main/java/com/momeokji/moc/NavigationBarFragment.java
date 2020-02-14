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
                        if (!(mainActivity.getCurrPage() instanceof HomeFragment))
                            mainActivity.ReplaceFragment(new HomeFragment());
                        break;
                    case R.id.navigationBar_shop_btn:
                        if (mainActivity.getCurrPage() instanceof RestaurantListFragment){
                            ((RestaurantListFragment)mainActivity.getCurrPage()).setTab(0);
                        }
                        else {
                            mainActivity.ReplaceFragment(new RestaurantListFragment());
                        }
                        break;
                    case R.id.navigationBar_roulette_btn:
                        mainActivity.HideNavigationBar(); // TODO HideNavigationBar 테스트용
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
