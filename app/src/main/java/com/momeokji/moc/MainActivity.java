package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.Transformation;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
//        displayedFragmentManager.displayedFragments[0] = new MainContextAndNavigationBarFragment(this, MAIN_CONTEXT_WITH_LOCATION_SELECT, new HomeFragment());
        displayedFragmentManager.displayedFragments[0] = new MainContextAndNavigationBarFragment(this, new MainContextWithLocationSelectFragment(this, new HomeFragment()));
        fragmentTransaction.add(R.id.mainActivity_frameLayout, displayedFragmentManager.displayedFragments[0]).commit();

        myList_btn = findViewById(R.id.myList_btn);
        myList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            }
        });
    }



    /*- Fragment 교체 함수 -*/

    public boolean ReplaceFragment(final int level, final Fragment targetFragment) {
        FragmentTransaction fragmentTransaction = displayedFragmentManager.fragmentManagers[level].beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_right_with_main_fragment, R.animator.animator_slide_out_left_with_main_fragment);

        switch (level) {
            case 0:
                checkMyListBtnPosition(targetFragment);
                displayedFragmentManager.fragmentStackManager.PushFragment(level, displayedFragmentManager.fragmentManagers[level].findFragmentById(R.id.mainActivity_frameLayout));
                fragmentTransaction.replace(R.id.mainActivity_frameLayout, targetFragment).commit();
                displayedFragmentManager.fragmentManagers[1].beginTransaction().setCustomAnimations(R.animator.animator_slide_in_right_with_main_fragment, R.animator.animator_slide_out_left_with_main_fragment).commit();
                displayedFragmentManager.fragmentManagers[2].beginTransaction().setCustomAnimations(R.animator.animator_slide_in_right_with_main_fragment, R.animator.animator_slide_out_left_with_main_fragment).commit();
                break;
            case 1:
                if (displayedFragmentManager.displayedFragments[0] instanceof MainContextAndNavigationBarFragment) {
                    displayedFragmentManager.fragmentStackManager.PushFragment(level, displayedFragmentManager.fragmentManagers[level].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout));
                    fragmentTransaction.replace(R.id.mainContextWithLocationSelect_frameLayout, targetFragment).commit();
                }
                break;
            case 2:
                if (displayedFragmentManager.displayedFragments[1] instanceof MainContextWithLocationSelectFragment) {
                    displayedFragmentManager.fragmentStackManager.PushFragment(level, displayedFragmentManager.fragmentManagers[level].findFragmentById(R.id.mainContext_frameLayout));
                    fragmentTransaction.replace(R.id.mainContext_frameLayout, targetFragment).commit();
                }
                break;
        }


        final Handler mHandler2 = new Handler();
        mHandler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                displayedFragmentManager.UpdateDisplayedFragmentState(level, targetFragment);
            }
        }, 500);

        return true;
    }


    @Override
    public void onBackPressed() {
        this.displayedFragmentManager.fragmentStackManager.onBackPressed();
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

        public void UpdateDisplayedFragmentState(int level, Fragment targetFragment) {
            switch (level) {
                case 0:
                    displayedFragments[0] = targetFragment;
                    fragmentManagers[0] = getSupportFragmentManager();
                    if (targetFragment instanceof MainContextAndNavigationBarFragment) {
                        displayedFragments[1] = ((MainContextAndNavigationBarFragment) displayedFragments[0]).getMainContextWithLocationSelect();
                        fragmentManagers[1] = displayedFragments[0].getChildFragmentManager();
                        navigationBar = ((MainContextAndNavigationBarFragment)displayedFragments[0]).getNavigationBarFragment();
                        if (((MainContextAndNavigationBarFragment) targetFragment).getMainContextWithLocationSelect() instanceof MainContextWithLocationSelectFragment) {
                            displayedFragments[2] = ((MainContextWithLocationSelectFragment)displayedFragments[1]).getMainContext();
                            fragmentManagers[2] = displayedFragments[1].getChildFragmentManager();
                            locationSelect = ((MainContextWithLocationSelectFragment)displayedFragments[1]).getLocationSelectFragment();
                        }
                        else {
                            displayedFragments[2] = null;
                            fragmentManagers[2] = null;
                            locationSelect = null;
                        }
                    }
                    else {
                        displayedFragments[1] = null;
                        fragmentManagers[1] = null;
                        navigationBar = null;

                        displayedFragments[2] = null;
                        fragmentManagers[2] = null;
                        locationSelect = null;
                    }
                    break;
                case 1:
                    if (displayedFragments[0] instanceof MainContextAndNavigationBarFragment) {
                        ((MainContextAndNavigationBarFragment)displayedFragments[0]).setMainContextWithLocationSelect(targetFragment);
                        displayedFragments[1] = targetFragment;
                        fragmentManagers[1] = displayedFragments[0].getChildFragmentManager();
                        if (targetFragment instanceof MainContextWithLocationSelectFragment) {
                            displayedFragments[2] = ((MainContextWithLocationSelectFragment) displayedFragments[1]).getMainContext();
                            fragmentManagers[2] = displayedFragments[1].getChildFragmentManager();
                            locationSelect = ((MainContextWithLocationSelectFragment) displayedFragments[1]).getLocationSelectFragment();
                        } else {
                            displayedFragments[2] = null;
                            fragmentManagers[2] = null;
                            locationSelect = null;
                        }
                    }
                    break;
                case 2:
                    if ( (displayedFragments[0] instanceof MainContextAndNavigationBarFragment) && (displayedFragments[1] instanceof MainContextWithLocationSelectFragment) ) {
                        ((MainContextWithLocationSelectFragment)displayedFragments[1]).setMainContext(targetFragment);
                        ((MainContextAndNavigationBarFragment)displayedFragments[0]).setMainContextWithLocationSelect(displayedFragments[1]);
                        displayedFragments[2] = targetFragment;
                        fragmentManagers[2] = displayedFragments[1].getChildFragmentManager();
                    }
                    break;

            }
        }
    }

    public void checkMyListBtnPosition(Fragment targetFragment) {
        if (targetFragment instanceof  MainContextAndNavigationBarFragment) {
            setMyListBtnPosition(ABOVE_NAVIGATION_BAR);
        }
        else {
            setMyListBtnPosition(ABOVE_PARENT);
        }
    }
    public void setMyListBtnPosition(boolean type) {
        ObjectAnimator objectAnimator;

        if (type == ABOVE_NAVIGATION_BAR) {
            objectAnimator = ObjectAnimator.ofFloat(myList_btn, "translationY", -1 * (int) ((NAVIGATION_BAR_HEIGHT_IN_DP) * getResources().getDisplayMetrics().density + 0.5f));
        }
        else {
            objectAnimator = ObjectAnimator.ofFloat(myList_btn, "translationY", (int) ((NAVIGATION_BAR_HEIGHT_IN_DP) * getResources().getDisplayMetrics().density + 0.5f));
        }
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }
}