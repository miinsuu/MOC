package com.momeokji.moc.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter_WriteReviewExpandableMenuList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<Item> data;

    public RecyclerViewAdapter_WriteReviewExpandableMenuList(List<Item> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (type) {
            case HEADER:
                view = inflater.inflate(R.layout.fragment_write_review_menu_dialog_header, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
//                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.fragment_write_review_menu_dialog_child, parent, false);
                ListChildViewHolder child = new ListChildViewHolder(view);
                return child;
 /*               TextView itemtextView = new TextView(context);
                itemtextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom);
                itemtextView.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                return new RecyclerView.ViewHolder(itemtextView) {
                };

  */
        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);
        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item;
                itemController.menuCategoryText.setText(item.text);
                if (item.invisibleChildren == null) {
                    itemController.expandToggleBtn.setImageResource(R.drawable.i_arrow_up);
                } else {
                    itemController.expandToggleBtn.setImageResource(R.drawable.i_arrow_down);
                }
                itemController.expandToggleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos+1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.expandToggleBtn.setImageResource(R.drawable.i_arrow_down);
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.expandToggleBtn.setImageResource(R.drawable.i_arrow_up);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:
                final ListChildViewHolder childViewHolder = (ListChildViewHolder) holder;
                childViewHolder.menuItem = item;
                childViewHolder.menuNameText.setText(item.text);
 //               TextView itemTextView = (TextView) holder.itemView;
 //               itemTextView.setText(data.get(position).text);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView menuCategoryText;
        public ImageView expandToggleBtn;
        public Item refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            menuCategoryText = (TextView) itemView.findViewById(R.id.menuCategoryText);
            expandToggleBtn = (ImageView) itemView.findViewById(R.id.expandToggleBtn);
        }
    }

    private static class ListChildViewHolder extends RecyclerView.ViewHolder {
        public TextView menuNameText;
        public Item menuItem;

        public ListChildViewHolder(View itemView) {
            super(itemView);
            menuNameText = (TextView) itemView.findViewById(R.id.menuNameText);

        }
    }

    public static class Item {
        public int type;
        public String text;
        public List<Item> invisibleChildren;

        public Item() {
        }

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }
    }

}
