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


public class RestaurantInfoReviewTabPage extends Fragment {
/*
    private ArrayList<reviewData> rlist ;
    private RecyclerView review_recyclerView;
    private RecyclerViewAdapter_ReviewTabPage review_adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant_info_review_tab_page, container, false);

        review_recyclerView = (RecyclerView) view.findViewById(R.id.review_recyclerView);
        review_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        review_adapter = new RecyclerViewAdapter_ReviewTabPage(rlist);
        review_recyclerView.setAdapter(review_adapter);

        return view;
    }

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
        ArrayList<reviewData> testitem = new ArrayList<>();
        testitem.add(new reviewData("test1"));
        testitem.add(new reviewData("test2"));
    }


*/

    private Context context;
    private Restaurant selectedRestaurant;
    RecyclerViewAdapter_ReviewTabPage recyclerViewAdapter_reviewTabPage;

    public RestaurantInfoReviewTabPage(Context context) {
        this.context = context;
        this.selectedRestaurant = ((MainActivity)context).restaurantDATA.selectedRestaurant;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_restaurant_info_review_tab_page, container, false);

        Button writeReviewBtn = view.findViewById(R.id.writeReview_btn);
        RecyclerView review_recyclerView = view.findViewById(R.id.review_recyclerView);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        review_recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerViewAdapter_reviewTabPage = new RecyclerViewAdapter_ReviewTabPage()

        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)context).ReplaceFragment(0, new WriteReview(selectedRestaurant),0);

            }
        });

        return view;
    }
}
