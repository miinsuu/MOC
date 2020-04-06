package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.momeokji.moc.Adapters.RecyclerViewAdapter_CardNewsList;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_NoticeList;
import com.momeokji.moc.Database.DataListener;
import com.momeokji.moc.Database.DatabaseQueryClass;
import com.momeokji.moc.Helper.Constants;
import com.momeokji.moc.data.CardNews;
import com.momeokji.moc.data.Notice;

public class CardNewsListActivity extends AppCompatActivity {
    Context context;
    private RecyclerView cardNewsList_recyclerView; // 리사이클러뷰
    private RecyclerViewAdapter_CardNewsList adapterCardNewsList; // 어댑터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_news_list);

        Button cardNewsList_back_btn = findViewById(R.id.cardNewsList_back_btn); // 뒤로가기 버튼
        cardNewsList_recyclerView = findViewById(R.id.cardNewsList_recyclerView); // 리사이클러뷰 초기화

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        cardNewsList_recyclerView.setLayoutManager(linearLayoutManager);
        // 어댑터 초기화
        adapterCardNewsList = new RecyclerViewAdapter_CardNewsList(context, new RecyclerViewAdapter_CardNewsList.OnItemClickListener() {
            @Override
            public void onItemClick(CardNews cardNews, int type) {
                if(type == 1) {
                    // 카드뉴스 내용 페이지로 이동
                    Intent intent = new Intent(getApplicationContext(), CardNewsActivity.class);
                    intent.putExtra("title", cardNews.getCardNewsTitle());
                    intent.putExtra("cardNews", cardNews);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
                }
            }

        });


        // 백 버튼 클릭 시
        cardNewsList_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 홈 화면으로 이동
                finish();
            }
        });

        // MainActivity에서 미리 가져온 카드뉴스 데이터 어댑터에 추가 & 리사이클러뷰에 적용
        adapterCardNewsList.setCardNewsList(MainActivity.cardNewsList);
        cardNewsList_recyclerView.setAdapter(adapterCardNewsList);

    }

    @Override
    protected void onResume() {
        super.onResume();

        adapterCardNewsList.setCardNewsList(MainActivity.cardNewsList);
        cardNewsList_recyclerView.setAdapter(adapterCardNewsList);
    }

    // 뒤로가기 애니메이션
    @Override
    public void finish() {
        super.finish();

        overridePendingTransition( R.anim.anim_slide_in_left_with_main_fragment, R.anim.anim_slide_out_right_with_main_fragment);
    }
}
