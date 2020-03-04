package com.momeokji.moc.Helper;

import android.content.Intent;
import android.os.Handler;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.momeokji.moc.HomeFragment;
import com.momeokji.moc.LoginActivity;
import com.momeokji.moc.MainActivity;
import com.momeokji.moc.MainContextAndNavigationBarFragment;
import com.momeokji.moc.MainContextWithLocationSelectFragment;
import com.momeokji.moc.MoreInfoFragment;
import com.momeokji.moc.NavigationBarFragment;
import com.momeokji.moc.Opening;
import com.momeokji.moc.R;
import com.momeokji.moc.RestaurantInfoFragment;
import com.momeokji.moc.RestaurantListFragment;
import com.momeokji.moc.RouletteFragment;

import java.util.ArrayList;
import java.util.Stack;

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

        if ((mainActivity.displayedFragmentManager.fragmentManagers[0].findFragmentById(R.id.mainActivity_frameLayout) instanceof MainContextAndNavigationBarFragment)
            && (mainActivity.displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof HomeFragment
            || mainActivity.displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof RestaurantListFragment
            || mainActivity.displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout) instanceof RouletteFragment
            || mainActivity.displayedFragmentManager.fragmentManagers[1].findFragmentById(R.id.mainContextWithLocationSelect_frameLayout) instanceof MoreInfoFragment)) {
            for (int i = fragmentBackStack.size(); i > 0; i--) {
                mainActivity.displayedFragmentManager.fragmentManagers[fragmentBackStack.pop()].popBackStack();
            }

            // (※하드코딩) 첫 홈 화면 -> 룰렛or더보기 -> 가게리스트 -> 뒤로가기 하면 MainContextWithLocationSelect 프레임이 설정이 꼬임 => 홈 화면이 아니게되면 홈으로 설정. (추후 변경하기)
            if (!(mainActivity.displayedFragmentManager.fragmentManagers[2].findFragmentById(R.id.mainContext_frameLayout) instanceof HomeFragment)) {
                ((MainContextWithLocationSelectFragment)mainActivity.displayedFragmentManager.fragmentManagers[1].findFragmentByTag(MainContextWithLocationSelectFragment.class.getName()))
                        .setMainContext(mainActivity.displayedFragmentManager.fragmentManagers[2].findFragmentByTag(HomeFragment.class.getName()));
            }

            // (※하드코딩) 홈, 가게, 룰렛, 더보기 화면일 때 뒤로가기 시 네비게이션바 선택 상태 '홈'으로 설정. (fragment 교체 타이밍 정확히 이해하면 Update 함수로 변경하기)
            mainActivity.displayedFragmentManager.SetBottomNavigationBarSelectedItem(0);
        }
        else {

            // (※하드코딩)레스토랑 상세정보 -> 가게리스트 시 버튼 위쪽으로 이동. (fragment 교체 타이밍 정확히 이해하면 Update 함수로 변경하기)
            mainActivity.displayedFragmentManager.fragmentManagers[fragmentBackStack.pop()].popBackStackImmediate();
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mainActivity.displayedFragmentManager.fragmentManagers[0].findFragmentById(R.id.mainActivity_frameLayout) instanceof MainContextAndNavigationBarFragment)
                        mainActivity.displayedFragmentManager.SetMyListBtnPosition(mainActivity.displayedFragmentManager.fragmentManagers[0].findFragmentByTag(MainContextAndNavigationBarFragment.class.getName()));
                }
            }, 100);
        }
    }

    public void PushFragment(int fragmentLevel) {
        fragmentBackStack.add(fragmentLevel);
    }
}
