package com.momeokji.moc;


import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.momeokji.moc.MainActivity.displayedFragmentManager;


public class MainContextAndNavigationBarFragment extends Fragment {

    private static MainContextAndNavigationBarFragment mainContextAndNavigationBarFragment = null;
    private Fragment mainContextWithLocationSelect;
    private NavigationBarFragment navigationBarFragment;

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

        displayedFragmentManager.fragmentManagers[1] = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        if (getChildFragmentManager().findFragmentByTag(MainContextWithLocationSelectFragment.class.getName()) == null)
            fragmentTransaction.add(R.id.mainContextWithLocationSelect_frameLayout, mainContextWithLocationSelect, mainContextWithLocationSelect.getClass().getName());

        if (getChildFragmentManager().findFragmentByTag(NavigationBarFragment.class.getName()) == null) {
            navigationBarFragment = NavigationBarFragment.getInstance();
            displayedFragmentManager.navigationBar = navigationBarFragment;
            fragmentTransaction.add(R.id.navigationBar_frameLayout, navigationBarFragment, navigationBarFragment.getClass().getName());
        }

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

    public NavigationBarFragment getNavigationBarFragment() {
        return this.navigationBarFragment;
    }
}
