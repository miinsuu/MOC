package com.momeokji.moc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_RestaurantList;
import com.momeokji.moc.data.Restaurant;

import java.util.ArrayList;


public class RestaurantListPage extends Fragment {

    private ArrayList<Restaurant> targetRestaurantArrayList;
    private boolean isRestaurantListLoaded = false;

    public RestaurantListPage() {    }
    public RestaurantListPage(ArrayList<Restaurant> restaurantArrayList) {
        this.targetRestaurantArrayList = restaurantArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_restaurant_list_page, container, false);


        final ImageView loading_gif = view.findViewById(R.id.loading_img);
        Glide.with(this).load(R.drawable.gif_loading).into(loading_gif);

        //* 데이터 로딩 되기 전까지 로딩 아이콘 *//
        final Handler delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isRestaurantListLoaded) {
                    delayHandler.postDelayed(this, 50); //TODO 상수 class 사용
                }
                    else
                    {
                        ((ViewManager)loading_gif.getParent()).removeView(loading_gif); // 로딩 gif 제거

                        //* 리사이클러 뷰 등록 *//
                        RecyclerView restaurantList_recyclerView = view.findViewById(R.id.restaurantList_recyclerView);
                        restaurantList_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));         // 레이아웃 매니저 등록

                        RecyclerViewAdapter_RestaurantList recyclerViewAdapter = new RecyclerViewAdapter_RestaurantList(getActivity());                                          // 어댑터 등록
                        if (targetRestaurantArrayList != null)
                            recyclerViewAdapter.setRestaurantList(targetRestaurantArrayList);
                        restaurantList_recyclerView.setAdapter(recyclerViewAdapter);

                        //-----------------------------------------------------------------------------------------------------------//
                    }
                }
        }, 50);
        //---------------------------------------------------------------------------------------------------------------------------//

        //* 1초뒤 데이터 수신 완료 표현 *// TODO 민수랑 데이터 통신 물어본 후 합치기
        final Handler mHandler2 = new Handler();
        mHandler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                enableRestaurantListLoad();
            }
        }, 1500);
        //--------------------------------------//


        return view;
    }

    public void enableRestaurantListLoad() {
        this.isRestaurantListLoaded = true;
    }
}
