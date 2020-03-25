package com.momeokji.moc.data;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class MyListMenu extends Menu {

    private String restaurantName;

    public MyListMenu(@NonNull Menu targetMenu, String restaurantName) {
        super(targetMenu.getName(), targetMenu.getPrice(), targetMenu.getPhoto());
        this.restaurantName = restaurantName;
    }

    // 메뉴이름, 가격, 가게이름 세가지 모두 같으면 같은 메뉴객체라고 판단
    @Override
    public boolean equals(@Nullable Object obj) {
        boolean sameMenu = false;

        if(obj != null && obj instanceof MyListMenu) {
            sameMenu = (this.getName() == ((MyListMenu) obj).getName())
                    && (this.getPrice() == ((MyListMenu) obj).getPrice())
                    && (this.getRestaurantName() == ((MyListMenu) obj).getRestaurantName());
        }

        return sameMenu;
    }

    public String getRestaurantName() {
        return this.restaurantName;
    }
}
