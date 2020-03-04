package com.momeokji.moc;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.momeokji.moc.Adapters.PagerAdapter_MenuReview;

import com.momeokji.moc.CustomView.MarqueeTextView;
import com.momeokji.moc.data.Restaurant;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class RestaurantInfoFragment extends Fragment {
    private Restaurant selectedRestaurant;
    private double lat;
    private double lng;

    public RestaurantInfoFragment(Restaurant selectedRestaurant) {
        this.selectedRestaurant = selectedRestaurant;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_info, container, false);

        TextView restaurantPage_restaurantName_txt = view.findViewById(R.id.restaurantInfoPage_restaurantName_txt);
        TextView minMaxPrice = view.findViewById(R.id.restaurantInfoPage_restaurantRangePrice_txt);
        MarqueeTextView preview = view.findViewById(R.id.restaurantInfoPage_previewTxt);
        final TextView address = view.findViewById(R.id.restaurantInfoPage_addressTxt);
        TextView phoneNumber = view.findViewById(R.id.restaurantInfoPage_phoneNumberTxt);
        ImageButton callBtn = view.findViewById(R.id.call_btn);
        Button addressMapBtn = view.findViewById(R.id.restaurantInfoPage_addressMapBtn);
        Button restaurantInfo_back_btn = view.findViewById(R.id.restaurantInfo_back_btn);

        //선택한 가게의 정보를 화면에 뿌려주기
        restaurantPage_restaurantName_txt.setText(selectedRestaurant.getRestaurantName());
        minMaxPrice.setText(selectedRestaurant.getMinMaxPrice());
        preview.setText(selectedRestaurant.getPreview());
        preview.setSelected(true);
        address.setText(selectedRestaurant.getAddress());
        phoneNumber.setText(selectedRestaurant.getPhoneNumber());

        //가게 상세페이지에서 뒤로가기
        restaurantInfo_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onBackPressed();
            }
        });

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

        // 가게 주소 -> x,y좌표 변환
        Geocoder geoCoder = new Geocoder(getContext());
        try {
            List<Address> resultLocation =
                    geoCoder.getFromLocationName(address.getText().toString(), 1);
            lat = resultLocation.get(0).getLatitude(); // 위도
            lng = resultLocation.get(0).getLongitude(); // 경도
            Log.e("주소변환 테스트", "위도: "+lat+", 경도: "+lng);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 가게 주소로 네이버지도 연동 맵 띄우기
        addressMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapFragmentActivity.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.putExtra("name", selectedRestaurant.getRestaurantName());
                startActivity(intent);
            }
        });


        final ViewPager restaurantInfoPage_viewPager = view.findViewById(R.id.restaurantInfoPage_viewPager);
        restaurantInfoPage_viewPager.setAdapter(new PagerAdapter_MenuReview(getChildFragmentManager(), 1, getActivity()));

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

    @Override
    public void onResume() {
        super.onResume();
    }
}
