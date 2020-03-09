package com.momeokji.moc.Adapters;

import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

public class RecyclerViewAdapter_SearchedRestaurantList extends RecyclerView.Adapter<RecyclerViewAdapter_SearchedRestaurantList.ItemViewHolder>{

    private ArrayList<Restaurant> restaurantList;
    private MainActivity mainActivity;

    private OnItemClickListener viewHolderListener = null;

    public static interface OnItemClickListener{                                                       // ViewHolder의 커스텀 클릭 리스너
        void OnItemClick(View v, int position);                                                        // (클릭 리스너를 액티비티에서 구현하기 위해 defualt interface 사용)
    }

    public RecyclerViewAdapter_SearchedRestaurantList(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_searched_restaurant_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){
        holder.onBind(restaurantList.get(position));
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

                            ((InputMethodManager) mainActivity.getSystemService(mainActivity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);      // 키보드가 닫음
                            Restaurant selectedRestaurant = restaurantList.get(targetPos); //선택된 가게의 정보가 담긴 instance
                            mainActivity.restaurantDATA.selectedRestaurant = selectedRestaurant;
                            displayedFragmentManager.ReplaceFragment(0, new RestaurantInfoFragment(selectedRestaurant), Constants.ANIMATION_DIRECT.TO_RIGHT);

                    }
                });

            restaurantName_txt = itemView.findViewById(R.id.searchedRestaurantListItem_restaurantName_txt);
            restaurantDescription_txt = itemView.findViewById(R.id.searchedRestaurantListItem_description_txt);
            menuPriceRange_txt = itemView.findViewById(R.id.searchedRestaurantListItem_menuPriceRange_txt);
        }

        public void onBind(final Restaurant restaurant){
            restaurantName_txt.setText((restaurant.getRestaurantName()));
            restaurantDescription_txt.setText(restaurant.getPreview());
            menuPriceRange_txt.setText(restaurant.getMinMaxPrice());
        }
    }
}
