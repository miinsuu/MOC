package com.momeokji.moc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainContextWithLocationSelectFragment extends Fragment {

    MainActivity mainActivity;
    private LocationSelectFragment locationSelectFragment;
    private Fragment mainContext;

    public MainContextWithLocationSelectFragment(MainActivity mainActivity, Fragment mainContext) {
        this.mainActivity = mainActivity;
        this.mainContext = mainContext;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_context_with_location_select, container, false);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        locationSelectFragment = new LocationSelectFragment();
        mainActivity.setLocationSelect(locationSelectFragment);
        fragmentTransaction.add(R.id.locationSelect_frameLayout, locationSelectFragment);
        mainActivity.setMainContext(mainContext);
        fragmentTransaction.add(R.id.mainContext_frameLayout, mainContext).commit();

        this.mainActivity.setFragmentManagers(2, getChildFragmentManager());

        return view;
    }

}
