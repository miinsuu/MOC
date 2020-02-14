package com.momeokji.moc;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class LoadingFragment extends Fragment {

    private MainActivity mainActivity;
    private Fragment nextFragment;

    public LoadingFragment(Fragment nextFragment) {
        this.nextFragment = nextFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        mainActivity = (MainActivity) getActivity();
        ImageView loading_gif = view.findViewById(R.id.loading_img);
        Glide.with(this).load(R.drawable.gif_loading).into(loading_gif);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GotoNextFragment();
            }
        }, 1000);
    }

    public void GotoNextFragment() {
        mainActivity.ReplaceFragment(nextFragment);
    }

}
