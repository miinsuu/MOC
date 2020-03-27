package com.momeokji.moc;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_ReviewTabPage;
import com.momeokji.moc.Database.DataListener;
import com.momeokji.moc.Database.DatabaseQueryClass;
import com.momeokji.moc.data.Restaurant;
import com.momeokji.moc.data.Review;

import static com.momeokji.moc.MainActivity.displayedFragmentManager;
import static com.momeokji.moc.MainActivity.mainActivity;


public class RestaurantInfoReviewTabPage extends Fragment {

    Context context;
    private Restaurant selectedRestaurant;
    private RecyclerView review_recyclerView; // 리사이클러뷰
    private RecyclerViewAdapter_ReviewTabPage adapterReviewTabPage; // 어댑터
    private RequestManager mGlideRequestManager; // Glide manager
    boolean isVisible; // 현재 프래그먼트가 화면에 보이는지 여부


    public RestaurantInfoReviewTabPage(Context context) {
        this.context = context;
        this.selectedRestaurant = ((MainActivity)context).restaurantDATA.selectedRestaurant;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_restaurant_info_review_tab_page, container, false);

        Button writeReviewBtn = view.findViewById(R.id.writeReview_btn);
        review_recyclerView = view.findViewById(R.id.review_recyclerView);
        mGlideRequestManager = Glide.with(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        review_recyclerView.setLayoutManager(linearLayoutManager);
        // 어댑터 초기화
        adapterReviewTabPage = new RecyclerViewAdapter_ReviewTabPage(context, mGlideRequestManager);

        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayedFragmentManager.ReplaceFragment(0, new WriteReview(selectedRestaurant),0);
            }
        });

        // 스와이프 새로고침
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.reviewUpdate_swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 3초동안 업데이트 애니메이션 작동
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // 새로고침 완료
                        mSwipeRefreshLayout.setRefreshing(false);
                        // DB 새로고침 코드
                        updateReview();
                    }
                }, 4000); // 4초 딜레이


            }
        });


        // DB에서 리뷰 불러오기
        getReviewsFromDB();

        return view;
    }

    // DB에서 리뷰 불러오기
    private void getReviewsFromDB() {
        DatabaseQueryClass.ReviewDB.getReviewsByShop(selectedRestaurant.getRestaurantName(), new DataListener() {
            @Override
            public void getData(Object data, String id) {
                adapterReviewTabPage.addReview(new Review(data.toString(), id));
                review_recyclerView.setAdapter(adapterReviewTabPage);
            }
        });
    }

    // DB연동 화면 리프레쉬
    private void refreshFragement(){
        androidx.fragment.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        Log.e("리뷰 refreshFragement", "성공");
    }

    private void updateReview() {
        // 리뷰 새로고침이 돌아가는 도중에 다른화면으로 전환하면 리뷰가 업데이트 되지 않도록
        // 리뷰프래그먼트가 보여질 때만 refreshFragement() 호출
        if(isVisible)
            refreshFragement();

    }

    @Override
    public void onPause() {
        super.onPause();
        // 프래그먼트가 화면에 보이지 않음
        isVisible = false;
        Log.d("ReviewFragment", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        // 프래그먼트가 화면에 보임
        isVisible = true;
        Log.d("ReviewFragment", "onResume");
    }

}
