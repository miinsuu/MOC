package com.momeokji.moc.Helper;

import androidx.fragment.app.FragmentManager;

import com.momeokji.moc.HomeFragment;
import com.momeokji.moc.MainActivity;
import com.momeokji.moc.MainContextAndNavigationBarFragment;
import com.momeokji.moc.MainContextWithLocationSelectFragment;
import com.momeokji.moc.MoreInfoFragment;
import com.momeokji.moc.R;
import com.momeokji.moc.RestaurantListFragment;
import com.momeokji.moc.RouletteFragment;

import java.util.Stack;

public class FragmentStackManager {

    private Stack<Integer> fragmentBackStack = new Stack<>();         // Fragment Level 에 따라 중간에 데이터도 필요하기에 Stack 대신 ArrayList 사용
    private MainActivity mainActivity;


    public FragmentStackManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void onBackPressed() {
        if (fragmentBackStack.isEmpty()
            || (mainActivity.displayedFragmentManager.fragmentManagers[0].findFragmentById(R.id.mainActivity_frameLayout) instanceof MainContextAndNavigationBarFragment
                && mainActivity.displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout) instanceof MainContextWithLocationSelectFragment
                && mainActivity.displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof HomeFragment)){
            mainActivity.BackToOpening();
            return ;
        }

        if ((mainActivity.displayedFragmentManager.fragmentManagers[0].findFragmentById(R.id.mainActivity_frameLayout) instanceof MainContextAndNavigationBarFragment)
        && (mainActivity.displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof RestaurantListFragment
        || mainActivity.displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout) instanceof RouletteFragment
        || mainActivity.displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout) instanceof MoreInfoFragment)) {

            while (fragmentBackStack.size() != 0) {
                mainActivity.displayedFragmentManager.fragmentManagers[fragmentBackStack.pop()].popBackStack();
            }

            // (※하드코딩) 첫 홈 화면 -> 룰렛or더보기 -> 가게리스트 -> 뒤로가기 하면 MainContextWithLocationSelect 프레임이 설정이 꼬임 => 홈 화면이 아니게되면 홈으로 설정. (추후 변경하기)
            if (!(mainActivity.displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof HomeFragment)) {
                ((MainContextWithLocationSelectFragment)mainActivity.displayedFragmentManager.fragmentManagers[1].findFragmentByTag(MainContextWithLocationSelectFragment.class.getName()))
                        .setMainContext(mainActivity.displayedFragmentManager.fragmentManagers[2].findFragmentByTag(HomeFragment.class.getName()));
            }
            mainActivity.displayedFragmentManager.SetBottomNavigationBarSelectedItem(0);
        }
        else {
            FragmentManager targetFragmentManager = mainActivity.displayedFragmentManager.fragmentManagers[fragmentBackStack.peek()];

            targetFragmentManager.popBackStack();
            fragmentBackStack.pop();
        }
    }

    public void PushFragment(int fragmentLevel) {
        fragmentBackStack.add(fragmentLevel);
    }
}
