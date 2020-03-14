package com.momeokji.moc.Adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter_WriteReviewExpandableMenuList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    Context context;
    private List<Item> data;
    public static String reviewMenuName = "";
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    public RecyclerViewAdapter_WriteReviewExpandableMenuList(Context context, List<Item> data) {
        this.context = context;
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
 /*
                CheckBox checkBoxMenu = new CheckBox(context);
                checkBoxMenu.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom);
                checkBoxMenu.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                return new RecyclerView.ViewHolder(checkBoxMenu) {
                };

  */
        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
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
                childViewHolder.checkBoxMenu.setText(item.text);
                childViewHolder.checkBoxMenu.setChecked(item.isSelected);
                childViewHolder.checkBoxMenu.setTag(data.get(position));
                childViewHolder.checkBoxMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CheckBox cb = (CheckBox) v;
                        Item item1 = (Item)childViewHolder.checkBoxMenu.getTag();
 //                       item1.setSelected(childViewHolder.checkBoxMenu.isChecked());
                        data.get(position).setSelected(cb.isChecked());
                        for(int j = 0; j <data.size(); j++){
                            if (data.get(j).isSelected == true) {
                                reviewMenuName += data.get(j).getText();
                                reviewMenuName += " ";
                            }
                        }
 //                       Toast.makeText(context,reviewMenuName,Toast.LENGTH_SHORT).show();

                    }
                });
               // childViewHolder.checkBoxMenu.setChecked();
 //               TextView itemTextView = (TextView) holder.itemView;
 //               itemTextView.setText(data.get(position).text);
 //               CheckBox checkBoxMenu = (CheckBox) holder.itemView;
 //               checkBoxMenu.setText(data.get(position).text);


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

    public List<Item> getData() {
        return data;
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

    public static class ListChildViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBoxMenu;
        public Item menuItem;

        public ListChildViewHolder(View itemView) {
            super(itemView);
            checkBoxMenu = (CheckBox) itemView.findViewById(R.id.checkBoxMenu);

        }
    }

    public static class Item {
        public int type;
        public String text;
        public boolean isSelected;
        public List<Item> invisibleChildren;

        public Item() {
        }

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }

        public Item(int type, String text, boolean isSelected) {
            this.type = type;
            this.text = text;
            this.isSelected = isSelected;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

}
