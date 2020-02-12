package com.momeokji.moc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MainActivity mainActivity;

    TextView location_txt;
    Spinner locationSelect_spinner;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        //* 지역 선택용 스피너 등록 *//
        locationSelect_spinner = view.findViewById(R.id.locationSelect_spinner);
        location_txt = view.findViewById(R.id.location_txt);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, ((MainActivity) getActivity()).restaurantDATA.Location); // TODO 서버 데이터 : Fragment 생성될 때 마다 Location 리스트
        locationSelect_spinner.setSelection(0);
        location_txt.setText((String) locationSelect_spinner.getItemAtPosition(0));
        locationSelect_spinner.setAdapter(arrayAdapter);
        locationSelect_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedItem = (String) adapterView.getItemAtPosition(position);
                location_txt.setText(selectedItem);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        view.findViewById(R.id.korean_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //---------------------------------------------------//


        //* 카테고리 버튼에 OnClickListener 등록 *//
        Button[] categotyBtns = new Button[8]; //TODO 상수 class로 변경하기
        categotyBtns[0] = view.findViewById(R.id.korean_btn);
        categotyBtns[1] = view.findViewById(R.id.chinese_btn);
        categotyBtns[2] = view.findViewById(R.id.japanese_btn);
        categotyBtns[3] = view.findViewById(R.id.western_btn);
        categotyBtns[4] = view.findViewById(R.id.snack_btn);
        categotyBtns[5] = view.findViewById(R.id.chicken_btn);
        categotyBtns[6] = view.findViewById(R.id.asian_btn);
        categotyBtns[7] = view.findViewById(R.id.fast_btn);

        for(int i = 0; i < 8; i++) {//TODO 상수 class로 변경하기
            final int position = i + 1;
            categotyBtns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.ReplaceFragment(new RestaurantListFragment(position));//TODO 상수 class로 변경하기
                }
            });
        }
        //---------------------------------------------------//

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
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
