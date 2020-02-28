package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

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
    final static private int ANIMATION_DIRECT_RIGHT = 0;
    final static private int ANIMATION_DIRECT_LEFT = 1;

    public static MainActivity mainActivity;
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
        displayedFragmentManager.displayedFragments[0] = new MainContextAndNavigationBarFragment(this, new MainContextWithLocationSelectFragment(this, new HomeFragment()));
        fragmentTransaction.add(R.id.mainActivity_frameLayout, displayedFragmentManager.displayedFragments[0]).commit();

        //* 나의 리스트 버튼 등록 *//
        myList_btn = findViewById(R.id.myList_btn);
        myList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            }
        });
    }



    /*- Fragment 교체 함수 -*/

    public boolean ReplaceFragment(final int level, final Fragment targetFragment, int animationDirection, boolean useStack) {
        FragmentTransaction fragmentTransaction = displayedFragmentManager.fragmentManagers[level].beginTransaction();
        Fragment removalFragment = null;

        if (animationDirection == ANIMATION_DIRECT_RIGHT)
            fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_right_with_main_fragment, R.animator.animator_slide_out_left_with_main_fragment);
        else if (animationDirection == ANIMATION_DIRECT_LEFT)
            fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_left_with_main_fragment, R.animator.animator_slide_out_right_with_main_fragment);

        switch (level) {
            case 0:
                if(animationDirection == ANIMATION_DIRECT_RIGHT)
                fragmentTransaction.addToBackStack("QWER");
                removalFragment = displayedFragmentManager.fragmentManagers[0].findFragmentById(R.id.mainActivity_frameLayout);
                if (useStack)
                    displayedFragmentManager.fragmentStackManager.PushFragment(level, removalFragment);
                fragmentTransaction.hide(removalFragment);                                              // replace로 교체하면 기존 fragment 제거 시 계층 관계에 따라 자식 fragment가 먼저 Destroy 되어 화면에서 사라짐

                if(animationDirection == ANIMATION_DIRECT_RIGHT)
                    fragmentTransaction.add(R.id.mainActivity_frameLayout, targetFragment).commit();        // hide 로 먼저 화면에서 제거한 후 UpdateDisplayedFragmentState 함수 실행 시 remove
                else
                    fragmentTransaction.show(targetFragment).commit();
                break;
            case 1:
                if (displayedFragmentManager.displayedFragments[0] instanceof MainContextAndNavigationBarFragment) {
                    removalFragment = displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout);
                    if (useStack)
                        displayedFragmentManager.fragmentStackManager.PushFragment(level, removalFragment);
                    fragmentTransaction.hide(removalFragment);
                    fragmentTransaction.add(R.id.mainContextWithLocationSelect_frameLayout, targetFragment).commit();
                }
                break;
            case 2:
                if (displayedFragmentManager.displayedFragments[1] instanceof MainContextWithLocationSelectFragment) {
                    removalFragment = displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout);
                    if (useStack)
                        displayedFragmentManager.fragmentStackManager.PushFragment(level, removalFragment);
                    fragmentTransaction.hide(removalFragment);
                    fragmentTransaction.add(R.id.mainContext_frameLayout, targetFragment).commit();
                }
                break;
            default:
                return false;
        }

        final Fragment tempRemovalFragment = removalFragment;
        if (tempRemovalFragment != null) {
            final Handler mHandler2 = new Handler();
            mHandler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //displayedFragmentManager.fragmentManagers[level].beginTransaction().remove(tempRemovalFragment).commit();
                }
            }, 300);
        }

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

        public Fragment.SavedState tempSavedState = null;

        public DisplayedFragmentManager(MainActivity mainActivity) {
            fragmentStackManager = new FragmentStackManager(mainActivity);
        }

        public void UpdateDisplayedFragmentState(int level, Fragment targetFragment) {
            switch (level) {
                case 0:
                    //* 현재 표시되는 fragment & FragmentManager 업데이트 *//
                    displayedFragments[0] = targetFragment;
                    fragmentManagers[0] = getSupportFragmentManager();
                    //* 새로 설정된 fragment의 하위 fragment와 그의 FragmentManager도 업데이트 *//
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
                    //* Level 1은 MainContextAndNavigationBarFragment의 윗부분 Context 이므로 해당 Context 영역이 존재하는지 확인 *//
                    if (displayedFragments[0] instanceof MainContextAndNavigationBarFragment) {
                        //* 현재 표시되는 fragment & FragmentManager 업데이트 *//
                        ((MainContextAndNavigationBarFragment)displayedFragments[0]).setMainContextWithLocationSelect(targetFragment);
                        displayedFragments[1] = targetFragment;
                        fragmentManagers[1] = displayedFragments[0].getChildFragmentManager();
                        //* 새로 설정된 fragment의 하위 fragment와 그의 FragmentManager도 업데이트 *//
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
                    //* Level 2는 MainContextWithLocationSelectFragment 중간 부분 Context 이므로 해당 Context 영역이 존재하는지 확인 *//
                    if ( (displayedFragments[0] instanceof MainContextAndNavigationBarFragment) && (displayedFragments[1] instanceof MainContextWithLocationSelectFragment) ) {
                        //* 현재 표시되는 fragment 업데이트 *//
                        ((MainContextWithLocationSelectFragment)displayedFragments[1]).setMainContext(targetFragment);
                        ((MainContextAndNavigationBarFragment)displayedFragments[0]).setMainContextWithLocationSelect(displayedFragments[1]);
                        displayedFragments[2] = targetFragment;
                        //* 설정된 fragment 의 FragmentManager 업데이트*//
                        fragmentManagers[2] = displayedFragments[1].getChildFragmentManager();
                    }
                    break;
            }

            //* BottomNavigation Bar 선택 상태 수정  &&  나의 리스트 버튼 위치 수정*//
            UpdateBottomNavigationBarSelectedItem();
            UpdateMyListBtnPosition(displayedFragments[0]);

            fragmentStackManager.ArrangeStack();
        }

        //* 뒤로가기 버튼 사용 시 BottomNavigationBar의 선택 상태를 올바르게 표시해주는 함수 *//
        public void UpdateBottomNavigationBarSelectedItem() {
            if (displayedFragments[0] instanceof MainContextAndNavigationBarFragment) {
                if (displayedFragments[2] instanceof HomeFragment) {
                    navigationBar.getBottomNavigationView().getMenu().getItem(0).setChecked(true);
                } else if (displayedFragments[2] instanceof RestaurantListFragment) {
                    navigationBar.getBottomNavigationView().getMenu().getItem(1).setChecked(true);
                } else if (displayedFragments[1] instanceof RouletteFragment) {
                    navigationBar.getBottomNavigationView().getMenu().getItem(2).setChecked(true);
                } else if (displayedFragments[1] instanceof MoreInfoFragment) {
                    navigationBar.getBottomNavigationView().getMenu().getItem(3).setChecked(true);
                }
            }
        }

        //* fragment에 따라 나의 리스트 버튼의 위치를 올바르게 표시해주는 함수 *//
        public void UpdateMyListBtnPosition(final Fragment targetFragment) {
            final ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) myList_btn.getLayoutParams();

            final int myListBtnMargin_in_dp = (int) (MY_LIST_BTN_MARGIN_IN_DP * getResources().getDisplayMetrics().density);
            final int navigationBarHeight_in_dp = (int) (NAVIGATION_BAR_HEIGHT_IN_DP * getResources().getDisplayMetrics().density);
            if (targetFragment instanceof  MainContextAndNavigationBarFragment) {
                if(params.bottomMargin == myListBtnMargin_in_dp + navigationBarHeight_in_dp)
                    return;
            } else {
                if(params.bottomMargin == myListBtnMargin_in_dp)
                    return;
            }

            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float inter, Transformation t) {
                    if (targetFragment instanceof  MainContextAndNavigationBarFragment) {
                        params.bottomMargin = myListBtnMargin_in_dp + (int)(navigationBarHeight_in_dp * inter);
                    } else {
                        params.bottomMargin = (myListBtnMargin_in_dp + navigationBarHeight_in_dp) - (int)(navigationBarHeight_in_dp * inter);
                    }
                    myList_btn.setLayoutParams(params);
                }
            };
            a.setDuration(300);
            myList_btn.startAnimation(a);
        }
    }

}