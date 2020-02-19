package com.momeokji.moc;


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
    private int fragmentType;
    private Fragment mainContext;
    private NavigationBarFragment navigationBarFragment;

    public MainContextAndNavigationBarFragment(MainActivity mainActivity, int fragmentType, Fragment mainContext) {
        this.mainActivity = mainActivity;
        this.fragmentType = fragmentType;
        this.mainContext = mainContext;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_context_and_navigation_bar, container, false);

        this.mainActivity.setFragmentManagers(1, getChildFragmentManager());

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        switch (fragmentType) {
            case MAIN_CONTEXT_WITHOUT_LOCATION_SELECT:
                mainActivity.setLocationSelect(null);
                mainActivity.setMainContext(mainContext);
                fragmentTransaction.add(R.id.mainContextWithLocationSelect_frameLayout, mainContext);
                this.mainActivity.setFragmentManagers(2, null);
                break;
            case MAIN_CONTEXT_WITH_LOCATION_SELECT:
                MainContextWithLocationSelectFragment mainContextWithLocationSelectFragment = new MainContextWithLocationSelectFragment(mainActivity, mainContext);
                fragmentTransaction.add(R.id.mainContextWithLocationSelect_frameLayout, mainContextWithLocationSelectFragment);
        }
        navigationBarFragment = new NavigationBarFragment();
        mainActivity.setNavigationBar(navigationBarFragment);
        fragmentTransaction.add(R.id.navigationBar_frameLayout, navigationBarFragment).commit();


        return view;
    }
}
