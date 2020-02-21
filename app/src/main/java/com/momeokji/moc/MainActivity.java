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
import com.momeokji.moc.Helper.FragmentStackManager;
import com.momeokji.moc.data.DATA;

public class MainActivity extends AppCompatActivity {

    final static private int MAIN_CONTEXT_WITHOUT_LOCATION_SELECT = 0;
    final static private int MAIN_CONTEXT_WITH_LOCATION_SELECT = 1;
    final static private boolean ABOVE_NAVIGATION_BAR = true;
    final static private boolean ABOVE_PARENT = false;
    final static private int NAVIGATION_BAR_HEIGHT_IN_DP = 56;
    final static private int MY_LIST_BTN_MARGIN_IN_DP = 5;

    public DisplayedFragmentManager displayedFragmentManager = new DisplayedFragmentManager(this);
    private FloatingActionButton myList_btn;

    public DATA restaurantDATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantDATA = new DATA();

        /*- 초기 Fragment 등록 -*/
        displayedFragmentManager.fragmentManagers[0] = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = displayedFragmentManager.fragmentManagers[0].beginTransaction();
        displayedFragmentManager.displayedFragments[0] = new MainContextAndNavigationBarFragment(this, MAIN_CONTEXT_WITH_LOCATION_SELECT, new HomeFragment());
        fragmentTransaction.add(R.id.mainActivity_frameLayout, displayedFragmentManager.displayedFragments[0]).commit();

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

    public boolean ReplaceFragment(int level, Fragment targetFragment) {
        FragmentTransaction fragmentTransaction = displayedFragmentManager.fragmentManagers[level].beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_right_with_main_fragment, R.animator.animator_slide_out_left_with_main_fragment);

        displayedFragmentManager.displayedFragments[level] = targetFragment;
        switch (level) {
            case 0:

                if (targetFragment instanceof  MainContextAndNavigationBarFragment) {
                    setMyListBtnPosition(ABOVE_NAVIGATION_BAR);
                }
                else {
                    setMyListBtnPosition(ABOVE_PARENT);
                    displayedFragmentManager.fragmentManagers[1] = null;
                    displayedFragmentManager.fragmentManagers[2] = null;
                }
                displayedFragmentManager.fragmentStackManager.PushFragment(level, displayedFragmentManager.fragmentManagers[level].findFragmentById(R.id.mainActivity_frameLayout));
                fragmentTransaction.replace(R.id.mainActivity_frameLayout, targetFragment).commit();

                break;
            case 1:
                if (displayedFragmentManager.displayedFragments[0] instanceof MainContextAndNavigationBarFragment)
                    ((MainContextAndNavigationBarFragment)displayedFragmentManager.displayedFragments[0]).setMainContext(targetFragment);   //TODO try-catch 추가하기
                if (!(targetFragment instanceof MainContextWithLocationSelectFragment)) {
                    displayedFragmentManager.fragmentManagers[2] = null;
                    displayedFragmentManager.navigationBar = null;
                }

                displayedFragmentManager.fragmentStackManager.PushFragment(level, displayedFragmentManager.fragmentManagers[level].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout));
                fragmentTransaction.replace(R.id.mainContextWithLocationSelect_frameLayout, targetFragment).commit();

                break;
            case 2:
                if (displayedFragmentManager.displayedFragments[0] instanceof MainContextAndNavigationBarFragment)
                    ((MainContextAndNavigationBarFragment)displayedFragmentManager.displayedFragments[0]).setMainContext(targetFragment);   //TODO try-catch 추가하기
                if (displayedFragmentManager.displayedFragments[1] instanceof MainContextWithLocationSelectFragment)
                    ((MainContextWithLocationSelectFragment)displayedFragmentManager.displayedFragments[1]).setMainContext(targetFragment);
                 displayedFragmentManager.fragmentStackManager.PushFragment(level, displayedFragmentManager.fragmentManagers[level].findFragmentById(R.id.mainContext_frameLayout));
                 fragmentTransaction.replace(R.id.mainContext_frameLayout, targetFragment).commit();


                break;
        }

        return true;
    }
/*

    public boolean ReplaceFragmentLevel_0(Fragment targetFragment) {
        FragmentTransaction fragmentTransaction = displayedFragmentManager.fragmentManagers[0].beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_right_with_main_fragment, R.animator.animator_slide_out_left_with_main_fragment);
        fragmentTransaction.replace(R.id.mainActivity_frameLayout, targetFragment).commit();
        setCurrFragmentType(targetFragment);
        fragmentStackManager.PushFragment(0, displayedFragmentManager.fragmentManagers[0].findFragmentById(R.id.mainActivity_frameLayout));

        if (targetFragment instanceof  MainContextAndNavigationBarFragment) {
            setMyListBtnPosition(ABOVE_NAVIGATION_BAR);
        }
        else {
            setMyListBtnPosition(ABOVE_PARENT);
            setFragmentManagers(1, null);
            setFragmentManagers(2, null);
        }
        return true;
    }
    public boolean ReplaceFragmentLevel_1(Fragment targetFragment) {
        FragmentTransaction fragmentTransaction = displayedFragmentManager.fragmentManagers[1].beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_right_with_main_fragment, R.animator.animator_slide_out_left_with_main_fragment);
        fragmentTransaction.replace(R.id.mainContextWithLocationSelect_frameLayout, targetFragment).commit();
        fragmentStackManager.PushFragment(1, displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout));

        if (!(targetFragment instanceof MainContextWithLocationSelectFragment))
            setLocationSelect(null);

        return true;
    }
    public boolean ReplaceFragmentLevel_2(Fragment targetFragment) {
        FragmentTransaction fragmentTransaction = displayedFragmentManager.fragmentManagers[2].beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_right_with_main_fragment, R.animator.animator_slide_out_left_with_main_fragment);
        fragmentTransaction.replace(R.id.mainContext_frameLayout, targetFragment).commit();
        ((MainContextAndNavigationBarFragment)currFragmentType).setMainContext(targetFragment);
        fragmentStackManager.PushFragment(2, displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout));
        return true;
    }
*/

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

    @Override
    public void onBackPressed() {
        this.displayedFragmentManager.fragmentStackManager.onBackPressed();
    }

    public FragmentManager[] getFragmentManagers() {
        return displayedFragmentManager.fragmentManagers;
    }

    public class DisplayedFragmentManager {
        public Fragment[] displayedFragments = new Fragment[3];
        public FragmentManager[] fragmentManagers = new FragmentManager[3];
        public FragmentStackManager fragmentStackManager;

        public LocationSelectFragment locationSelect;
        public NavigationBarFragment navigationBar;

        public DisplayedFragmentManager(MainActivity mainActivity) {
            fragmentStackManager = new FragmentStackManager(mainActivity);
        }
    }
}