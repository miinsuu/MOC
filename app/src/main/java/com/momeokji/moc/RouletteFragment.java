package com.momeokji.moc;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class RouletteFragment extends Fragment {

    final static private int CATEGORY_NUM = 9;
    final static private int ANIMATION_DIRECT_RIGHT = 0;
    final static private int ANIMATION_DIRECT_LEFT = 1;

    public final static int INIT_ROULETTE_INTERVAL = 100;
    public final static int INCREMENT_ROULETTE_INTERVAL = 10;
    public final static int RANDOM_INCREMENT_ROULETTE_INTERVAL = 15;
    public final static int MAX_ROULETTE_INTERVAL = 400;

    private static int targetItemIndex = 0;
    private static int nextItemChooseInterval = INIT_ROULETTE_INTERVAL;
    private ImageButton[] items;
    private Drawable[] unoutlinedIcons;
    private Drawable[] outlinedIcons;
    private ImageButton lever_up_imgbtn, lever_down_imgbtn;
    private TextView roulette_drawing_txt;


    public RouletteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_roulette, container, false);

        items = new ImageButton[9];
        items[0] = view.findViewById(R.id.item1);
        items[1] = view.findViewById(R.id.item2);
        items[2] = view.findViewById(R.id.item3);
        items[3] = view.findViewById(R.id.item6);
        items[4] = view.findViewById(R.id.item5);
        items[5] = view.findViewById(R.id.item4);
        items[6] = view.findViewById(R.id.item7);
        items[7] = view.findViewById(R.id.item8);
        items[8] = view.findViewById(R.id.item9);
        for(int i = 0; i < CATEGORY_NUM; i++) {
            final int position = i + 1;
            items[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity mainActivity = (MainActivity)getActivity();
                    mainActivity.ReplaceFragment(1, new MainContextWithLocationSelectFragment(mainActivity, new RestaurantListFragment(position)), ANIMATION_DIRECT_LEFT);
                    mainActivity.displayedFragmentManager.SetBottomNavigationBarSelectedItem(1);
                }
            });
            items[i].setClickable(false);
        }
        unoutlinedIcons = new Drawable[9];
        unoutlinedIcons[0] = ContextCompat.getDrawable(getContext(), R.drawable.food);
        unoutlinedIcons[1] = ContextCompat.getDrawable(getContext(), R.drawable.food);
        unoutlinedIcons[2] = ContextCompat.getDrawable(getContext(), R.drawable.food);
        unoutlinedIcons[3] = ContextCompat.getDrawable(getContext(), R.drawable.food);
        unoutlinedIcons[4] = ContextCompat.getDrawable(getContext(), R.drawable.food);
        unoutlinedIcons[5] = ContextCompat.getDrawable(getContext(), R.drawable.food);
        unoutlinedIcons[6] = ContextCompat.getDrawable(getContext(), R.drawable.food);
        unoutlinedIcons[7] = ContextCompat.getDrawable(getContext(), R.drawable.food);
        unoutlinedIcons[8] = ContextCompat.getDrawable(getContext(), R.drawable.food);

        outlinedIcons = new Drawable[9];
        outlinedIcons[0] = ContextCompat.getDrawable(getContext(), R.drawable.fork);
        outlinedIcons[1] = ContextCompat.getDrawable(getContext(), R.drawable.fork);
        outlinedIcons[2] = ContextCompat.getDrawable(getContext(), R.drawable.fork);
        outlinedIcons[3] = ContextCompat.getDrawable(getContext(), R.drawable.fork);
        outlinedIcons[4] = ContextCompat.getDrawable(getContext(), R.drawable.fork);
        outlinedIcons[5] = ContextCompat.getDrawable(getContext(), R.drawable.fork);
        outlinedIcons[6] = ContextCompat.getDrawable(getContext(), R.drawable.fork);
        outlinedIcons[7] = ContextCompat.getDrawable(getContext(), R.drawable.fork);
        outlinedIcons[8] = ContextCompat.getDrawable(getContext(), R.drawable.fork);

        lever_up_imgbtn = view.findViewById(R.id.roulette_lever_up_imgbtn);
        lever_down_imgbtn = view.findViewById(R.id.roulette_lever_down_imgbtn);
        SetLeverUpDown(true);
        lever_up_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawItem();
            }
        });
        roulette_drawing_txt = view.findViewById(R.id.roulette_drawing_txt);
        return view;
    }

    public void DrawItem() {
        this.targetItemIndex = 0;
        this.nextItemChooseInterval = INIT_ROULETTE_INTERVAL;

        SetLeverUpDown(false);

        final Random randomInterval = new Random();
        final Handler mHandler = new Handler();
        final Runnable rouletteRun = new Runnable() {
            @Override
            public void run() {
                if (nextItemChooseInterval > MAX_ROULETTE_INTERVAL) {
                    targetItemIndex = CalculateZigZagIndex(targetItemIndex-1);
                    SetTargetItemToResult(targetItemIndex);
                    SetLeverToRedrawState(targetItemIndex);
                    return;
                }

                if (targetItemIndex != 0)
                    items[CalculateZigZagIndex(targetItemIndex-1)].setBackground(unoutlinedIcons[CalculateZigZagIndex(targetItemIndex)]);
                items[CalculateZigZagIndex(targetItemIndex)].setBackground(outlinedIcons[CalculateZigZagIndex(targetItemIndex)]);
                targetItemIndex++;
                nextItemChooseInterval += INCREMENT_ROULETTE_INTERVAL + (randomInterval.nextInt(RANDOM_INCREMENT_ROULETTE_INTERVAL*2)-RANDOM_INCREMENT_ROULETTE_INTERVAL);
                mHandler.postDelayed(this, nextItemChooseInterval);

            }
        };

        mHandler.postDelayed(rouletteRun, nextItemChooseInterval);
    }

    public void SetTargetItemToResult(int targetItemIndex) {
        items[targetItemIndex].startAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.anim_scale_up_roulette_choosed_item));
        items[targetItemIndex].setClickable(true);
    }

    public void SetLeverToRedrawState(final int targetItemIndex) {
        SetLeverUpDown(true);
        roulette_drawing_txt.setText("다시뽑기");

        lever_up_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items[targetItemIndex].startAnimation(AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.anim_scale_down_roulette_choosed_item));
                DrawItem();
            }
        });
    }

    public int CalculateZigZagIndex(int targetItemIndex) {
        return targetItemIndex % 6  +  6*((targetItemIndex/6)%2) * (1- ((targetItemIndex/3)%2));
    }
    public void SetLeverUpDown(boolean isLeverUp) {
        if(isLeverUp) {
            lever_up_imgbtn.setVisibility(View.VISIBLE);
            lever_up_imgbtn.setClickable(true);
            lever_down_imgbtn.setVisibility(View.INVISIBLE);
            lever_down_imgbtn.setClickable(false);
        }
        else {
            lever_down_imgbtn.setVisibility(View.VISIBLE);
            lever_down_imgbtn.setClickable(true);
            lever_up_imgbtn.setVisibility(View.INVISIBLE);
            lever_up_imgbtn.setClickable(false);

        }
    }
}
