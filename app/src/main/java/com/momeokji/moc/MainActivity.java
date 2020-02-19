package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.momeokji.moc.data.DATA;

public class MainActivity extends AppCompatActivity {

    final static private int MAIN_CONTEXT_WITHOUT_LOCATION_SELECT = 0;
    final static private int MAIN_CONTEXT_WITH_LOCATION_SELECT = 1;
    final static private boolean ABOVE_NAVIGATION_BAR = true;
    final static private boolean ABOVE_PARENT = false;
    final static private int NAVIGATION_BAR_HEIGHT_IN_DP = 56;
    final static private int MY_LIST_BTN_MARGIN_IN_DP = 5;

    private FragmentManager[] fragmentManagers = new FragmentManager[3];

    private Fragment currFragmentType;
    private LocationSelectFragment locationSelect;
    private Fragment mainContext;
    private NavigationBarFragment navigationBar;
    private FloatingActionButton myList_btn;

    public DATA restaurantDATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantDATA = new DATA();

        /*- 초기 Fragment 등록 -*/
        fragmentManagers[0] = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManagers[0].beginTransaction();
        currFragmentType = new MainContextAndNavigationBarFragment(this, MAIN_CONTEXT_WITH_LOCATION_SELECT, new HomeFragment());
        fragmentTransaction.add(R.id.mainActivity_frameLayout, currFragmentType);
        fragmentTransaction.commit();

        myList_btn = findViewById(R.id.myList_btn);
        myList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            }
        });
        setMyListBtnPosition(ABOVE_NAVIGATION_BAR);
    }

    /*- Fragment 교체 함수 -*/
    public boolean ReplaceFragmentType(Fragment targetFragment) {
        if (!(targetFragment instanceof MainContextAndNavigationBarFragment || targetFragment instanceof MainContextAndNoNavigationBarFragment))
            return false;

        if (targetFragment instanceof  MainContextAndNavigationBarFragment) {
            setMyListBtnPosition(ABOVE_NAVIGATION_BAR);
        }
        else {
            setMyListBtnPosition(ABOVE_PARENT);
        }
        FragmentTransaction fragmentTransaction = fragmentManagers[0].beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_right_with_main_fragment, R.animator.animator_slide_out_left_with_main_fragment);
        fragmentTransaction.replace(R.id.mainActivity_frameLayout, targetFragment).commit();
        setCurrFragmentType(targetFragment);
        return true;
    }
    public boolean ReplaceMainContextWithLocationSelect(Fragment targetFragment) {
        if (!(this.currFragmentType instanceof MainContextAndNavigationBarFragment))
            return false;
        if (!(targetFragment instanceof MainContextWithLocationSelectFragment))
            setLocationSelect(null);

        FragmentTransaction fragmentTransaction = fragmentManagers[1].beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_right_with_main_fragment, R.animator.animator_slide_out_left_with_main_fragment);
        fragmentTransaction.replace(R.id.mainContextWithLocationSelect_frameLayout, targetFragment).commit();
        return true;
    }
    public boolean ReplaceMainContext(Fragment targetFragment) {
        FragmentTransaction fragmentTransaction;
        if (this.locationSelect == null) {
            fragmentTransaction = fragmentManagers[1].beginTransaction();
        }
        else {
            fragmentTransaction = fragmentManagers[2].beginTransaction();
        }
        fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_right_with_main_fragment, R.animator.animator_slide_out_left_with_main_fragment);
        fragmentTransaction.replace(R.id.mainContext_frameLayout, targetFragment).commit();
        setMainContext(targetFragment);
        return true;
    }

    public void setMyListBtnPosition(boolean type) {
        ConstraintLayout.LayoutParams mLayoutParams = (ConstraintLayout.LayoutParams) myList_btn.getLayoutParams();
        if (type == ABOVE_NAVIGATION_BAR) {
            mLayoutParams.bottomMargin = (int) ((NAVIGATION_BAR_HEIGHT_IN_DP + MY_LIST_BTN_MARGIN_IN_DP) * getResources().getDisplayMetrics().density);
            myList_btn.setLayoutParams(mLayoutParams);
        }
        else {
            mLayoutParams.bottomMargin = (int) (MY_LIST_BTN_MARGIN_IN_DP * getResources().getDisplayMetrics().density);
            myList_btn.setLayoutParams(mLayoutParams);
        }
    }

    public Fragment getCurrFragmentType() {
        return this.currFragmentType;
    }
    public void setCurrFragmentType(Fragment currFragmentType) {
        this.currFragmentType = currFragmentType;
    }

    public Fragment getMainContext() {
        return this.mainContext;
    }
    public void setMainContext(Fragment mainContext) {
        this.mainContext = mainContext;
    }

    public NavigationBarFragment getNavigationBar() {
        return this.navigationBar;
    }
    public void setNavigationBar(NavigationBarFragment navigationBar) {
        this.navigationBar = navigationBar;
    }

    public LocationSelectFragment getLocationSelect() {
        return this.locationSelect;
    }
    public void setLocationSelect(LocationSelectFragment locationSelect) {
        this.locationSelect = locationSelect;
    }

    public void setFragmentManagers(int index, FragmentManager fragmentManager) {
        this.fragmentManagers[index] = fragmentManager;
    }
}