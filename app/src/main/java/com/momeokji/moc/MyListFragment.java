package com.momeokji.moc;

import android.graphics.Point;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.Adapters.RecyclerViewAdapter_MyListMenu;
import com.momeokji.moc.Helper.Constants;


public class MyListFragment extends DialogFragment {
    public static final String TAG_MY_LIST_FRAGMENT = "dialog_event";

    private MainActivity mainActivity;
    private GestureDetector detector = null;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    public static MyListFragment getInstance(MainActivity mainActivity) {
        MyListFragment myListFragment = new MyListFragment(mainActivity);
        return myListFragment;
    }

    public MyListFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my_list, container, false);

        final FrameLayout mylistDialog = view.findViewById(R.id.mylistDialog);

        //* RecyclerViewAdapter_MyListMenu 리사이클러 뷰 어댑터 등록
        final RecyclerView myList_myMenuList_recyclerView = view.findViewById(R.id.myList_myMenuList_recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        myList_myMenuList_recyclerView.setLayoutManager(linearLayoutManager);         // 레이아웃 매니저 등록
        final RecyclerViewAdapter_MyListMenu recyclerViewAdapter_myListMenu = new RecyclerViewAdapter_MyListMenu(mainActivity);
        myList_myMenuList_recyclerView.setAdapter(recyclerViewAdapter_myListMenu);

        final int firstvisible = linearLayoutManager.findFirstVisibleItemPosition();

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

        mylistDialog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });
        myList_myMenuList_recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (firstvisible == 0){

                }
                if (!myList_myMenuList_recyclerView.canScrollVertically(-1)){
                    detector.onTouchEvent(e);
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        detector = new GestureDetector(getActivity(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    dismiss();
                }
                return true;
            }
        });

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
