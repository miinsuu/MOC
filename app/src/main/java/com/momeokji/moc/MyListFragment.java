package com.momeokji.moc;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.momeokji.moc.Adapters.RecyclerViewAdapter_MyListMenu;
import com.momeokji.moc.Helper.Constants;
import com.momeokji.moc.data.MyListMenu;

import java.util.ArrayList;


public class MyListFragment extends DialogFragment {
    public static final String TAG_MY_LIST_FRAGMENT = "dialog_event";

    private MainActivity mainActivity;

    public static MyListFragment getInstance(MainActivity mainActivity) {
        MyListFragment myListFragment = new MyListFragment(mainActivity);
        return myListFragment;
    }

    public MyListFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_list, container, false);


        //* RecyclerViewAdapter_MyListMenu 리사이클러 뷰 어댑터 등록
        RecyclerView myList_myMenuList_recyclerView = view.findViewById(R.id.myList_myMenuList_recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myList_myMenuList_recyclerView.setLayoutManager(linearLayoutManager);         // 레이아웃 매니저 등록
        final RecyclerViewAdapter_MyListMenu recyclerViewAdapter_myListMenu = new RecyclerViewAdapter_MyListMenu(mainActivity);
        myList_myMenuList_recyclerView.setAdapter(recyclerViewAdapter_myListMenu);

        TextView myList_allDelete_txt = view.findViewById(R.id.myList_allDelete_txt);
        ImageButton myList_allDelete_imgbtn = view.findViewById(R.id.myList_allDelete_imgbtn);
        View.OnClickListener allDeleteOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.restaurantDATA.MyListMenuList.clear();
                recyclerViewAdapter_myListMenu.notifyDataSetChanged();
            }
        };
        myList_allDelete_txt.setOnClickListener(allDeleteOnClickListener);
        myList_allDelete_imgbtn.setOnClickListener(allDeleteOnClickListener);


        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

        SetFragmentAttributes();
    }

    public void SetFragmentAttributes() {
        Point screenSize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(screenSize);
        getDialog().getWindow().setLayout(screenSize.x, (int)(screenSize.y* Constants.XML_DESIGN.MYLIST_HEIGHT_RATIO));
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_my_list);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.mylist_animation;
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }
}
