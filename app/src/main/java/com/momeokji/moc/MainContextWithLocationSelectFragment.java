package com.momeokji.moc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainContextWithLocationSelectFragment extends Fragment {

    private MainActivity mainActivity;
    private LocationSelectFragment locationSelectFragment;
    private Fragment mainContext;

    public MainContextWithLocationSelectFragment(MainActivity mainActivity, Fragment mainContext) {
        this.mainActivity = mainActivity;
        this.mainContext = mainContext;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_context_with_location_select, container, false);
        mainActivity.displayedFragmentManager.fragmentManagers[2] = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        if (getChildFragmentManager().findFragmentByTag(LocationSelectFragment.class.getName()) == null) {
            locationSelectFragment = new LocationSelectFragment();
            mainActivity.displayedFragmentManager.locationSelect = locationSelectFragment;
            fragmentTransaction.add(R.id.locationSelect_frameLayout, locationSelectFragment, locationSelectFragment.getClass().getName());
        }

        Fragment previousMainContext = getChildFragmentManager().findFragmentById(R.id.mainContext_frameLayout);
        if (previousMainContext != null) {
            fragmentTransaction.hide(previousMainContext);
        }

        if (getChildFragmentManager().findFragmentByTag(mainContext.getClass().getName()) == null)
            fragmentTransaction.add(R.id.mainContext_frameLayout, mainContext, mainContext.getClass().getName());
        else {
            fragmentTransaction.show(mainContext);
            fragmentTransaction.detach(mainContext).attach(mainContext);
        }

        fragmentTransaction.commit();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    public void setMainContext(Fragment targetFragment) {
        this.mainContext = targetFragment;
    }
    public Fragment getMainContext() {
        return this.mainContext;
    }

    public LocationSelectFragment getLocationSelectFragment() {
        return this.locationSelectFragment;
    }
}
