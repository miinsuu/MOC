package com.momeokji.moc.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.R;
import com.momeokji.moc.data.CardNews;
import com.momeokji.moc.data.Notice;

import java.util.ArrayList;

public class RecyclerViewAdapter_CardNewsList extends RecyclerView.Adapter<RecyclerViewAdapter_CardNewsList.ItemViewHolder> {

    ArrayList<CardNews> cardNewsList = new ArrayList<CardNews>();
    Context context;

    public interface OnItemClickListener{
        public void onItemClick(CardNews cardNews, int type);
    }

    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter_CardNewsList(Context context, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_CardNewsList.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_cardnews_list, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_CardNewsList.ItemViewHolder holder, final int position) {
        holder.onBind(cardNewsList.get(position));

        // 카드뉴스 리스트 항목 클릭
        holder.cardNewsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 항목 눌러서 이동
                onItemClickListener.onItemClick(cardNewsList.get(position), 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cardNewsList.size() ;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        View cardNewsLayout;
        TextView cardNewsList_titleTxt;

        ItemViewHolder(View ItemView){
            super(ItemView);
            cardNewsLayout = ItemView.findViewById(R.id.cardNewsList_linearLayout);
            cardNewsList_titleTxt = ItemView.findViewById(R.id.cardNewsList_titleTxt);
        }
        public void onBind(final CardNews cardNews) {
            cardNewsList_titleTxt.setText(cardNews.getCardNewsTitle());

            cardNewsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        }

    }

    public void addCardNews(CardNews cardNews){
        cardNewsList.add(cardNews);
    }
}
