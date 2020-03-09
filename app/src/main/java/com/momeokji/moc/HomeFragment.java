package com.momeokji.moc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_RestaurantList;
import com.momeokji.moc.data.Restaurant;
import com.momeokji.moc.data.User;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final int CATEGORY_NUM = 8;
    final static private int ANIMATION_DIRECT_RIGHT = 0;
    final static private int ANIMATION_DIRECT_LEFT = 1;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MainActivity mainActivity;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Log.e("Login확인","사용자UID=>"+User.getUser().getUserUID());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_home, container, false);
        RelativeLayout home_searchRestaurants_relativeLayout = view.findViewById(R.id.home_searchRestaurants_relativeLayout);
        home_searchRestaurants_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.ReplaceFragment(0, new SearchRestaurantFragment(), ANIMATION_DIRECT_RIGHT);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else if(context instanceof MainActivity){
            // TODO Activity와 통신을 위해 이 부분을 지우고 MainActivity에 OnFragmentINteractionListener Override
            }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onStart() {
        super.onStart();

        //* 카테고리 버튼에 OnClickListener 등록
        //     - onStart에서 해주는 이유 : NavigationBarFragment의 BottomNavigationView를 등록하기 때문에 NavigationBarFragment가 확실히 생성된 후 작업하기 위해*//
        final View view = this.getView();
        final NavigationBarFragment tempNavBarFrag = mainActivity.displayedFragmentManager.navigationBar;
        final BottomNavigationView tempBotNavView = tempNavBarFrag.getBottomNavigationView();


        Button[] categoryBtns = new Button[CATEGORY_NUM];
        categoryBtns[0] = view.findViewById(R.id.korean_btn);
        categoryBtns[1] = view.findViewById(R.id.chinese_btn);
        categoryBtns[2] = view.findViewById(R.id.japanese_btn);
        categoryBtns[3] = view.findViewById(R.id.western_btn);
        categoryBtns[4] = view.findViewById(R.id.snack_btn);
        categoryBtns[5] = view.findViewById(R.id.chicken_btn);
        categoryBtns[6] = view.findViewById(R.id.night_btn);
        categoryBtns[7] = view.findViewById(R.id.fast_btn);

        for(int i = 0; i < CATEGORY_NUM; i++) {
            final int position = i + 1;
            categoryBtns[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainActivity.ReplaceFragment(2, new RestaurantListFragment(position), ANIMATION_DIRECT_RIGHT);
                        tempBotNavView.getMenu().getItem(1).setChecked(true);
                }
            });
        }

        // 로그아웃
        ImageButton logoutButton = view.findViewById(R.id.moreEvent_btn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그아웃
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                // 유저정보 삭제
                User.getUser().clearUser();
                Toast.makeText(getContext(), "로그아웃", Toast.LENGTH_SHORT).show();
                // 로그인 화면으로 이동
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

    }

   @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
