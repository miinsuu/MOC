package com.momeokji.moc;


import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainContextAndNavigationBarFragment extends Fragment {

    final static private int MAIN_CONTEXT_WITHOUT_LOCATION_SELECT = 0;
    final static private int MAIN_CONTEXT_WITH_LOCATION_SELECT = 1;

    private MainActivity mainActivity;
    private Fragment mainContextWithLocationSelect, previousMainContextWithLocationSelect;
    private NavigationBarFragment navigationBarFragment;

    public MainContextAndNavigationBarFragment(MainActivity mainActivity, Fragment mainContextWithLocationSelect) {
        this.mainActivity = mainActivity;
        this.mainContextWithLocationSelect = mainContextWithLocationSelect;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_context_and_navigation_bar, container, false);

        mainActivity.displayedFragmentManager.fragmentManagers[1] = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        if (getChildFragmentManager().findFragmentByTag(MainContextWithLocationSelectFragment.class.getName()) == null)
            fragmentTransaction.add(R.id.mainContextWithLocationSelect_frameLayout, mainContextWithLocationSelect, mainContextWithLocationSelect.getClass().getName());

        if (getChildFragmentManager().findFragmentByTag(NavigationBarFragment.class.getName()) == null) {
            navigationBarFragment = new NavigationBarFragment();
            mainActivity.displayedFragmentManager.navigationBar = navigationBarFragment;
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
            previousMainContextWithLocationSelect = this.mainContextWithLocationSelect;
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
