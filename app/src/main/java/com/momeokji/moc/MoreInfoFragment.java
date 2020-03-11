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

        View userNicknameLinearLayout = view.findViewById(R.id.moreInfo_fragment_userNickname_linearLayout);
        View myReviewLinearLayout = view.findViewById(R.id.moreInfo_fragment_myReview_linearLayout);
        View logoutLinearLayout = view.findViewById(R.id.moreInfo_fragment_logout_linearLayout);
        TextView userNicknameTxt = view.findViewById(R.id.moreInfo_fragment_userNicknameTxt);

        // 유저 이름 띄우기
        userNicknameTxt.setText(User.getUser().getNickName());
        // 로그아웃 리스너
        logoutLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 파이어베이스 로그아웃
                FirebaseAuth.getInstance().signOut();
                // 페이스북 로그아웃
                LoginManager.getInstance().logOut();
                // 유저정보 삭제
                User.getUser().clearUser();
                Toast.makeText(getContext(), "로그아웃", Toast.LENGTH_SHORT).show();
                // 로그인 화면으로 이동
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
