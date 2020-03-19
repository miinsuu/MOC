package com.momeokji.moc;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.momeokji.moc.Adapters.PagerAdapter_MenuReview;
import com.momeokji.moc.CustomView.MarqueeTextView;
import com.momeokji.moc.data.Restaurant;

import java.io.IOException;
import java.util.List;


public class RestaurantInfoFragment extends Fragment {
    private Restaurant selectedRestaurant;
    private double lat;
    private double lng;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_FloatingBtn = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 50;
    private TextView restaurantInfo_ToolbarNameTxt;
    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheFloatingBtnVisible = true;

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

        restaurantInfo_ToolbarNameTxt = view.findViewById(R.id.restaurantInfo_ToolbarNameTxt);
        AppBarLayout restaurantInfo_AppbarLayout = view.findViewById(R.id.restaurantInfo_AppbarLayout);

        //선택한 가게의 정보를 화면에 뿌려주기
        restaurantPage_restaurantName_txt.setText(selectedRestaurant.getRestaurantName());
        minMaxPrice.setText(selectedRestaurant.getMinMaxPrice());
        preview.setText(selectedRestaurant.getPreview());
        preview.setSelected(true);
        address.setText(selectedRestaurant.getAddress());
        phoneNumber.setText(selectedRestaurant.getPhoneNumber());

        restaurantInfo_ToolbarNameTxt.setText(selectedRestaurant.getRestaurantName());
        restaurantInfo_AppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(i) / (float) maxScroll;

                handleToolbarTitleVisibility(percentage);
                handlefloatingBtnVisibility(percentage);

            }
        });
        startAlphaAnimation(restaurantInfo_ToolbarNameTxt,0,View.INVISIBLE);
        MainActivity.myList_btn.show();



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

    private void handleToolbarTitleVisibility(float percentage) {
        if(percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if(!mIsTheTitleVisible) {
                startAlphaAnimation(restaurantInfo_ToolbarNameTxt,ALPHA_ANIMATIONS_DURATION,View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(restaurantInfo_ToolbarNameTxt,ALPHA_ANIMATIONS_DURATION,View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }
    private void handlefloatingBtnVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_FloatingBtn) {
            if(mIsTheFloatingBtnVisible) {
                MainActivity.myList_btn.hide();
                mIsTheFloatingBtnVisible = false;
            }

        } else {
            if (!mIsTheFloatingBtnVisible) {
                MainActivity.myList_btn.show();
                mIsTheFloatingBtnVisible = true;
            }
        }
    }
    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f,1f)
                : new AlphaAnimation(1f,0f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}

