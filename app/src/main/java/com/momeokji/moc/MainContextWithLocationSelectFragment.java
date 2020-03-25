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

import static com.momeokji.moc.MainActivity.displayedFragmentManager;

public class MainContextWithLocationSelectFragment extends Fragment {

    private static MainContextWithLocationSelectFragment mainContextWithLocationSelectFragment = null;
    private LocationSelectFragment locationSelectFragment;
    private Fragment mainContext;

    public MainContextWithLocationSelectFragment(Fragment mainContext) {
        this.mainContext = mainContext;
    }
    public static MainContextWithLocationSelectFragment getInstance(Fragment mainContext) {
        if (mainContextWithLocationSelectFragment == null)
            mainContextWithLocationSelectFragment = new MainContextWithLocationSelectFragment(mainContext);
        else
            mainContextWithLocationSelectFragment.mainContext = mainContext;
        return mainContextWithLocationSelectFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_context_with_location_select, container, false);
        displayedFragmentManager.fragmentManagers[2] = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        if (getChildFragmentManager().findFragmentByTag(LocationSelectFragment.class.getName()) == null) {
            locationSelectFragment = LocationSelectFragment.getInstance();
            displayedFragmentManager.locationSelect = locationSelectFragment;
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
