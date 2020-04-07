package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NoticeContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_content);

        Button noticeContent_back_btn = findViewById(R.id.noticeContent_back_btn); // 뒤로가기 버튼
        TextView noticeContent_titleTxt = findViewById(R.id.noticeContent_titleTxt); // 상단바 공지사항 제목
        TextView noticeTitle = findViewById(R.id.noticeTitle); // 공지사항 제목
        TextView noticeCreateAt = findViewById(R.id.noticeCreateAt); // 공지사항 쓴 날짜
        TextView noticeContent = findViewById(R.id.noticeContent); // 공지사항 내용

        // 뒤로가기 버튼
        noticeContent_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로가기
                finish();
            }
        });

        Intent intent = getIntent();
        noticeContent_titleTxt.setText(intent.getStringExtra("title"));
        noticeTitle.setText(intent.getStringExtra("title"));
        noticeCreateAt.setText(intent.getStringExtra("createAt"));
        String content = intent.getStringExtra("content").toString();
        content = content.replaceAll("<N>",System.lineSeparator());
        noticeContent.setText(content);
    }

    // 뒤로가기 애니메이션
    @Override
    public void finish() {
        super.finish();

        overridePendingTransition( R.anim.anim_slide_in_left_with_main_fragment, R.anim.anim_slide_out_right_with_main_fragment);
    }
}
