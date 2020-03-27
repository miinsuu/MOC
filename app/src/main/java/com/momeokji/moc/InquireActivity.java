package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.momeokji.moc.Database.DatabaseQueryClass;
import com.momeokji.moc.Database.MyOnSuccessListener;

public class InquireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquire);

        Button inquire_back_btn = findViewById(R.id.inquire_back_btn); // 뒤로가기 버튼
        final EditText inquire_title = findViewById(R.id.inquire_title); // 문의사항 제목
        final EditText inquire_content = findViewById(R.id.inquire_content); // 문의사항 내용
        Button inquire_doneBtn = findViewById(R.id.inquire_doneBtn); // 문의사항 보내기

        // 뒤로가기 버튼 리스너
        inquire_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로가기
                finish();
            }
        });

        // 문의사항 보내기 버튼 리스너
        inquire_doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String title = inquire_title.getText().toString().trim();
                final String content = inquire_content.getText().toString().trim();
                if(title.equals("")) {
                    Toast.makeText(getApplicationContext(), "문의사항 제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if(content.equals("")) {
                    Toast.makeText(getApplicationContext(), "문의사항 내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 다이얼로그 OK누르면 문의사항 보내기
                AlertDialog.Builder builder = new AlertDialog.Builder(InquireActivity.this);
                builder.setTitle("문의사항을 보내시겠습니까?");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        DatabaseQueryClass.OtherInfoDB.createInquiration(title, content, new MyOnSuccessListener() {
                            @Override
                            public void onSuccess() {
                                Log.e("문의사항 업로드 완료","=========");
                                Toast.makeText(getApplicationContext(), "문의사항이 접수되었습니다.", Toast.LENGTH_SHORT).show();
                                // 문의사항 접수 후 뒤로가기
                                finish();
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
            }
        });

    }
}
