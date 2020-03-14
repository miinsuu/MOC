package com.momeokji.moc;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.Adapters.RecyclerViewAdapter_ReviewTabPage;
import com.momeokji.moc.data.Restaurant;
import com.momeokji.moc.data.Review;

import java.util.ArrayList;

import static com.momeokji.moc.MainActivity.displayedFragmentManager;


public class RestaurantInfoReviewTabPage extends Fragment {

    private Context context;
    private Restaurant selectedRestaurant;
    private ArrayList<Review> reviews;

    public RestaurantInfoReviewTabPage(Context context, Restaurant selectedRestaurant, ArrayList<Review> reviews) {
        this.context = context;
        this.selectedRestaurant = ((MainActivity)context).restaurantDATA.selectedRestaurant;;
        this.reviews = reviews;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_restaurant_info_review_tab_page, container, false);

        Button writeReviewBtn = view.findViewById(R.id.writeReview_btn);
        RecyclerView review_recyclerView = view.findViewById(R.id.review_recyclerView);

        reviews = Review.createContactsList(5);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        review_recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerViewAdapter_ReviewTabPage adapterReviewTabPage= new RecyclerViewAdapter_ReviewTabPage(reviews);
        review_recyclerView.setAdapter(adapterReviewTabPage);

        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayedFragmentManager.ReplaceFragment(0, new WriteReview(selectedRestaurant),0);

            }
        });

        return view;
    }
/*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
    }

    private void initDataset() {
        //for Test
        ArrayList<Review> mData = new ArrayList<>();
        mData.add(new Review("asd", "ohoh","20200312","wowowowo"));
        mData.add(new Review("sf", "hhh","20200202","haha"));
    }

 */
}
