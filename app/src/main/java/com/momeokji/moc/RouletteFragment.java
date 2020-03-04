package com.momeokji.moc;


import android.content.Context;
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

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class RouletteFragment extends Fragment {

    final static private int CATEGORY_NUM = 8;
    final static private int ANIMATION_DIRECT_RIGHT = 0;
    final static private int ANIMATION_DIRECT_LEFT = 1;

    public final static int INIT_ROULETTE_INTERVAL = 300;
    public final static int INCREMENT_ROULETTE_INTERVAL = 20;
    public final static int RANDOM_INCREMENT_ROULETTE_INTERVAL = 30;
    public final static int MAX_ROULETTE_INTERVAL = 300;

    private static int targetItemIndex = 0;
    private static int nextItemChooseInterval = INIT_ROULETTE_INTERVAL;
    public ImageButton[] items;

    public RouletteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_roulette, container, false);

        items = new ImageButton[12];
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
            items[i].setClickable(false);
        }
        items[9] = items[3];
        items[10] = items[4];
        items[11] = items[5];

        final ImageButton lever = view.findViewById(R.id.roulette_drawing_imgbtn);
        lever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lever.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.coupon2));
                DrawItem();

            }
        });

        return view;
    }

    public void DrawItem() {
        targetItemIndex = 0;
        nextItemChooseInterval = INIT_ROULETTE_INTERVAL;
        final Random randomInterval = new Random();
        final Handler mHandler = new Handler();
        final Runnable rouletteRun = new Runnable() {
            @Override
            public void run() {
                if (nextItemChooseInterval > MAX_ROULETTE_INTERVAL) {
                    targetItemIndex = (targetItemIndex % 12) - 1;
                    SetTargetItemToResult(targetItemIndex);
                    return;
                }

                if (targetItemIndex != 0)
                    items[(targetItemIndex - 1) % 12].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.food));
                items[targetItemIndex % 12].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.fork));
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
}
