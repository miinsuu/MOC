package com.momeokji.moc.Helper;

import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.momeokji.moc.HomeFragment;
import com.momeokji.moc.MainActivity;
import com.momeokji.moc.MainContextAndNavigationBarFragment;
import com.momeokji.moc.MoreInfoFragment;
import com.momeokji.moc.R;
import com.momeokji.moc.RestaurantListFragment;
import com.momeokji.moc.RouletteFragment;

import static com.momeokji.moc.MainActivity.fragmentStackManager;

public class DisplayedFragmentManager {
    public MainActivity mainActivity;
    public BottomNavigationView bottomNavigationView;
    public FragmentManager[] fragmentManagers = new FragmentManager[2];
    public int frameLayoutIDs[] = new int[2];

    private FloatingActionButton myList_btn;
    private boolean isPositionAbove = true;
    private boolean isAnimating = false;

    public DisplayedFragmentManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        frameLayoutIDs[0] = R.id.mainActivity_frameLayout;
        frameLayoutIDs[1] = R.id.mainContent_frameLayout;
    }

    /*- Fragment 교체 함수 -*/
    public boolean ReplaceFragment(final int level, final Fragment targetFragment, int animationDirection) {
        if (isAnimating)
            return false;

        final FragmentManager fragmentManager = fragmentManagers[level];
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String targetFragmentClassName = targetFragment.getClass().getName();
        Fragment removalFragment;

        if (fragmentStackManager == null)
            return false;

        removalFragment = fragmentManager.findFragmentById(frameLayoutIDs[level]);
        if (removalFragment == null)
            return false;

        fragmentTransaction.addToBackStack(targetFragmentClassName);

        if (animationDirection == Constants.ANIMATION_DIRECT.TO_RIGHT)
            fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment, R.anim.anim_slide_in_left_with_main_fragment, R.anim.anim_slide_out_right_with_main_fragment);
        else if (animationDirection == Constants.ANIMATION_DIRECT.TO_LEFT)
            fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left_with_main_fragment, R.anim.anim_slide_out_right_with_main_fragment, R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);

        fragmentTransaction.add(frameLayoutIDs[level], targetFragment, targetFragmentClassName);
        fragmentTransaction.hide(removalFragment);

        fragmentTransaction.commit();

        fragmentStackManager.PushFragment(level);

        FinishAnimationProcess();
        return true;
    }

    //* BottomNavigationBar의 선택 상태를 올바르게 표시해주는 함수
    public void UpdateBottomNavigationBarSelectedItem() {
         if (fragmentManagers[1].findFragmentById(R.id.mainContext_frameLayout) instanceof HomeFragment) {
                SetBottomNavigationBarSelectedItem(Constants.NAVIGATION_ITEM.HOME);
        } else if (fragmentManagers[1].findFragmentById(R.id.mainContext_frameLayout) instanceof RestaurantListFragment) {
            SetBottomNavigationBarSelectedItem(Constants.NAVIGATION_ITEM.RESTAURANT_LIST);
        } else if (fragmentManagers[1].findFragmentById(R.id.mainContent_frameLayout) instanceof RouletteFragment) {
            SetBottomNavigationBarSelectedItem(Constants.NAVIGATION_ITEM.ROULETTE);
        } else if (fragmentManagers[1].findFragmentById(R.id.mainContent_frameLayout) instanceof MoreInfoFragment) {
            SetBottomNavigationBarSelectedItem(Constants.NAVIGATION_ITEM.MORE_INFO);
        }
    }
    public void SetBottomNavigationBarSelectedItem(int itemPos) {
        if (bottomNavigationView != null)
            bottomNavigationView.getMenu().getItem(itemPos).setChecked(true);
    }


/*    //* fragment에 따라 나의 리스트 버튼의 위치를 올바르게 표시해주는 함수
    public void UpdateMyListBtnPosition() {
        UpdateMyListBtnPosition(fragmentManagers[0].findFragmentById(R.id.mainActivity_frameLayout));
    }
    public void UpdateMyListBtnPosition(Fragment targetFragment) {
        if (targetFragment instanceof MainContextAndNavigationBarFragment)
            SetMyListBtnPosition(Constants.XML_DESIGN.MYLISY_BTN_ABOVE_POSITION);
        else
            SetMyListBtnPosition(Constants.XML_DESIGN.MYLISY_BTN_BELOW_POSITION);
    }
    public void SetMyListBtnPosition(final boolean isTargetPositionAbove) {
        myList_btn = mainActivity.findViewById(R.id.myList_btn);
        if (isPositionAbove == isTargetPositionAbove)
            return;

        Animation animation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), (isTargetPositionAbove ? R.anim.anim_lift_up_with_my_list_btn : R.anim.anim_lift_down_with_my_list_btn));
        animation.setFillAfter(true);
        myList_btn.startAnimation(animation);
        isPositionAbove = isTargetPositionAbove;
    }*/

    public void FinishAnimationProcess() {
        isAnimating = true;
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isAnimating = false;
                UpdateBottomNavigationBarSelectedItem();
            }
        }, Constants.DELAYS.ANIMATION_DELAY);
    }

    public boolean getIsAnimating() {
        return this.isAnimating;
    }
}
