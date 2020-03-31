package com.momeokji.moc.Helper;

import androidx.fragment.app.FragmentManager;

import com.momeokji.moc.HomeFragment;
import com.momeokji.moc.MainActivity;
import com.momeokji.moc.MainContextAndNavigationBarFragment;
import com.momeokji.moc.R;

import java.util.Stack;

import static com.momeokji.moc.MainActivity.displayedFragmentManager;

public class FragmentStackManager {

    private Stack<Integer> fragmentBackStack = new Stack<>();         // Fragment Level 에 따라 중간에 데이터도 필요하기에 Stack 대신 ArrayList 사용
    private MainActivity mainActivity;


    public FragmentStackManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void onBackPressed() {
        if (fragmentBackStack.isEmpty()) {
            mainActivity.BackToOpening();
            return ;
        }

        if (displayedFragmentManager.fragmentManagers[0].findFragmentById(R.id.mainActivity_frameLayout) instanceof MainContextAndNavigationBarFragment
        && !(displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContext_frameLayout) instanceof HomeFragment)) {

            ClearStack();
            displayedFragmentManager.SetBottomNavigationBarSelectedItem(Constants.NAVIGATION_ITEM.HOME);
        }
        else {
            FragmentManager targetFragmentManager = displayedFragmentManager.fragmentManagers[fragmentBackStack.peek()];
            targetFragmentManager.popBackStackImmediate();
            fragmentBackStack.pop();
        }
    }

    public void PushFragment(int fragmentLevel) {
        fragmentBackStack.add(fragmentLevel);
    }
    public void ClearStack() {
        while (!fragmentBackStack.empty())
            fragmentBackStack.pop();
        while (displayedFragmentManager.fragmentManagers[1].getBackStackEntryCount() != 1)
            displayedFragmentManager.fragmentManagers[1].popBackStackImmediate();
        while (displayedFragmentManager.fragmentManagers[0].getBackStackEntryCount() != 1)
            displayedFragmentManager.fragmentManagers[0].popBackStackImmediate();
    }
}
