package com.momeokji.moc.Helper;

import android.os.Handler;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.momeokji.moc.HomeFragment;
import com.momeokji.moc.MainActivity;
import com.momeokji.moc.MainContextAndNavigationBarFragment;
import com.momeokji.moc.MainContextWithLocationSelectFragment;
import com.momeokji.moc.R;
import com.momeokji.moc.RestaurantListFragment;

import java.util.ArrayList;
import java.util.Stack;

public class FragmentStackManager {

    final static private int ANIMATION_DIRECT_RIGHT = 0;
    final static private int ANIMATION_DIRECT_LEFT = 1;

    private ArrayList<FragmentState> fragmentBackStack = new ArrayList<>();         // Fragment Level 에 따라 중간에 데이터도 필요하기에 Stack 대신 ArrayList 사용
    private MainActivity mainActivity;
    private int top = -1;


    public FragmentStackManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void onBackPressed() {
        if (top == -1)
            return ;
        final int fragmentLevel = fragmentBackStack.get(top).fragmentLevel;
        final Fragment targetFragment = fragmentBackStack.get(top).fragment;

        mainActivity.ReplaceFragment(fragmentLevel, targetFragment, ANIMATION_DIRECT_LEFT, false);
        fragmentBackStack.remove(top--);
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

    public void ArrangeStack() {
        if (mainActivity.displayedFragmentManager.displayedFragments[2] instanceof HomeFragment) {
            fragmentBackStack.clear();
            top = -1;
        }/*
        loop:
        for(int i = 0; i < fragmentBackStack.size(); i++)
            for(int j = 2; j >= 0; j--)
                if (fragmentBackStack.get(i).fragment.getClass().isInstance(mainActivity.displayedFragmentManager.displayedFragments[j])) {
                    fragmentBackStack.remove(i);
                    top--;
                    break loop;
                }
*/
    }
}
