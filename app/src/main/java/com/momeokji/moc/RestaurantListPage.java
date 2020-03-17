package com.momeokji.moc;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Handler;
import android.view.ViewManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_RestaurantList;
import com.momeokji.moc.data.Restaurant;

import java.util.ArrayList;


public class RestaurantListPage extends Fragment {

/*    //* 현재 데이터 수신 구조에서 사용 X
    final static private int initItemNum = 7;
    final static private int addItemNum = 2;
    final static private int loadingCheckDelay = 50;
    private boolean isRestaurantListLoaded = false;
    private int maxItemNum = initItemNum;*/

    private ArrayList<Restaurant> targetRestaurantArrayList;
    RecyclerView restaurantList_recyclerView;


    public RestaurantListPage(ArrayList<Restaurant> restaurantArrayList) {
        this.targetRestaurantArrayList = restaurantArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_restaurant_list_page, container, false);


        //* 리사이클러 뷰 어댑터 등록 *//
        restaurantList_recyclerView = view.findViewById(R.id.restaurantList_recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        restaurantList_recyclerView.setLayoutManager(linearLayoutManager);         // 레이아웃 매니저 등록
        final RecyclerViewAdapter_RestaurantList recyclerViewAdapter = new RecyclerViewAdapter_RestaurantList((MainActivity)getActivity());
        recyclerViewAdapter.setRestaurantList(targetRestaurantArrayList);
        restaurantList_recyclerView.setAdapter(recyclerViewAdapter);


/*        //* 현재 데이터 수신 구조에서 사용 X
        WaitToReceiveData();
        WaitDelay();*/


        return view;
    }

/*    //* 데이터 수신까지 대기하며 로딩 gif 표시하는 함수
        public void WaitToReceiveData() {
        if (isRestaurantListLoaded) {
            AddRecyclerViewAdapterAndScrollListener(getView());
        }
        else {
            final ImageView loading_gif = getView().findViewById(R.id.loading_img);
            loading_gif.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gif_loading));
            Glide.with(this).load(R.drawable.gif_loading).into(loading_gif);

            //* 데이터 수신 되기 전까지 로딩 아이콘
            final Handler delayHandler = new Handler();
            delayHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isRestaurantListLoaded) {
                        delayHandler.postDelayed(this, loadingCheckDelay);
                    } else {
                        //* 수신 완료 시 동작 *
                        ((ViewManager) loading_gif.getParent()).removeView(loading_gif); // 로딩 gif 제거

                        AddRecyclerViewAdapterAndScrollListener(getView());
                    }
                }
            }, loadingCheckDelay);
            //---------------------------------------------------------------------------------------------------------------------------//
        }

    }*/

/*    //* 현재 데이터 수신 구조에서 사용 X
      //* 가게리스트 페이지에 들어가면 가게리스트를 서버로부터 받는다 했을 때
      // - 스크롤이 수신한 가게 리스트의 마지막 아이템까지 내려갈 때 데이터 추가 수신
      public void EnableRestaurantListLoad() {
        this.isRestaurantListLoaded = true;
    }


    public void WaitDelay() {     // 임시 대기 함수
        //* 1.5초뒤 데이터 수신 완료 표현
        final Handler mHandler2 = new Handler();
        mHandler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                EnableRestaurantListLoad();
            }
        }, 1500);
        //--------------------------------------//
    }

    public void AddRecyclerViewAdapterAndScrollListener(View view) {
        //* 리사이클러 뷰 어댑터 등록
        restaurantList_recyclerView = view.findViewById(R.id.restaurantList_recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        restaurantList_recyclerView.setLayoutManager(linearLayoutManager);         // 레이아웃 매니저 등록

        final RecyclerViewAdapter_RestaurantList recyclerViewAdapter = new RecyclerViewAdapter_RestaurantList((MainActivity)getActivity());                                          // 어댑터 등록

        if (targetRestaurantArrayList != null) {
            if (maxItemNum > targetRestaurantArrayList.size()) {
                recyclerViewAdapter.setRestaurantList(targetRestaurantArrayList);
                maxItemNum = targetRestaurantArrayList.size();
            }
            else {
                recyclerViewAdapter.setRestaurantList(new ArrayList<>(targetRestaurantArrayList.subList(0, maxItemNum)));
            }
        }
        restaurantList_recyclerView.setAdapter(recyclerViewAdapter);
        //-----------------------------------------------------------------------------------------------------------//

        //* 리사이클러 뷰 스크롤 리스터 등록
        restaurantList_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastItemPosition == maxItemNum - 1) {

                    if (maxItemNum + addItemNum > targetRestaurantArrayList.size()) {    // if문 과 setRestaurantList 지우고 서버에서 데이터 수신하는 코드로 수정
                        maxItemNum = targetRestaurantArrayList.size();
                    }
                    else {
                        maxItemNum += addItemNum;
                    }
                    recyclerViewAdapter.setRestaurantList(new ArrayList<Restaurant>(targetRestaurantArrayList.subList(0,maxItemNum)));

                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });
        //-----------------------------------------------------------------------------------------------------------//
    }*/

}
