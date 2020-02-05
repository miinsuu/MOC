package com.momeokji.moc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_RestaurantList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RestaurantListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestaurantListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerViewAdapter_RestaurantList adapter;

    public RestaurantListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantListFragment newInstance(String param1, String param2) {
        RestaurantListFragment fragment = new RestaurantListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        //* 탭 레이아웃 설정 *//
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.categoryTabBar_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                tab.select();
                switch (position) {
                    case 0:

                    case 1:
                        adapter.setRestaurantList(((MainActivity) getActivity()).restaurantDATA.KoreanRestaurantList);
                        adapter.notifyDataSetChanged();
                        break;
                    case 2:
                        adapter.setRestaurantList(((MainActivity) getActivity()).restaurantDATA.ChineseRestaurantList);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // TODO 만들어진 아이템 리스트 삭제
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //-------------------------//

        //* 페이저어댑터 등록 *//
/*
        ViewPager restaurantListPage_viewPager = view.findViewById(R.id.restaurantListPage_viewPager);
        PagerAdapter_RestaurantListPage restaurantListPage_pagerAdapter = new PagerAdapter_RestaurantListPage(getActivity());
        restaurantListPage_viewPager.setAdapter(restaurantListPage_pagerAdapter);
        //-------------------------//   */


        //* 리사이클러 뷰 등록 *//
        RecyclerView restaurantList_recyclerView = view.findViewById(R.id.restaurantList_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());         // 레이아웃 매니저 등록
        restaurantList_recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter_RestaurantList();                                          // 어댑터 등록
        restaurantList_recyclerView.setAdapter(adapter);
        adapter.setRestaurantList(((MainActivity) getActivity()).restaurantDATA.ChineseRestaurantList);
        //-----------------------//

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
/*        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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
