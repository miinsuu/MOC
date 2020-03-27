package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.momeokji.moc.Adapters.RecyclerViewAdapter_MyReview;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_NoticeList;
import com.momeokji.moc.Adapters.RecyclerViewAdapter_ReviewTabPage;
import com.momeokji.moc.Database.DataListener;
import com.momeokji.moc.Database.DatabaseQueryClass;
import com.momeokji.moc.Database.MyOnSuccessListener;
import com.momeokji.moc.data.Notice;
import com.momeokji.moc.data.Review;

public class NoticeActivity extends AppCompatActivity {
    Context context;
    private RecyclerView noticeList_recyclerView; // 리사이클러뷰
    private RecyclerViewAdapter_NoticeList adapterNoticeList; // 어댑터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        Button notice_back_btn = findViewById(R.id.notice_back_btn); // 뒤로가기 버튼
        noticeList_recyclerView = findViewById(R.id.noticeList_recyclerView); // 리사이클러뷰 초기화

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        noticeList_recyclerView.setLayoutManager(linearLayoutManager);
        // 어댑터 초기화
        adapterNoticeList = new RecyclerViewAdapter_NoticeList(context, new RecyclerViewAdapter_NoticeList.OnItemClickListener() {
            @Override
            public void onItemClick(Notice notice, int type) {
                if(type == 1) {
                    // 공지사항 내용보기 페이지로 이동
                    Intent intent = new Intent(getApplicationContext(), NoticeContentActivity.class);
                    intent.putExtra("title", notice.getNoticeTitle());
                    intent.putExtra("createAt", notice.getNoticeCreateAt());
                    intent.putExtra("content", notice.getNoticeContent());
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
                }
            }
        });

        // 뒤로가기 버튼 리스너
        notice_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로가기
                finish();
            }
        });

        // DB에서 공지사항 불러오기
        getNoticeFromDB();
    }

    // DB에서 공지사항 불러오기
    private void getNoticeFromDB() {
        DatabaseQueryClass.OtherInfoDB.getNotice(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                adapterNoticeList.addNotice(new Notice(data.toString(), id));
                noticeList_recyclerView.setAdapter(adapterNoticeList);
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
