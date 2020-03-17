package com.momeokji.moc.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.momeokji.moc.MainActivity;
import com.momeokji.moc.R;
import com.momeokji.moc.ViewReviewImageActivity;
import com.momeokji.moc.data.Review;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class RecyclerViewAdapter_ReviewTabPage extends RecyclerView.Adapter<RecyclerViewAdapter_ReviewTabPage.ItemViewHolder> {
    ArrayList<Review> reviewList = new ArrayList<Review>();
    Context context;
    RequestManager mGlideRequestManager; // Glide manager

    public RecyclerViewAdapter_ReviewTabPage(Context context, RequestManager GlideRequestManager) {
        this.context = context;
        mGlideRequestManager = GlideRequestManager;
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
        public void onBind(final Review review) {
            reviewMenuName.setText(review.getReviewMenuName());
            reviewNickName.setText(review.getReviewNickName());
            reviewTime.setText(review.getReviewTime());
            mGlideRequestManager.load(review.getReviewImageUri()).into(reviewImage);
            reviewText.setText(review.getReviewText());

            reviewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(review.getReviewImageUri() == null) {
                        return;
                    } else {
                        Intent intent = new Intent(context, ViewReviewImageActivity.class);
                        intent.putExtra("imageUri", review.getReviewImageUri().toString());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    public void addReview(Review review){
        reviewList.add(review);
    }

    public ArrayList<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
    }
}