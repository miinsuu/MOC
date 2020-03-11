package com.momeokji.moc;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        TextView userNicknameTxt = view.findViewById(R.id.moreInfo_fragment_userNicknameTxt);

        // 유저 이름 띄우기
        userNicknameTxt.setText(User.getUser().getNickName());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
