package com.momeokji.moc.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.momeokji.moc.R;
import com.momeokji.moc.data.CardNews;

import java.util.ArrayList;
import java.util.Map;

public class RecyclerViewAdapter_CardNews extends RecyclerView.Adapter<RecyclerViewAdapter_CardNews.ItemViewHolder> {

    String[] imgUriList ;
    Context context;
    RequestManager mGlideRequestManager; // Glide manager

    public RecyclerViewAdapter_CardNews(Context context, RequestManager GlideRequestManager) {
        this.context = context;
        mGlideRequestManager = GlideRequestManager;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_CardNews.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_cardnews, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_CardNews.ItemViewHolder holder, final int position) {
        holder.onBind(imgUriList[position]);

    }

    @Override
    public int getItemCount() {
        return imgUriList.length ;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView cardNews_img;

        ItemViewHolder(View ItemView){
            super(ItemView);
            cardNews_img = ItemView.findViewById(R.id.cardNews_img);
        }
        public void onBind(final String imgUri) {
            // 이미지 띄우기
            mGlideRequestManager.load(Uri.parse(imgUri)).into(cardNews_img);
        }

    }


    public void setImgUriList(String[] imgUriList){
        this.imgUriList = imgUriList;
    }

}
