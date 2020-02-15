package com.momeokji.moc.Adapters;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.MainActivity;
import com.momeokji.moc.R;
import com.momeokji.moc.RestaurantInfoFragment;
import com.momeokji.moc.data.Restaurant;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewAdapter_RestaurantList extends RecyclerView.Adapter<RecyclerViewAdapter_RestaurantList.ItemViewHolder>{

    private ArrayList<Restaurant> restaurantList;
    private String restaurantName; //선택한 가게이름
    private MainActivity mainActivity;
    private int selectedRecyclerViewItemPosition = -1;
    private LinearLayout expandedMenu_linearLayout;
    private Context context;
    private boolean isCurrItemExpanded = false;

    private OnItemClickListener viewHolderListener = null;                                      //

    public static interface OnItemClickListener{                                                       // ViewHolder의 커스텀 클릭 리스너
        void OnItemClick(View v, int position);                                                        // (클릭 리스너를 액티비티에서 구현하기 위해 defualt interface 사용)
    }
    public RecyclerViewAdapter_RestaurantList(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    public void setOnItemClickListener(OnItemClickListener viewHolderListener){
        this.viewHolderListener = viewHolderListener;
    }

    public RecyclerViewAdapter_RestaurantList(Context context) {
        this.context = context;
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
        private TextView restaurantName_txt, restaurantDescription_txt, menuPriceRange_txt;
        private ArrayList<TextView> mainMenus = new ArrayList<>();
        private ImageButton menuExpand_imgbtn;

        ItemViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        ((MainActivity)context).ReplaceFragment(new RestaurantInfoFragment());
/*                        int targetPos = getAdapterPosition();
                        if(targetPos != RecyclerView.NO_POSITION) {
                            if (viewHolderListener != null)
                                viewHolderListener.OnItemClick(view, targetPos);
                        }*/
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
        public void onBind(Restaurant restaurant, final int position){
            restaurantName_txt.setText((restaurant.getRestaurantName()));
            restaurantDescription_txt.setText(restaurant.getPreview());
            menuPriceRange_txt.setText(restaurant.getMinMaxPrice());
            for(int i = 0; i < 3; i++) {    //TODO 상수 클래스 적용하기
                mainMenus.get(i*2).setText(restaurant.getMainMenus()[i].getName());
                mainMenus.get(i*2+1).setText(restaurant.getMainMenus()[i].getPrice() + "원");
            }


            menuExpand_imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkSelectedItem(position);
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
        int height = (int) (76 * context.getResources().getDisplayMetrics().density);
        ValueAnimator valueAnimator;
        if(isTargetItemExpanded) {
            valueAnimator = ValueAnimator.ofInt(0, height);
        }
        else {
            valueAnimator = ValueAnimator.ofInt(height, 0);
        }
        valueAnimator.setDuration(100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();

                expandedMenu_linearLayout.getLayoutParams().height = value;
                expandedMenu_linearLayout.requestLayout();
                expandedMenu_linearLayout.setVisibility(isTargetItemExpanded ? View.VISIBLE : View.GONE);
            }
        });
        valueAnimator.start();
    }
}
