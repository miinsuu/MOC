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

        int fragmentLevel = fragmentBackStack.get(top).fragmentLevel;
        switch (fragmentLevel) {
            case 0:
                mainActivity.getFragmentManagers()[fragmentLevel].beginTransaction().replace(R.id.mainActivity_frameLayout, fragmentBackStack.get(top).fragment).commit();
                break;
            case 1:
                mainActivity.getFragmentManagers()[fragmentLevel].beginTransaction().replace(R.id.mainContextWithLocationSelect_frameLayout, fragmentBackStack.get(top).fragment).commit();
                break;
            case 2:
                mainActivity.getFragmentManagers()[fragmentLevel].beginTransaction().replace(R.id.mainContext_frameLayout, fragmentBackStack.get(top).fragment).commit();
                break;
        }
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
}
