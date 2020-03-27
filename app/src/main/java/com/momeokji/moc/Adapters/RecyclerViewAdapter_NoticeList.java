package com.momeokji.moc.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.R;
import com.momeokji.moc.data.Notice;

import java.util.ArrayList;

public class RecyclerViewAdapter_NoticeList extends RecyclerView.Adapter<RecyclerViewAdapter_NoticeList.ItemViewHolder> {

    ArrayList<Notice> noticeList = new ArrayList<Notice>();
    Context context;

    public interface OnItemClickListener{
        public void onItemClick(Notice notice, int type);
    }

    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter_NoticeList(Context context, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_NoticeList.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_notice_list, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_NoticeList.ItemViewHolder holder, final int position) {
        holder.onBind(noticeList.get(position));

        // 공지사항 클릭
        holder.noticeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 공지사항 눌러서 이동
                onItemClickListener.onItemClick(noticeList.get(position), 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return noticeList.size() ;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        View noticeLayout;
        TextView notice_titleTxt;

        ItemViewHolder(View ItemView){
            super(ItemView);
            noticeLayout = ItemView.findViewById(R.id.moreInfo_fragment_event_linearLayout);
            notice_titleTxt = ItemView.findViewById(R.id.notice_titleTxt);
        }
        public void onBind(final Notice notice) {
            notice_titleTxt.setText(notice.getNoticeTitle());

            noticeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        }

    }

    public void addNotice(Notice notice){
        noticeList.add(notice);
    }
}
