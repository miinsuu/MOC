package com.momeokji.moc;


import android.content.Context;
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
import android.widget.Toast;

import com.momeokji.moc.Adapters.RecyclerViewAdapter_RestaurantList;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_SearchedRestaurantList;
import com.momeokji.moc.CustomView.BackPressEditText;
import com.momeokji.moc.data.Menu;
import com.momeokji.moc.data.Restaurant;

import java.security.Key;
import java.util.ArrayList;
import java.util.Map;

import static androidx.core.content.ContextCompat.getSystemService;


public class SearchRestaurantFragment extends Fragment {

    private static SearchRestaurantFragment searchRestaurantFragment = null;
    RecyclerViewAdapter_SearchedRestaurantList recyclerViewAdapter_searchedRestaurantList;
    BackPressEditText searchRestaurant_searchString_editTxt;

    public static SearchRestaurantFragment getInstance() {
        if (searchRestaurantFragment == null)
            searchRestaurantFragment = new SearchRestaurantFragment();
        return searchRestaurantFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_restaurant, container, false);

        final RecyclerView searchRestaurant_recyclerView = view.findViewById(R.id.searchRestaurant_recyclerView);
        searchRestaurant_searchString_editTxt = view.findViewById(R.id.searchRestaurant_searchString_editTxt);
        final ImageButton searchRestaurant_removeText_imgBtn = view.findViewById(R.id.searchRestaurant_removeText_imgBtn);
        final ImageButton searchRestaurant_searchIcon_imgBtn = view.findViewById(R.id.searchRestaurant_searchIcon_imgBtn);
        Button searchRestaurant_back_btn = view.findViewById(R.id.searchRestaurant_back_btn);

        //* 리사이클러뷰 어댑터 등록
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        searchRestaurant_recyclerView.setLayoutManager(linearLayoutManager);         // 레이아웃 매니저 등록
        recyclerViewAdapter_searchedRestaurantList = new RecyclerViewAdapter_SearchedRestaurantList((MainActivity)getActivity());
        recyclerViewAdapter_searchedRestaurantList.setRestaurantList(new ArrayList<Restaurant>());
        searchRestaurant_recyclerView.setAdapter(recyclerViewAdapter_searchedRestaurantList);

        //* 검색창 텍스트뷰 키리스너 등록 - 엔터 누르면 검색
        searchRestaurant_removeText_imgBtn.setVisibility(View.INVISIBLE);
        searchRestaurant_searchString_editTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == event.KEYCODE_ENTER) {

                    String targetString = searchRestaurant_searchString_editTxt.getText().toString(); // 검색어
                    targetString = targetString.trim(); // 검색어 좌우공백 제거

                    if(targetString.length() < 2) // 2글자 이상 검색
                        Toast.makeText(getContext(), "두 글자 이상 검색해 주세요.", Toast.LENGTH_SHORT).show();
                    else
                        UpdateSearchedRestaurantList(SearchTargetRestaurantList(targetString));

                    RemoveFocusFromEditText();
                    return true;
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
        searchRestaurant_searchIcon_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String targetString = searchRestaurant_searchString_editTxt.getText().toString(); // 검색어
                targetString = targetString.trim(); // 검색어 좌우공백 제거

                if(targetString.length() < 2) // 2글자 이상 검색
                    Toast.makeText(getContext(), "두 글자 이상 검색해 주세요.", Toast.LENGTH_SHORT).show();
                else
                    UpdateSearchedRestaurantList(SearchTargetRestaurantList(targetString));

                RemoveFocusFromEditText();
            }
        });

        //* EditText 입력 중 가게리스트 터치 시 포커스 제거
        searchRestaurant_recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RemoveFocusFromEditText();
                return false;
            }
        });


        //* 뒤로가기 버튼 클릭리스너 등록 - 백버튼 기능
        searchRestaurant_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveFocusFromEditText();
                ((MainActivity)getActivity()).onBackPressed();
            }
        });


        return view;
    }


    public ArrayList<Restaurant> SearchTargetRestaurantList(String targetString) {
        //**************** 받은 스트링을 가지고 타겟 음식점 찾기******************************//
        ArrayList<Restaurant> targetRestaurantList = new ArrayList<>();
        ArrayList<Restaurant> allRestaurantList = ((MainActivity)getActivity()).restaurantDATA.getAllList();
        boolean overlapCheck; // 가게중복체크

        for(int i = 0; i < allRestaurantList.size(); i++)
        {
            overlapCheck = false; // 중복여부 초기화
            Restaurant restaurantTmp = allRestaurantList.get(i);

            if(restaurantTmp.getRestaurantName().contains(targetString)) // 검색어가 가게이름에 포함되면 결과리스트에 가게추가
            {
                for(int j = 0; j < targetRestaurantList.size(); j++) // 결과리스트에 추가된 가게들과 중복검사
                {
                    if(targetRestaurantList.get(j).getRestaurantName().equals(restaurantTmp.getRestaurantName()))
                        overlapCheck = true; //가게가 중복되어서 추가하지 않음
                }
                if(!overlapCheck) // 중복되지 않은 가게는 추가
                    targetRestaurantList.add(restaurantTmp);

                continue;
            }

            Menu[] mainMenuTmp = restaurantTmp.getMainMenus();
            for(int j = 0; j < 3; j++)
            {
                if(mainMenuTmp[j].getName().contains(targetString)) // 검색어가 메표메뉴이름에 포함되면 결과리스트에 가게추가
                {
                    for(int k = 0; k < targetRestaurantList.size(); k++) // 결과리스트에 추가된 가게들과 중복검사
                    {
                        if(targetRestaurantList.get(k).getRestaurantName().equals(restaurantTmp.getRestaurantName()))
                            overlapCheck = true; //가게가 중복되어서 추가하지 않음
                    }
                    if(!overlapCheck) // 중복되지 않은 가게는 추가
                    {
                        targetRestaurantList.add(restaurantTmp);
                        overlapCheck = true;
                    }

                    break;
                }
            }
            if(overlapCheck)
                continue;

            ArrayList<Map> menuCategoryListTmp = restaurantTmp.getMenuList();
            for(int j = 0; j < menuCategoryListTmp.size(); j++)
            {
                for(Object o : menuCategoryListTmp.get(j).values())
                {
                    ArrayList<Menu> menuListTmp = (ArrayList<Menu>)o;
                    for(int k = 0; k < menuListTmp.size(); k++)
                    {
                        if(menuListTmp.get(k).getName().contains(targetString)) // 검색어가 메뉴이름에 포함되면 결과리스트에 가게추가
                        {
                            for(int m = 0; m < targetRestaurantList.size(); m++) // 결과리스트에 추가된 가게들과 중복검사
                            {
                                if(targetRestaurantList.get(m).getRestaurantName().equals(restaurantTmp.getRestaurantName()))
                                    overlapCheck = true; // 가게가 중복되어서 추가하지 않음
                            }
                            if(!overlapCheck) // 중복되지 않은 가게는 추가
                            {
                                targetRestaurantList.add(restaurantTmp);
                                overlapCheck = true;
                            }

                            break;
                        }
                    }
                }

                if(overlapCheck)
                    break;
            }

        }

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
