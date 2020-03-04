package com.momeokji.moc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


import com.naver.maps.geometry.GeoConstants;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;


public class MapFragmentActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private double lat;
    private double lng;
    private String restaurantName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map_fragment_activity);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat",0);
        lng = intent.getDoubleExtra("lng",0);
        restaurantName = intent.getStringExtra("name");

        // 위치 추적할 객체
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        // 네이버 지도 프래그먼트
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }


    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        // 가게 좌표 찍어주기
        Marker marker = new Marker();
        marker.setPosition(new LatLng(lat, lng));
        marker.setIcon(MarkerIcons.BLACK);
        marker.setIconTintColor(Color.RED);
        marker.setCaptionText(restaurantName);
        marker.setCaptionAligns(Align.Top);
        marker.setCaptionTextSize(16);
        marker.setMap(naverMap);

        // 앱사용자 현재 위치 찍어주기
        LocationOverlay locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);
        locationOverlay.setIcon(OverlayImage.fromResource(R.drawable.i_favorite_2));
        locationOverlay.setIconWidth(LocationOverlay.SIZE_AUTO);
        locationOverlay.setIconHeight(LocationOverlay.SIZE_AUTO);

        // 위치추적객체 & 지도 병합
        naverMap.setLocationSource(locationSource);

        // 위치버튼 누를 시 앱사용자위치 변할 때 마다 지도도 같이 이동
        naverMap.getUiSettings().setLocationButtonEnabled(true);

    }
}
