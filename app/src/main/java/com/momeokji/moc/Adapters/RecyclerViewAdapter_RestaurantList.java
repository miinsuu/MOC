package com.momeokji.moc.Adapters;

import android.animation.ValueAnimator;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import static com.momeokji.moc.MainActivity.displayedFragmentManager;

public class RecyclerViewAdapter_RestaurantList extends RecyclerView.Adapter<RecyclerViewAdapter_RestaurantList.ItemViewHolder>{

    private ArrayList<Restaurant> restaurantList;
    private int preSelectedItem = -1;
    private MainActivity mainActivity;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

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
        private LinearLayout restaurantListItem_menuExpand_linearLayout;
        private ImageButton menuExpand_imgbtn;
        private LinearLayout expandedMenu_linearLayout;

        ItemViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                            Restaurant selectedRestaurant = restaurantList.get(getAdapterPosition()); //선택된 가게의 정보가 담긴 instance
                            mainActivity.restaurantDATA.selectedRestaurant = selectedRestaurant;

                            displayedFragmentManager.ReplaceFragment(0, new RestaurantInfoFragment(selectedRestaurant), Constants.ANIMATION_DIRECT.TO_RIGHT);

                    }
                });

            restaurantName_txt = itemView.findViewById(R.id.restaurantListItem_restaurantName_txt);
            restaurantDescription_txt = itemView.findViewById(R.id.restaurantListItem_description_txt);
            menuPriceRange_txt = itemView.findViewById(R.id.restaurantListItem_menuPriceRange_txt);
            restaurantListItem_menuExpand_linearLayout =  itemView.findViewById(R.id.restaurantListItem_menuExpand_linearLayout);
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

            TurnArrow(menuExpand_imgbtn, selectedItems.get(position));
            changeVisibility(position);

            View.OnClickListener onClickListener =new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkSelectedItem(restaurantList.indexOf(restaurant));
                }
            };
            menuExpand_imgbtn.setOnClickListener(onClickListener);
            restaurantListItem_menuExpand_linearLayout.setOnClickListener(onClickListener);
        }

        public void checkSelectedItem(int selectedItemPosition) {
            if (selectedItems.get(selectedItemPosition)) {
                selectedItems.delete(selectedItemPosition);
            }
            else {
                selectedItems.delete(preSelectedItem);
                selectedItems.put(selectedItemPosition, true);
            }

            if (preSelectedItem != -1) {
                notifyItemChanged(preSelectedItem);
            }
            notifyItemChanged(selectedItemPosition);
            preSelectedItem = selectedItemPosition;
        }

        private void changeVisibility(int position) {
            final boolean isTargetItemExpanded = selectedItems.get(position);

            int height = (int) (Constants.XML_DESIGN.EXPANDABLE_MAINS_HEIGHT * mainActivity.getResources().getDisplayMetrics().density);
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
        public void TurnArrow(ImageButton arrow, boolean direction) {
            Animation animation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), (direction ? R.anim.anim_rotate_counter_clockwise_with_arrow_down : R.anim.anim_rotate_clockwise_with_arrow_up));
            animation.setFillAfter(true);
            arrow.startAnimation(animation);
        }
    }
}
