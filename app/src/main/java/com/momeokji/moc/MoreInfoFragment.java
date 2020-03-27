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
    private MainActivity mainActivity;
    private static MoreInfoFragment moreInfoFragment = null;
    public MoreInfoFragment() {
        // Required empty public constructor
    }

    public static MoreInfoFragment getInstance() {
        if (moreInfoFragment == null)
            moreInfoFragment = new MoreInfoFragment();
        return moreInfoFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        mainActivity = MainActivity.getInstance();

        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_more_info, container, false);

        View userNicknameLinearLayout = view.findViewById(R.id.moreInfo_fragment_userNickname_linearLayout); // 내 정보 수정 액티비티
        View myReviewLinearLayout = view.findViewById(R.id.moreInfo_fragment_myReview_linearLayout); // 내가쓴리뷰 삭제
        TextView userNicknameTxt = view.findViewById(R.id.moreInfo_fragment_userNicknameTxt); // 유저 닉네임 표시
        View moreInfo_fragment_event_linearLayout = view.findViewById(R.id.moreInfo_fragment_event_linearLayout); // 이벤트 페이지
        View moreInfo_fragment_notice_linearLayout = view.findViewById(R.id.moreInfo_fragment_notice_linearLayout); // 공지사항 페이지
        View moreInfo_fragment_tos_linearLayout = view.findViewById(R.id.moreInfo_fragment_tos_linearLayout); // 서비스 약관 페이지
        View moreInfo_fragment_inquiration_linearLayout = view.findViewById(R.id.moreInfo_fragment_inquiration_linearLayout); // 문의하기 페이지


        // 유저 이름 띄우기
        userNicknameTxt.setText(User.getUser().getNickName());

        // 내 정보 수정 액티비티로 이동
        userNicknameLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UpdateNicknameActivity.class);
                startActivity(intent);
                mainActivity.overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
            }
        });

        // 내가 쓴 리뷰 보기/삭제
        myReviewLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyReviewDeleteActivity.class));
                mainActivity.overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
            }
        });

        // 이벤트 페이지 이동
        moreInfo_fragment_event_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EventPageActivity.class));
                mainActivity.overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
            }
        });

        // 이용약관 페이지 이동
        moreInfo_fragment_tos_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), TosActivity.class));
                mainActivity.overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
            }
        });

        // 공지사항리스트 페이지 이동
        moreInfo_fragment_notice_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NoticeActivity.class));
                mainActivity.overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
            }
        });

        // 문의하기 페이지 이동
        moreInfo_fragment_inquiration_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), InquireActivity.class));
                mainActivity.overridePendingTransition(R.anim.anim_slide_in_right_with_main_fragment, R.anim.anim_slide_out_left_with_main_fragment);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
