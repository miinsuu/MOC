package com.momeokji.moc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.momeokji.moc.Database.DatabaseQueryClass;
import com.momeokji.moc.data.User;

public class UpdateNicknameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nickname);

        final TextView updateNickname_acticity_newNicknameTxt = findViewById(R.id.updateNickname_acticity_newNicknameTxt); // 바꿀 닉네임
        Button updateNickname_acticity_cancelBtn = findViewById(R.id.updateNickname_acticity_cancelBtn); // 취소버튼
        Button updateNickname_acticity_updateBtn = findViewById(R.id.updateNickname_acticity_updateBtn); // 변경버튼

        // 변경 버튼 눌렀을 때 DB에 있는 정보 업데이트
        updateNickname_acticity_updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updateNickname_acticity_newNicknameTxt.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "변경할 닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String newNickname = updateNickname_acticity_newNicknameTxt.getText().toString();
                    String usersMapName = User.getUser().getusersMapName();
                    // DB에 업데이트 작업
                    DatabaseQueryClass.UserInfo.updateNickname(newNickname, usersMapName);

                    // DB에 업데이트된 닉네임을 앱으로 가져오기
                    String userUID = FirebaseAuth.getInstance().getUid();
                    User.getUser().putUserInfo(userUID);
                    finish(); // 업데이트 액티비티 종료
                    Toast.makeText(getApplicationContext(), "닉네임 업데이트", Toast.LENGTH_LONG).show();

                    // 홈 화면으로 이동
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }

            }
        });

        updateNickname_acticity_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // 닉네임 업데이트 액티비티 종료 -> 더보기 화면으로
            }
        });
    }
}
