package com.momeokji.moc;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.momeokji.moc.data.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreInfoFragment extends Fragment {


    public MoreInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_more_info, container, false);

        View userNicknameLinearLayout = view.findViewById(R.id.moreInfo_fragment_userNickname_linearLayout); // 내 정보 수정 액티비티
        View myReviewLinearLayout = view.findViewById(R.id.moreInfo_fragment_myReview_linearLayout); // 내가쓴리뷰 삭제
        TextView userNicknameTxt = view.findViewById(R.id.moreInfo_fragment_userNicknameTxt); // 유저 닉네임 표시

        // 유저 이름 띄우기
        userNicknameTxt.setText(User.getUser().getNickName());

        // 내 정보 수정 액티비티로 이동
        userNicknameLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UpdateNicknameActivity.class));
            }
        });

        // 내가 쓴 리뷰 보기/삭제
        myReviewLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyReviewDeleteActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
