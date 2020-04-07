package com.momeokji.moc;

import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.momeokji.moc.Adapters.PagerAdapter_MenuReview;
import com.momeokji.moc.CustomView.MarqueeTextView;
import com.momeokji.moc.Helper.Constants;
import com.momeokji.moc.data.Restaurant;

import java.io.IOException;
import java.util.List;

import static android.content.Context.WINDOW_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class RestaurantInfoFragment extends Fragment {
    private Restaurant selectedRestaurant;
    private double lat;
    private double lng;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.3f;
    private static final float PERCENTAGE_TO_HIDE_FloatingBtn = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 50;
    private TextView restaurantInfo_ToolbarNameTxt;
    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheFloatingBtnVisible = true;

    public RestaurantInfoFragment(Restaurant selectedRestaurant) {
        this.selectedRestaurant = selectedRestaurant;
    }

    public static RestaurantInfoFragment getInstance(Restaurant selectedRestaurant) {
            return new RestaurantInfoFragment(selectedRestaurant);
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
        final TextView restaurantInfoPage_detailPreviewTxt = view.findViewById(R.id.restaurantInfoPage_detailPreviewTxt);
        final HorizontalScrollView preview_horizontalScrollView = view.findViewById(R.id.preview_horizontalScrollView);
        LinearLayout restaurantInfoPage_detailPreview_linearLayout = view.findViewById(R.id.restaurantInfoPage_detailPreview_linearLayout);
        RelativeLayout address_phone_relativeLayout = view.findViewById(R.id.address_phone_relativeLayout);

        restaurantInfo_ToolbarNameTxt = view.findViewById(R.id.restaurantInfo_ToolbarNameTxt);
        AppBarLayout restaurantInfo_AppbarLayout = view.findViewById(R.id.restaurantInfo_AppbarLayout);


        // 화면 폭 dp 구하기
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width  = size.x;
        float density = getContext().getResources().getDisplayMetrics().density;
        int dpWidth  = (int)((float)width/density);
        dpWidth = dpWidth - 20;

        // 주소 전화 레이아웃 너비 설정
        ViewGroup.LayoutParams addressLayoutParams = address_phone_relativeLayout.getLayoutParams();
        int addressNewWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpWidth, getResources().getDisplayMetrics());
        addressLayoutParams.width = addressNewWidth;

        // 리뷰 더보기 레이아웃 너비 설정
        ViewGroup.LayoutParams reviewLayoutParams = restaurantInfoPage_detailPreview_linearLayout.getLayoutParams();
        int reviewNewWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpWidth, getResources().getDisplayMetrics());
        reviewLayoutParams.width = reviewNewWidth;


        //선택한 가게의 정보를 화면에 뿌려주기
        restaurantPage_restaurantName_txt.setText(selectedRestaurant.getRestaurantName());
        minMaxPrice.setText(selectedRestaurant.getMinMaxPrice());
        restaurantInfoPage_detailPreviewTxt.setText(selectedRestaurant.getPreview());
        restaurantInfoPage_detailPreviewTxt.setMovementMethod(new ScrollingMovementMethod());

        final float[] PosX = new float[1];
        restaurantInfoPage_detailPreviewTxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    PosX[0] = event.getX();
                    preview_horizontalScrollView.requestDisallowInterceptTouchEvent(true);
                    //스크롤뷰가 텍스트뷰의 터치이벤트를 가져가지 못하게 함
                } else if(event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(Math.abs(PosX[0] - event.getX()) > 50)
                        preview_horizontalScrollView.requestDisallowInterceptTouchEvent(false);
                }


                return false;
            }
        });
        preview.setText(selectedRestaurant.getPreview());
        preview.setSelected(true);
        address.setText(selectedRestaurant.getAddress());
        phoneNumber.setText(selectedRestaurant.getPhoneNumber());

        if(restaurantInfoPage_detailPreviewTxt.getText().toString().trim().equals(""))
            restaurantInfoPage_detailPreviewTxt.setText("\""+selectedRestaurant.getRestaurantName()+"\""+" 많은 이용 부탁드립니다 ^^");

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

        // 가게 주소로 네이버지도 연동 맵 띄우기
        addressMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

