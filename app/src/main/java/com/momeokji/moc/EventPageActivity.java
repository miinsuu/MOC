package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EventPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);

        Button eventPage_back_btn = findViewById(R.id.eventPage_back_btn);

        // 백 버튼 클릭 시
        eventPage_back_btn.setOnClickListener(new View.OnClickListener() {
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
