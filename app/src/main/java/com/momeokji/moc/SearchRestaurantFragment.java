package com.momeokji.moc;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.momeokji.moc.Adapters.RecyclerViewAdapter_RestaurantList;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_SearchedRestaurantList;
import com.momeokji.moc.data.Restaurant;

import java.security.Key;
import java.util.ArrayList;


public class SearchRestaurantFragment extends Fragment {

    RecyclerViewAdapter_SearchedRestaurantList recyclerViewAdapter_searchedRestaurantList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_restaurant, container, false);

        //* 리사이클러뷰 어댑터 등록
        RecyclerView searchRestaurant_recyclerView = view.findViewById(R.id.searchRestaurant_recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        searchRestaurant_recyclerView.setLayoutManager(linearLayoutManager);         // 레이아웃 매니저 등록
        recyclerViewAdapter_searchedRestaurantList = new RecyclerViewAdapter_SearchedRestaurantList((MainActivity)getActivity());
        recyclerViewAdapter_searchedRestaurantList.setRestaurantList(new ArrayList<Restaurant>());
        searchRestaurant_recyclerView.setAdapter(recyclerViewAdapter_searchedRestaurantList);

        //* 검색창 텍스트뷰 키리스너 등록
        final EditText searchRestaurant_searchString_editTxt = view.findViewById(R.id.searchRestaurant_searchString_editTxt);
        searchRestaurant_searchString_editTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == event.KEYCODE_ENTER) {
                    UpdateSearchedRestaurantList(SearchTargetRestaurantList(searchRestaurant_searchString_editTxt.getText().toString()));
                    ((InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
                    searchRestaurant_searchString_editTxt.clearFocus();

                    return true;
                }
                return false;
            }
        });

        //* 검색버튼 클릭리스너 등록
        final ImageButton searchRestaurant_searchIcon_imgBtn = view.findViewById(R.id.searchRestaurant_searchIcon_imgBtn);
        searchRestaurant_searchIcon_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSearchedRestaurantList(SearchTargetRestaurantList(searchRestaurant_searchString_editTxt.getText().toString()));
            }
        });

        return view;
    }

    public ArrayList<Restaurant> SearchTargetRestaurantList(String targetString) {
        ArrayList<Restaurant> targetRestaurantList = new ArrayList<>();

        //**************** 받은 스트링을 가지고 타겟 음식점 찾기******************************//
        targetRestaurantList = ((MainActivity)getActivity()).restaurantDATA.getAllList();

        return targetRestaurantList;
    }
    public void UpdateSearchedRestaurantList(ArrayList<Restaurant> targetRestaurantList) {
        recyclerViewAdapter_searchedRestaurantList.setRestaurantList(targetRestaurantList);
        recyclerViewAdapter_searchedRestaurantList.notifyDataSetChanged();
    }
}
