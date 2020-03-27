package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.momeokji.moc.Database.DataListener;
import com.momeokji.moc.Database.DatabaseQueryClass;
import com.momeokji.moc.data.Restaurant;

public class TosActivity extends AppCompatActivity {
    String tosStr; // 이용약관 내용
    String privacyStr; // 개인정보 처리방침 내용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tos);

        Button tos_back_btn = findViewById(R.id.tos_back_btn); // 뒤로가기 버튼
        Button tos_tosBtn =  findViewById(R.id.tos_tosBtn); // 이용약관 버튼
        Button tos_privacyBtn = findViewById(R.id.tos_privacyBtn); // 개인정보 처리방침 버튼
        final TextView tos_contentTxt = findViewById(R.id.tos_contentTxt); // 내용 텍스트
        Button tos_viewWebBtn = findViewById(R.id.tos_viewWebBtn); // 웹에서 보기 텍스트

        // DB에서 이용약관 및 개인정보 처리방침 받아오기
        DatabaseQueryClass.OtherInfoDB.getTos(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                String json = data.toString();
                JsonElement ele = new JsonParser().parse(json);
                JsonObject obj = ele.getAsJsonObject();

                tosStr = obj.get("tosContent").getAsString(); // 이용약관
                privacyStr = obj.get("privacyContent").getAsString(); // 개인정보 처리방침

                tos_contentTxt.setText(tosStr);
            }
        });



        // 뒤로가기 버튼
        tos_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로가기
                finish();
            }
        });

        // 이용약관 버튼
        tos_tosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tos_contentTxt.setText(tosStr);
            }
        });

        // 개인정보 처리방침 버튼
        tos_privacyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tos_contentTxt.setText(privacyStr);
            }
        });

        // 웬페이지 이동 버튼 리스너
        tos_viewWebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 해당 블로그 페이지로 이동
                Intent intent = new Intent(Intent.ACTION_VIEW);
                if(tos_contentTxt.getText().toString().equals(tosStr)) {
                    intent.setData(Uri.parse("https://m.blog.naver.com/cad8798/221872449270"));
                } else if (tos_contentTxt.getText().toString().equals(privacyStr)) {
                    intent.setData(Uri.parse("https://m.blog.naver.com/cad8798/221847444437"));
                }
                startActivity(intent);
            }
        });


    }
}
