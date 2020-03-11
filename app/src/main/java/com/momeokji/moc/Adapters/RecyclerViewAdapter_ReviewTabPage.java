package com.momeokji.moc.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.R;
import com.momeokji.moc.data.Review;

import java.util.ArrayList;

public class RecyclerViewAdapter_ReviewTabPage extends RecyclerView.Adapter<RecyclerViewAdapter_ReviewTabPage.ItemViewHolder> {

    private ArrayList<Review> reviewList;
    public RecyclerViewAdapter_ReviewTabPage(ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_review_tab_page, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(reviewList.get(position));
    }

    @Override
    public int getItemCount() {
        return reviewList.size() ;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView reviewMenuName, reviewNickName, reviewText, reviewTime;
        private ImageView reviewImage;
        ItemViewHolder(View ItemView){
            super(ItemView);
            reviewMenuName = ItemView.findViewById(R.id.reviewMenuName);
            reviewNickName = ItemView.findViewById(R.id.reviewNickName);
            reviewTime = ItemView.findViewById(R.id.reviewTime);
            reviewImage = ItemView.findViewById(R.id.reviewImageView);
            reviewText = ItemView.findViewById(R.id.reviewText);
        }
        public void onBind(Review review) {
            //           reviewNickName.setText(review);
            //           reviewImage.setImageURI();
            //           reviewText.setText();

        }
    }






}