package com.momeokji.moc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_RestaurantList;
import com.momeokji.moc.Helper.Constants;
import com.momeokji.moc.data.Restaurant;


public class HomeFragment extends Fragment {
    private MainActivity mainActivity;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_home, container, false);
        RelativeLayout home_searchRestaurants_relativeLayout = view.findViewById(R.id.home_searchRestaurants_relativeLayout);
        home_searchRestaurants_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.ReplaceFragment(0, new SearchRestaurantFragment(), Constants.ANIMATION_DIRECT.TO_RIGHT);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //* 카테고리 버튼에 OnClickListener 등록
        //     - onStart에서 해주는 이유 : NavigationBarFragment의 BottomNavigationView를 등록하기 때문에 NavigationBarFragment가 확실히 생성된 후 작업하기 위해*//
        final View view = this.getView();
        final NavigationBarFragment tempNavBarFrag = mainActivity.displayedFragmentManager.navigationBar;
        final BottomNavigationView tempBotNavView = tempNavBarFrag.getBottomNavigationView();


        Button[] categoryBtns = new Button[Constants.COUNTS.CATEGORY_NUM];
        categoryBtns[0] = view.findViewById(R.id.korean_btn);
        categoryBtns[1] = view.findViewById(R.id.chinese_btn);
        categoryBtns[2] = view.findViewById(R.id.japanese_btn);
        categoryBtns[3] = view.findViewById(R.id.western_btn);
        categoryBtns[4] = view.findViewById(R.id.snack_btn);
        categoryBtns[5] = view.findViewById(R.id.chicken_btn);
        categoryBtns[6] = view.findViewById(R.id.asian_btn);
        categoryBtns[7] = view.findViewById(R.id.fast_btn);

        for(int i = 0; i < Constants.COUNTS.CATEGORY_NUM; i++) {
            final int position = i + 1;
            categoryBtns[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainActivity.ReplaceFragment(2, new RestaurantListFragment(position), Constants.ANIMATION_DIRECT.TO_RIGHT);
                        tempBotNavView.getMenu().getItem(1).setChecked(true);
                }
            });
        }
    }
}
