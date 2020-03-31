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
import android.widget.Toast;

import com.momeokji.moc.Helper.Constants;

import java.util.Random;

import static com.momeokji.moc.MainActivity.displayedFragmentManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class RouletteFragment extends Fragment {

    private static int targetItemIndex = 0;
    private static int nextItemChooseInterval = Constants.ROULETTE.INIT_ROULETTE_INTERVAL;
    private ImageButton[] items;
    private Drawable[] unoutlinedIcons;
    private Drawable[] outlinedIcons;
    private ImageButton lever_up_imgbtn, lever_down_imgbtn;
    private TextView roulette_drawing_txt;


    public RouletteFragment() {
        // Required empty public constructor
    }

    public static RouletteFragment getInstance() {
        return new RouletteFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_roulette, container, false);

        items = new ImageButton[8];
        items[0] = view.findViewById(R.id.item1);
        items[1] = view.findViewById(R.id.item2);
        items[2] = view.findViewById(R.id.item3);
        items[3] = view.findViewById(R.id.item4);
        items[4] = view.findViewById(R.id.item5);
        items[5] = view.findViewById(R.id.item6);
        items[6] = view.findViewById(R.id.item7);
        items[7] = view.findViewById(R.id.item8);
        for(int i = 0; i < Constants.COUNTS.CATEGORY_NUM; i++) {
            final int position = i + 1;
            items[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayedFragmentManager.ReplaceFragment(1, RestaurantListFragment.getInstance(position), Constants.ANIMATION_DIRECT.TO_RIGHT);
                    displayedFragmentManager.SetBottomNavigationBarSelectedItem(Constants.NAVIGATION_ITEM.RESTAURANT_LIST);
                }
            });
            items[i].setClickable(false);
        }
        unoutlinedIcons = new Drawable[8];
        unoutlinedIcons[0] = ContextCompat.getDrawable(getContext(), R.drawable.korean);
        unoutlinedIcons[1] = ContextCompat.getDrawable(getContext(), R.drawable.chinese);
        unoutlinedIcons[2] = ContextCompat.getDrawable(getContext(), R.drawable.japanese);
        unoutlinedIcons[3] = ContextCompat.getDrawable(getContext(), R.drawable.western);
        unoutlinedIcons[4] = ContextCompat.getDrawable(getContext(), R.drawable.snack);
        unoutlinedIcons[5] = ContextCompat.getDrawable(getContext(), R.drawable.chicken);
        unoutlinedIcons[6] = ContextCompat.getDrawable(getContext(), R.drawable.night);
        unoutlinedIcons[7] = ContextCompat.getDrawable(getContext(), R.drawable.fast);

        outlinedIcons = new Drawable[8];
        outlinedIcons[0] = ContextCompat.getDrawable(getContext(), R.drawable.korean_outlined);
        outlinedIcons[1] = ContextCompat.getDrawable(getContext(), R.drawable.chinese_outlined);
        outlinedIcons[2] = ContextCompat.getDrawable(getContext(), R.drawable.japanese_outlined);
        outlinedIcons[3] = ContextCompat.getDrawable(getContext(), R.drawable.western_outlined);
        outlinedIcons[4] = ContextCompat.getDrawable(getContext(), R.drawable.snack_outlined);
        outlinedIcons[5] = ContextCompat.getDrawable(getContext(), R.drawable.chicken_outlined);
        outlinedIcons[6] = ContextCompat.getDrawable(getContext(), R.drawable.night_outlined);
        outlinedIcons[7] = ContextCompat.getDrawable(getContext(), R.drawable.fast_outlined);

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
        items[(targetItemIndex)].setBackground(unoutlinedIcons[(targetItemIndex)]);
        this.targetItemIndex = 0;
        this.nextItemChooseInterval = Constants.ROULETTE.INIT_ROULETTE_INTERVAL;

        SetLeverUpDown(false);

        final Random randomInterval = new Random();
        final Handler mHandler = new Handler();
        final Runnable rouletteRun = new Runnable() {
            @Override
            public void run() {
                if (nextItemChooseInterval > Constants.ROULETTE.MAX_ROULETTE_INTERVAL) {
                    targetItemIndex = CalculateZigZagIndex(targetItemIndex-1);
                    SetTargetItemToResult(targetItemIndex);
                    SetLeverToRedrawState(targetItemIndex);
                    return;
                }

                if (targetItemIndex != 0)
                    items[CalculateZigZagIndex(targetItemIndex-1)].setBackground(unoutlinedIcons[CalculateZigZagIndex(targetItemIndex-1)]);
                items[CalculateZigZagIndex(targetItemIndex)].setBackground(outlinedIcons[CalculateZigZagIndex(targetItemIndex)]);
                targetItemIndex++;
                nextItemChooseInterval += Constants.ROULETTE.INCREMENT_ROULETTE_INTERVAL + (randomInterval.nextInt(Constants.ROULETTE.RANDOM_INCREMENT_ROULETTE_INTERVAL*2)-Constants.ROULETTE.RANDOM_INCREMENT_ROULETTE_INTERVAL);
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
        switch (targetItemIndex % Constants.COUNTS.CATEGORY_NUM) {
            case 0: return 0;
            case 1: return 1;
            case 2: return 2;
            case 3: return 4;
            case 4: return 7;
            case 5: return 6;
            case 6: return 5;
            case 7: return 3;
            default: return -1;
        }
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
