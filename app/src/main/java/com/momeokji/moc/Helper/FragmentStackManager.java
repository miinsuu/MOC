package com.momeokji.moc.Helper;

import android.os.Handler;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.momeokji.moc.MainActivity;
import com.momeokji.moc.MainContextAndNavigationBarFragment;
import com.momeokji.moc.MainContextWithLocationSelectFragment;
import com.momeokji.moc.R;

import java.util.ArrayList;
import java.util.Stack;

public class FragmentStackManager {

    private ArrayList<FragmentState> fragmentBackStack = new ArrayList<>();         // Fragment Level 에 따라 중간에 데이터도 필요하기에 Stack 대신 ArrayList 사용
    private MainActivity mainActivity;
    private int top = -1;


    public FragmentStackManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void onBackPressed() {
        if (top == -1)
            return ;
        FragmentTransaction fragmentTransaction = null;
        final int fragmentLevel = fragmentBackStack.get(top).fragmentLevel;
        final Fragment targetFragment = fragmentBackStack.get(top).fragment;
        switch (fragmentLevel) {
            case 0:
                fragmentTransaction = mainActivity.displayedFragmentManager.fragmentManagers[fragmentLevel].beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_left_with_main_fragment, R.animator.animator_slide_out_right_with_main_fragment);
                fragmentTransaction.replace(R.id.mainActivity_frameLayout, targetFragment).commit();

               /* mainActivity.displayedFragmentManager.displayedFragments[0] = targetFragment;
                if (targetFragment instanceof MainContextAndNavigationBarFragment) {
                    mainActivity.displayedFragmentManager.displayedFragments[1] = ((MainContextAndNavigationBarFragment) targetFragment).getMainContext();
                    if (targetFragment instanceof MainContextWithLocationSelectFragment)
                        mainActivity.displayedFragmentManager.displayedFragments[2] = ((MainContextWithLocationSelectFragment)targetFragment).getMainContext();
                    else
                        mainActivity.displayedFragmentManager.displayedFragments[2] = null;
                }
                else
                    mainActivity.displayedFragmentManager.displayedFragments[1] = null;

                */
                mainActivity.checkMyListBtnPosition(targetFragment);
                break;
            case 1:
                fragmentTransaction = mainActivity.displayedFragmentManager.fragmentManagers[fragmentLevel].beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_left_with_main_fragment, R.animator.animator_slide_out_right_with_main_fragment);
                fragmentTransaction.replace(R.id.mainContextWithLocationSelect_frameLayout, targetFragment).commit();

                /*mainActivity.displayedFragmentManager.displayedFragments[1] = targetFragment;
                if (targetFragment instanceof MainContextWithLocationSelectFragment)
                    mainActivity.displayedFragmentManager.displayedFragments[2] = ((MainContextWithLocationSelectFragment)targetFragment).getMainContext();
                else
                    mainActivity.displayedFragmentManager.displayedFragments[2] = null;*/
                break;
            case 2:
                fragmentTransaction = mainActivity.displayedFragmentManager.fragmentManagers[fragmentLevel].beginTransaction();
                fragmentTransaction.setCustomAnimations(R.animator.animator_slide_in_left_with_main_fragment, R.animator.animator_slide_out_right_with_main_fragment);
                fragmentTransaction.replace(R.id.mainContext_frameLayout, targetFragment).commit();

                /*mainActivity.displayedFragmentManager.displayedFragments[2] = targetFragment;
                if (mainActivity.displayedFragmentManager.displayedFragments[1] instanceof MainContextWithLocationSelectFragment)
                    ((MainContextWithLocationSelectFragment)targetFragment).setMainContext(targetFragment);*/
                break;
        }

        fragmentBackStack.remove(top--);
        final Handler mHandler2 = new Handler();
        mHandler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainActivity.displayedFragmentManager.UpdateDisplayedFragmentState(fragmentLevel, targetFragment);
            }
        }, 500);
    }

    public void PushFragment(int fragmentLevel, Fragment targetFragment) {
        top++;
        fragmentBackStack.add(new FragmentState(fragmentLevel, targetFragment));
    }

    public class FragmentState {
        private int fragmentLevel;
        private Fragment fragment;

        public FragmentState(int fragmentLevel, Fragment fragment) {
            this.fragmentLevel = fragmentLevel;
            this.fragment = fragment;
        }
    }
}
