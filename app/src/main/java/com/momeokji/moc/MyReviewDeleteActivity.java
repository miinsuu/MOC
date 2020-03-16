package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_MyReview;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_ReviewTabPage;
import com.momeokji.moc.Database.DataListener;
import com.momeokji.moc.Database.DatabaseQueryClass;
import com.momeokji.moc.Database.MyOnSuccessListener;
import com.momeokji.moc.data.Review;
import com.momeokji.moc.data.User;

public class MyReviewDeleteActivity extends AppCompatActivity {
    Context context;
    RecyclerView myreview_recyclerView; // 리사이클러뷰
    RecyclerViewAdapter_MyReview MyReviewAdapter; // 어댑터
    RequestManager mGlideRequestManager; // Glide 매니저

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review_delete);

        myreview_recyclerView = findViewById(R.id.myreview_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        myreview_recyclerView.setLayoutManager(linearLayoutManager);

        mGlideRequestManager = Glide.with(this);

        // 어댑터 초기화
        MyReviewAdapter= new RecyclerViewAdapter_MyReview(context, mGlideRequestManager, new RecyclerViewAdapter_MyReview.OnItemClickListener() {
            @Override
            public void onItemClick(final Review review, int type) {
                if(type == 1) {
                    // 내가 쓴 리뷰 중, 선택한 리뷰 삭제
                    // 다이얼로그 OK누르면 삭제
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyReviewDeleteActivity.this);
                    builder.setTitle("리뷰를 삭제하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            // DB에서 리뷰 삭제
                            DatabaseQueryClass.ReviewDB.deleteReview(review.getReviewId(), new MyOnSuccessListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(getApplicationContext(), "리뷰를 삭제하였습니다.", Toast.LENGTH_SHORT).show();
                                    // 리뷰 삭제 후 화면 업데이트
                                    finish();
                                    startActivity(new Intent(MyReviewDeleteActivity.this, MyReviewDeleteActivity.class));
                                }
                            });

                        }
                    });
                    builder.setNegativeButton("아니요", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else if(type == 2) {
                    // 리뷰 이미지 크게보기 액티비티
                    if(review.getReviewImageUri() == null) {
                        return;
                    } else {
                        Intent intent = new Intent(MyReviewDeleteActivity.this, ViewReviewImageActivity.class);
                        intent.putExtra("imageUri", review.getReviewImageUri().toString());
                        startActivity(intent);
                    }
                }

            }
        });

        // DB에서 내가 쓴 리뷰 불러오기
        getReviewsFromDB();
    }

    // DB에서 내가 쓴 리뷰 불러오기
    private void getReviewsFromDB() {
        DatabaseQueryClass.ReviewDB.getMyReviewsByUserUID(User.getUser().getUserUID(), new DataListener() {
            @Override
            public void getData(Object data, String id) {
                MyReviewAdapter.addReview(new Review(data.toString(), id));
                myreview_recyclerView.setAdapter(MyReviewAdapter);
            }
        });
    }


}
