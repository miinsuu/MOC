package com.momeokji.moc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.momeokji.moc.Adapters.PagerAdapter_MenuReview;
import com.momeokji.moc.CustomView.MarqueeTextView;
import com.momeokji.moc.data.Restaurant;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link RestaurantInfoFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link RestaurantInfoFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class RestaurantInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Restaurant selectedRestaurant; //가게정보들이 담긴 instance

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
//    public static RestaurantInfoFragment newInstance(String param1, String param2) {
//        RestaurantInfoFragment fragment = new RestaurantInfoFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public RestaurantInfoFragment(Restaurant selectedRestaurant) {
        this.selectedRestaurant = selectedRestaurant;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_info, container, false);


        TextView restaurantPage_restaurantName_txt = view.findViewById(R.id.restaurantPage_restaurantName_txt);
        TextView minMaxPrice = view.findViewById(R.id.restaurantInfoPage_restaurantRangePrice_txt);
        MarqueeTextView preview = view.findViewById(R.id.restaurantInfoPage_previewTxt);
        TextView address = view.findViewById(R.id.restaurantInfoPage_addressTxt);
        TextView phoneNumber = view.findViewById(R.id.restaurantInfoPage_phoneNumberTxt);
        ImageButton callBtn = view.findViewById(R.id.call_btn);

        //선택한 가게의 정보를 화면에 뿌려주기
        restaurantPage_restaurantName_txt.setText(selectedRestaurant.getRestaurantName());
        minMaxPrice.setText(selectedRestaurant.getMinMaxPrice());
        preview.setText(selectedRestaurant.getPreview());
        preview.setSelected(true);
        address.setText(selectedRestaurant.getAddress());
        phoneNumber.setText(selectedRestaurant.getPhoneNumber());

        //가게 전화번호로 전화다이얼화면 띄우기
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumStr = selectedRestaurant.getPhoneNumber();
                if(phoneNumStr.equals(" ") || phoneNumStr.equals(""))
                    Toast.makeText(getContext(), "이 가게는 전화를 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
                else {
                    phoneNumStr =  phoneNumStr.replace("-","");
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumStr));
                    startActivity(intent);
                }
            }
        });


        final ViewPager restaurantInfoPage_viewPager = view.findViewById(R.id.restaurantInfoPage_viewPager);
        restaurantInfoPage_viewPager.setAdapter(new PagerAdapter_MenuReview(getChildFragmentManager(),1,getActivity()));

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.menuReviewTabBar_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                restaurantInfoPage_viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

}
