package com.momeokji.moc.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.momeokji.moc.Database.DatabaseQueryClass;
import com.momeokji.moc.Database.MyOnSuccessListener;
import com.momeokji.moc.MoreInfoFragment;
import com.momeokji.moc.MyReviewDeleteActivity;
import com.momeokji.moc.R;
import com.momeokji.moc.ViewReviewImageActivity;
import com.momeokji.moc.data.Review;

import java.util.ArrayList;

public class RecyclerViewAdapter_MyReview extends RecyclerView.Adapter<RecyclerViewAdapter_MyReview.ItemViewHolder>{
    ArrayList<Review> reviewList = new ArrayList<Review>();
    Context context;
    RequestManager mGlideRequestManager; // Glide manager

    public interface OnItemClickListener{
        public void onItemClick(Review review, int type);
    }

    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter_MyReview(Context context, RequestManager GlideRequestManager, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.context = context;
        mGlideRequestManager = GlideRequestManager;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_MyReview.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_myreview_tab_page, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_MyReview.ItemViewHolder holder, final int position) {
        holder.onBind(reviewList.get(position));
        // 내가 쓴 리뷰 삭제
        holder.reviewDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 선택한 리뷰 삭제
                onItemClickListener.onItemClick(reviewList.get(position), 1);

            }
        });

        // 리뷰 이미지 크게보기 액티비티
        holder.reviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(reviewList.get(position), 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviewList.size() ;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView reviewMenuName, reviewNickName, reviewText, reviewTime;
        private ImageView reviewImage;
        Button reviewDeleteBtn;
        ItemViewHolder(View ItemView){
            super(ItemView);
            reviewMenuName = ItemView.findViewById(R.id.reviewMenuName);
            reviewNickName = ItemView.findViewById(R.id.reviewNickName);
            reviewTime = ItemView.findViewById(R.id.reviewTime);
            reviewImage = ItemView.findViewById(R.id.reviewImageView);
            reviewText = ItemView.findViewById(R.id.reviewText);
            reviewDeleteBtn = ItemView.findViewById(R.id.myReviewDeleteBtn);
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

                }
            });


            reviewDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

    }

    public void addReview(Review review){
        reviewList.add(review);
    }
}
