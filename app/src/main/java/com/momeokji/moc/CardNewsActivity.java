package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_CardNews;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_CardNewsList;
import com.momeokji.moc.data.CardNews;

import java.util.ArrayList;
import java.util.Map;

public class CardNewsActivity extends AppCompatActivity {
    Context context;
    private RecyclerView cardNews_recyclerView; // 리사이클러뷰
    private RecyclerViewAdapter_CardNews adapterCardNews; // 어댑터
    RequestManager mGlideRequestManager; // Glide 매니저

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_news);

        Button cardNews_back_btn = findViewById(R.id.cardNews_back_btn); // 뒤로가기 버튼
        TextView cardNews_titleTxt = findViewById(R.id.cardNews_titleTxt); // 카드뉴스 제목
        cardNews_recyclerView = findViewById(R.id.cardNews_recyclerView); // 리사이클러뷰 초기화

        // Glide 매니저 초기화
        mGlideRequestManager = Glide.with(this);

        Intent intent = getIntent();
        // 카드뉴스 제목 뿌리기
        cardNews_titleTxt.setText(intent.getStringExtra("title"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        cardNews_recyclerView.setLayoutManager(linearLayoutManager);
        // 어댑터 초기화
        adapterCardNews = new RecyclerViewAdapter_CardNews(context, mGlideRequestManager);

        CardNews cardNews = intent.getParcelableExtra("cardNews");
        ArrayList<Map<String, Object>> imgUriArrayTmp = cardNews.getCardNewsImageUri();
        String[] imgUriArray = new String[imgUriArrayTmp.size()];

        // 카드뉴스 이미지 순서대로 재정렬
        for(int i = 0; i < imgUriArrayTmp.size(); i++) {
            imgUriArray[(int)imgUriArrayTmp.get(i).get("index")] = imgUriArrayTmp.get(i).get("imageUri").toString();
        }

        // 어댑터에 정렬된 이미지 적용
        adapterCardNews.setImgUriList(imgUriArray);

        // 리사이클러뷰에 어댑터 적용
        cardNews_recyclerView.setAdapter(adapterCardNews);

        // 백 버튼 클릭 시
        cardNews_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 홈 화면으로 이동
                finish();
            }
        });

    }


    // 뒤로가기 애니메이션
    @Override
    public void finish() {
        super.finish();

        overridePendingTransition( R.anim.anim_slide_in_left_with_main_fragment, R.anim.anim_slide_out_right_with_main_fragment);
    }
}
