package com.momeokji.moc.Helper;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.momeokji.moc.HomeFragment;
import com.momeokji.moc.LocationSelectFragment;
import com.momeokji.moc.MainActivity;
import com.momeokji.moc.MainContextAndNavigationBarFragment;
import com.momeokji.moc.MainContextWithLocationSelectFragment;
import com.momeokji.moc.MoreInfoFragment;
import com.momeokji.moc.NavigationBarFragment;
import com.momeokji.moc.R;
import com.momeokji.moc.RestaurantListFragment;
import com.momeokji.moc.RouletteFragment;

import static com.momeokji.moc.MainActivity.fragmentStackManager;

public class DisplayedFragmentManager {
    public MainActivity mainActivity;
    public FragmentManager[] fragmentManagers = new FragmentManager[3];

    public LocationSelectFragment locationSelect;
    public NavigationBarFragment navigationBar;

    private FloatingActionButton myList_btn;

    public DisplayedFragmentManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /*- Fragment 교체 함수 -*/
    public boolean ReplaceFragment(final int level, final Fragment targetFragment, int animationDirection) {
        final FragmentManager fragmentManager = fragmentManagers[level];
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String targetFragmentClassName = targetFragment.getClass().getName();
        Fragment removalFragment = null;
        final Fragment tempRemovalFragment;

        if (fragmentStackManager == null)
            return false;

        if (animationDirection == Constants.ANIMATION_DIRECT.TO_RIGHT)
            fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment, R.anim.anim_slide_in_left_with_main_fragment, R.anim.anim_slide_out_right_with_main_fragment);
        else if (animationDirection == Constants.ANIMATION_DIRECT.TO_LEFT)
            fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left_with_main_fragment, R.anim.anim_slide_out_right_with_main_fragment, R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);

        int frameLayoutId = 0;

        switch (level) {
            case 0:
                frameLayoutId = R.id.mainActivity_frameLayout;
                break;
            case 1:
                frameLayoutId = R.id.mainContextWithLocationSelect_frameLayout;
                if (!(fragmentManagers[0].findFragmentById(R.id.mainActivity_frameLayout) instanceof MainContextAndNavigationBarFragment))
                    return false;
                else
                    ((MainContextAndNavigationBarFragment)fragmentManagers[0].findFragmentById(R.id.mainActivity_frameLayout)).setMainContextWithLocationSelect(targetFragment);
                break;
            case 2:
                frameLayoutId = R.id.mainContext_frameLayout;

                if (!(fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout) instanceof MainContextWithLocationSelectFragment))
                    return false;
                else
                    ((MainContextWithLocationSelectFragment)fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout)).setMainContext(targetFragment);
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
        fragmentStackManager.PushFragment(level);
        if (level == 0)
            UpdateMyListBtnPosition(targetFragment);
        return true;
    }

    //* BottomNavigationBar의 선택 상태를 올바르게 표시해주는 함수
    public void UpdateBottomNavigationBarSelectedItem() {
        if (fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof HomeFragment) {
            SetBottomNavigationBarSelectedItem(0);
        } else if (fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof RestaurantListFragment) {
            SetBottomNavigationBarSelectedItem(1);
        } else if (fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout) instanceof RouletteFragment) {
            SetBottomNavigationBarSelectedItem(2);
        } else if (fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout) instanceof MoreInfoFragment) {
            SetBottomNavigationBarSelectedItem(3);
        }
    }
    public void SetBottomNavigationBarSelectedItem(int itemPos) {
        navigationBar.getBottomNavigationView().getMenu().getItem(itemPos).setChecked(true);
    }


    //* fragment에 따라 나의 리스트 버튼의 위치를 올바르게 표시해주는 함수
    public void UpdateMyListBtnPosition() {
        UpdateMyListBtnPosition(fragmentManagers[0].findFragmentById(R.id.mainActivity_frameLayout));
    }
    public void UpdateMyListBtnPosition(Fragment targetFragment) {
        if (targetFragment instanceof MainContextAndNavigationBarFragment)
            SetMyListBtnPosition(Constants.XML_DESIGN.MYLISY_BTN_ABOVE_POSITION);
        else
            SetMyListBtnPosition(Constants.XML_DESIGN.MYLISY_BTN_BELOW_POSITION);
    }
    public void SetMyListBtnPosition(final boolean position) {
        myList_btn = mainActivity.findViewById(R.id.myList_btn);
        final ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) myList_btn.getLayoutParams();

        final int myListBtnMargin_in_dp = (int) (Constants.XML_DESIGN.MY_LIST_BTN_MARGIN_IN_DP * mainActivity.getResources().getDisplayMetrics().density);
        final int navigationBarHeight_in_dp = (int) (Constants.XML_DESIGN.NAVIGATION_BAR_HEIGHT_IN_DP * mainActivity.getResources().getDisplayMetrics().density);
        if (position) {
            if(params.bottomMargin == myListBtnMargin_in_dp + navigationBarHeight_in_dp)
                return;
        } else {
            if(params.bottomMargin == myListBtnMargin_in_dp)
                return;
        }

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float inter, Transformation t) {
                if (position) {
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
