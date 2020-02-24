package com.momeokji.moc.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.R;
import com.momeokji.moc.reviewData;

import java.util.ArrayList;

public class RecyclerViewAdapter_ReviewTabPage extends RecyclerView.Adapter<RecyclerViewAdapter_ReviewTabPage.ViewHolder> {

    private ArrayList<reviewData> mDataset;
    public RecyclerViewAdapter_ReviewTabPage(ArrayList<reviewData> searchDataSet, Activity activity) {
        mDataset = searchDataSet;
    }

    public RecyclerViewAdapter_ReviewTabPage(ArrayList<reviewData> list) {
        this.mDataset = list;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView rtextview;

        public ViewHolder(View view) {
            super(view) ;

            rtextview = (TextView) view.findViewById(R.id.reviewtext);
        }
    }

//    RecyclerViewAdapter_ReviewTabPage(ArrayList<String> list) {
//        mData = list;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_review_tab_page,parent,false) ;
        ViewHolder viewHolder = new ViewHolder(view) ;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_ReviewTabPage.ViewHolder holder, int position) {
//        String text = mDataset.get(position);
        holder.rtextview.setText(mDataset.get(position).review);
    }

    @Override
    public int getItemCount() {
        return mDataset.size() ;
    }
}
