package com.momeokji.moc;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
    EditText searchRestaurant_searchString_editTxt;

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

        //* 검색창 텍스트뷰 키리스너 등록 - 엔터 누르면 검색
        searchRestaurant_searchString_editTxt = view.findViewById(R.id.searchRestaurant_searchString_editTxt);
        final ImageButton searchRestaurant_removeText_imgBtn = view.findViewById(R.id.searchRestaurant_removeText_imgBtn);
        searchRestaurant_removeText_imgBtn.setVisibility(View.INVISIBLE);
        searchRestaurant_searchString_editTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == event.KEYCODE_ENTER) {
                        UpdateSearchedRestaurantList(SearchTargetRestaurantList(searchRestaurant_searchString_editTxt.getText().toString()));
                        RemoveFocusFromEditText();
                        return true;
                    }
                }
                return false;
            }
        });

        //* EditText에 텍스트변경 리스너 등록 - 검색어 제거 버튼 보일지 안보일지
        searchRestaurant_searchString_editTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRestaurant_searchString_editTxt.getText().toString().length() == 0) {
                    searchRestaurant_removeText_imgBtn.setVisibility(View.INVISIBLE);
                }
                else {
                    searchRestaurant_removeText_imgBtn.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {            }
        });

        //* 검색어 제거 버튼에 클릭리스너 등록 - 누르면 검색어 지워짐 & 버튼 사라짐
        searchRestaurant_removeText_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRestaurant_searchString_editTxt.setText("");
                searchRestaurant_removeText_imgBtn.setVisibility(View.INVISIBLE);
            }
        });

        //* 검색 버튼 클릭리스너 등록 - 검색 아이콘 누르면 검색
        final ImageButton searchRestaurant_searchIcon_imgBtn = view.findViewById(R.id.searchRestaurant_searchIcon_imgBtn);
        searchRestaurant_searchIcon_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSearchedRestaurantList(SearchTargetRestaurantList(searchRestaurant_searchString_editTxt.getText().toString()));
            }
        });

        //* 뒤로가기 버튼 클릭리스너 등록 - 백버튼 기능
        Button searchRestaurant_back_btn = view.findViewById(R.id.searchRestaurant_back_btn);
        searchRestaurant_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onBackPressed();
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

    public void RemoveFocusFromEditText() {
        ((InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
        searchRestaurant_searchString_editTxt.clearFocus();
    }
}
