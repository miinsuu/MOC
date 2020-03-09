package com.momeokji.moc.Adapters;

import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.CustomView.MarqueeTextView;
import com.momeokji.moc.Helper.Constants;
import com.momeokji.moc.MainActivity;
import com.momeokji.moc.R;
import com.momeokji.moc.RestaurantInfoFragment;
import com.momeokji.moc.data.Restaurant;

import java.util.ArrayList;

public class RecyclerViewAdapter_RestaurantList extends RecyclerView.Adapter<RecyclerViewAdapter_RestaurantList.ItemViewHolder>{

    private ArrayList<Restaurant> restaurantList;
    private int selectedRecyclerViewItemPosition = -1;
    private LinearLayout expandedMenu_linearLayout;
    private MainActivity mainActivity;
    private boolean isCurrItemExpanded = false;

    private OnItemClickListener viewHolderListener = null;

    public static interface OnItemClickListener{                                                       // ViewHolder의 커스텀 클릭 리스너
        void OnItemClick(View v, int position);                                                        // (클릭 리스너를 액티비티에서 구현하기 위해 defualt interface 사용)
    }

    public RecyclerViewAdapter_RestaurantList(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_restaurant_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){
        holder.onBind(restaurantList.get(position), position);
    }

    @Override
    public int getItemCount(){ return restaurantList.size();  }

    public ArrayList<Restaurant> getRestaurantList(){
        return this.restaurantList;
    }
    public void setRestaurantList(ArrayList<Restaurant> restaurantList){
        this.restaurantList = restaurantList;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView restaurantName_txt, menuPriceRange_txt;
        private MarqueeTextView restaurantDescription_txt;
        private ArrayList<TextView> mainMenus = new ArrayList<>();
        private ImageButton menuExpand_imgbtn;

        ItemViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {

                            int targetPos = getAdapterPosition();
                            if(targetPos != RecyclerView.NO_POSITION) {
                                if (viewHolderListener != null)
                                    viewHolderListener.OnItemClick(view, targetPos);
                            }
                            Restaurant selectedRestaurant = restaurantList.get(targetPos); //선택된 가게의 정보가 담긴 instance
                            mainActivity.restaurantDATA.selectedRestaurant = selectedRestaurant;

                            mainActivity.ReplaceFragment(0, new RestaurantInfoFragment(selectedRestaurant), Constants.ANIMATION_DIRECT.TO_RIGHT);

                    }
                });

            restaurantName_txt = itemView.findViewById(R.id.restaurantListItem_restaurantName_txt);
            restaurantDescription_txt = itemView.findViewById(R.id.restaurantListItem_description_txt);
            menuPriceRange_txt = itemView.findViewById(R.id.restaurantListItem_menuPriceRange_txt);
            menuExpand_imgbtn = itemView.findViewById(R.id.restaurantListItem_menuExpand_imgbtn);
            expandedMenu_linearLayout = itemView.findViewById(R.id.expandedMenu_linearLayout);

            mainMenus.add((TextView) itemView.findViewById(R.id.mainMenu1Name_Txt));
            mainMenus.add((TextView) itemView.findViewById(R.id.mainMenu1Price_Txt));
            mainMenus.add((TextView) itemView.findViewById(R.id.mainMenu2Name_Txt));
            mainMenus.add((TextView) itemView.findViewById(R.id.mainMenu2Price_Txt));
            mainMenus.add((TextView) itemView.findViewById(R.id.mainMenu3Name_Txt));
            mainMenus.add((TextView) itemView.findViewById(R.id.mainMenu3Price_Txt));
        }
        public void onBind(final Restaurant restaurant, final int position){
            restaurantName_txt.setText((restaurant.getRestaurantName()));
            restaurantDescription_txt.setText(restaurant.getPreview());
            menuPriceRange_txt.setText(restaurant.getMinMaxPrice());
            for(int i = 0; i < Constants.COUNTS.MAX_MAINS_NUM; i++) {
                mainMenus.get(i*2).setText(restaurant.getMainMenus()[i].getName());
                mainMenus.get(i*2+1).setText(restaurant.getMainMenus()[i].getPrice() + "원");
            }


            menuExpand_imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checkSelectedItem(restaurantList.indexOf(restaurant));
                }
            });
            changeVisibility(position);
        }
    }

    public void checkSelectedItem(int selectedItemPosition) {
        if (this.selectedRecyclerViewItemPosition == selectedItemPosition) {
            isCurrItemExpanded = false;
            notifyItemChanged(this.selectedRecyclerViewItemPosition);
        }
        else {
            isCurrItemExpanded = true;
            if (this.selectedRecyclerViewItemPosition != -1) {
                notifyItemChanged(this.selectedRecyclerViewItemPosition);
            }
            notifyItemChanged(selectedItemPosition);
            this.selectedRecyclerViewItemPosition = selectedItemPosition;
        }
    }

    private void changeVisibility(int position) {
        final boolean isTargetItemExpanded = (this.selectedRecyclerViewItemPosition == position) && isCurrItemExpanded;
        if (!isCurrItemExpanded) {
            this.selectedRecyclerViewItemPosition = -1;
        }

        int height = (int) (Constants.DESIGN_SIZE.EXPANDABLE_MAINS_HEIGHT * mainActivity.getResources().getDisplayMetrics().density);
        ValueAnimator valueAnimator = isTargetItemExpanded ? (ValueAnimator.ofInt(0, height)) : (ValueAnimator.ofInt(height, 0));
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                expandedMenu_linearLayout.getLayoutParams().height = (int) animation.getAnimatedValue();
                expandedMenu_linearLayout.requestLayout();
                expandedMenu_linearLayout.setVisibility(isTargetItemExpanded ? View.VISIBLE : View.GONE);
            }
        });

        valueAnimator.start();
    }
}
