package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VariousContentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_contents);

        Button eventPage_back_btn = findViewById(R.id.variousContents_back_btn); // 백 버튼
        View variousContents_awards_linearLayout = findViewById(R.id.variousContents_awards_linearLayout); // 2019 숭실 어워드 레이아웃
        View variousContents_cardNews_linearLayout = findViewById(R.id.variousContents_cardNews_linearLayout); // 고민사거리 카드뉴스 레이아웃

        // 백 버튼 클릭 시
        eventPage_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 홈 화면으로 이동
                finish();
            }
        });

        // 2019 숭실 어워드 레이아웃 리스너
        variousContents_awards_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 홈 화면으로 이동
                finish();
            }
        });

        // 고민사거리 카드뉴스 레이아웃 리스너
        variousContents_cardNews_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 고민사거리 카드뉴스 리스트 페이지로 이동
                startActivity(new Intent(getApplicationContext(), CardNewsListActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
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
