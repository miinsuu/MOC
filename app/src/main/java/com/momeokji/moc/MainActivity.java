package com.momeokji.moc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.momeokji.moc.Helper.FragmentStackManager;

import com.momeokji.moc.data.DATA;
import com.momeokji.moc.data.User;


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

    public MainActivity() {
        this.mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantDATA = new DATA();

        /*- 초기 Fragment 등록 -*/
        displayedFragmentManager.fragmentManagers[0] = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = displayedFragmentManager.fragmentManagers[0].beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_right_with_main_fragment, R.animator.animator_slide_out_left_with_main_fragment, R.animator.animator_slide_in_left_with_main_fragment, R.animator.animator_slide_out_right_with_main_fragment);
        fragmentTransaction.add(R.id.mainActivity_frameLayout, new MainContextAndNavigationBarFragment(this, new MainContextWithLocationSelectFragment(this, new HomeFragment())), MainContextAndNavigationBarFragment.class.getName()).addToBackStack(MainContextAndNavigationBarFragment.class.getName()).commit();

        //* 나의 리스트 버튼 등록 *//
        myList_btn = findViewById(R.id.myList_btn);
        myList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                MyListFragment myListFragment = MyListFragment.getInstance(mainActivity);
                myListFragment.show(fragmentTransaction, MyListFragment.TAG_MY_LIST_FRAGMENT);
            }
        });

    }



    /*- Fragment 교체 함수 -*/

    public boolean ReplaceFragment(final int level, final Fragment targetFragment, int animationDirection) {
        final FragmentManager fragmentManager = displayedFragmentManager.fragmentManagers[level];
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String targetFragmentClassName = targetFragment.getClass().getName();
        Fragment removalFragment = null;
        final Fragment tempRemovalFragment;

        if (animationDirection == ANIMATION_DIRECT_RIGHT)
            fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_right_with_main_fragment, R.animator.animator_slide_out_left_with_main_fragment, R.animator.animator_slide_in_left_with_main_fragment, R.animator.animator_slide_out_right_with_main_fragment);
        else if (animationDirection == ANIMATION_DIRECT_LEFT)
            fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_left_with_main_fragment, R.animator.animator_slide_out_right_with_main_fragment, R.animator.animator_slide_in_right_with_main_fragment, R.animator.animator_slide_out_left_with_main_fragment);

        int frameLayoutId = 0;

        switch (level) {
            case 0:
                frameLayoutId = R.id.mainActivity_frameLayout;
                break;
            case 1:
                frameLayoutId = R.id.mainContextWithLocationSelect_frameLayout;
                if (!(displayedFragmentManager.fragmentManagers[0].findFragmentById(R.id.mainActivity_frameLayout) instanceof MainContextAndNavigationBarFragment))
                    return false;
                else
                    ((MainContextAndNavigationBarFragment)displayedFragmentManager.fragmentManagers[0].findFragmentById(R.id.mainActivity_frameLayout)).setMainContextWithLocationSelect(targetFragment);
                break;
            case 2:
                frameLayoutId = R.id.mainContext_frameLayout;

                if (!(displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout) instanceof MainContextWithLocationSelectFragment))
                    return false;
                else
                    ((MainContextWithLocationSelectFragment)displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout)).setMainContext(targetFragment);
                break;
            default:
                return false;
        }

        removalFragment = fragmentManager.findFragmentById(frameLayoutId);
        if (removalFragment == null)
            return false;

        fragmentTransaction.addToBackStack(targetFragmentClassName);
        if (fragmentManager.findFragmentByTag(targetFragmentClassName) == null)
            fragmentTransaction.add(frameLayoutId, targetFragment, targetFragmentClassName);
        else {
            fragmentTransaction.show(targetFragment);
            fragmentTransaction.detach(targetFragment).attach(targetFragment);
        }

        fragmentTransaction.hide(removalFragment).commit();
        displayedFragmentManager.fragmentStackManager.PushFragment(level);
        if (level == 0)
            displayedFragmentManager.SetMyListBtnPosition(targetFragment);
        return true;
    }


    @Override
    public void onBackPressed() {
        this.displayedFragmentManager.fragmentStackManager.onBackPressed();
    }

    public class DisplayedFragmentManager {
        public FragmentManager[] fragmentManagers = new FragmentManager[3];
        public FragmentStackManager fragmentStackManager;

        public LocationSelectFragment locationSelect;
        public NavigationBarFragment navigationBar;

        public DisplayedFragmentManager(MainActivity mainActivity) {
            fragmentStackManager = new FragmentStackManager(mainActivity);
        }

        //* BottomNavigationBar의 선택 상태를 올바르게 표시해주는 함수
        public void UpdateBottomNavigationBarSelectedItem() {
            if (displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof HomeFragment) {
                SetBottomNavigationBarSelectedItem(0);
            } else if (displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof RestaurantListFragment) {
                SetBottomNavigationBarSelectedItem(1);
            } else if (displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout) instanceof RouletteFragment) {
                SetBottomNavigationBarSelectedItem(2);
            } else if (displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout) instanceof MoreInfoFragment) {
                SetBottomNavigationBarSelectedItem(3);
            }
        }
        public void SetBottomNavigationBarSelectedItem(int itemPos) {
            navigationBar.getBottomNavigationView().getMenu().getItem(itemPos).setChecked(true);
        }


        //* fragment에 따라 나의 리스트 버튼의 위치를 올바르게 표시해주는 함수
        public void UpdateMyListBtnPosition() {
            final Fragment targetFragment = displayedFragmentManager.fragmentManagers[0].findFragmentById(R.id.mainActivity_frameLayout);
            SetMyListBtnPosition(targetFragment);
        }
        public void SetMyListBtnPosition(final Fragment targetFragment) {
            final ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) myList_btn.getLayoutParams();

            final int myListBtnMargin_in_dp = (int) (MY_LIST_BTN_MARGIN_IN_DP * getResources().getDisplayMetrics().density);
            final int navigationBarHeight_in_dp = (int) (NAVIGATION_BAR_HEIGHT_IN_DP * getResources().getDisplayMetrics().density);
            if (targetFragment instanceof MainContextAndNavigationBarFragment) {
                if(params.bottomMargin == myListBtnMargin_in_dp + navigationBarHeight_in_dp)
                    return;
            } else {
                if(params.bottomMargin == myListBtnMargin_in_dp)
                    return;
            }

            Animation a = new Animation() {
                @Override
                protected void applyTransformation(float inter, Transformation t) {
                    if (targetFragment instanceof MainContextAndNavigationBarFragment) {
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

    public void BackToOpening() {
        Intent intent = new Intent(MainActivity.this, Opening.class);
        startActivity(intent);
    }
}