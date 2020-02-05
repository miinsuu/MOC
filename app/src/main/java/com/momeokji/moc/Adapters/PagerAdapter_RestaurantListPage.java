package com.momeokji.moc.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.momeokji.moc.R;

public class PagerAdapter_RestaurantListPage extends PagerAdapter {

    private Context context = null;

    public PagerAdapter_RestaurantListPage(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        View view = null;
        if (context != null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.page_restaurant_list, container, false);
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 8; //TODO 상수 클래스 만들어서 CategryNum 으로 변경
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View) object);
    }
}
