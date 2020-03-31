package com.momeokji.moc.Helper;

public class Constants {

    public static class COUNTS {
        public static final int CATEGORY_NUM = 8;
        public static final int MAX_MAINS_NUM = 3;
    }

    public static class DELAYS {
        public static final int BACK_PRESS_TO_CLOSE_APP_DELAY = 1000;
        public static final int LOADING_DELAY = 400;
        public static final int ANIMATION_DELAY = 300;
    }

    public static class XML_DESIGN {
        public static final int NAVIGATION_BAR_HEIGHT_IN_DP = 56;
        public static final int MY_LIST_BTN_MARGIN_IN_DP = 5;
        public static final int EXPANDABLE_MAINS_HEIGHT = 76;
        public static final double MYLIST_HEIGHT_RATIO = 0.85;
        public static final boolean MYLISY_BTN_ABOVE_POSITION = true;
        public static final boolean MYLISY_BTN_BELOW_POSITION = false;
    }

    public static class ANIMATION_DIRECT {
        public static final int TO_RIGHT = 0;
        public static final int TO_LEFT = 1;
    }

    public static class ROULETTE {
        public final static int INIT_ROULETTE_INTERVAL = 100;
        public final static int INCREMENT_ROULETTE_INTERVAL = 10;
        public final static int RANDOM_INCREMENT_ROULETTE_INTERVAL = 15;
        public final static int MAX_ROULETTE_INTERVAL = 200;
    }

    public static class NAVIGATION_ITEM {
        public final static int HOME = 0;
        public final static int RESTAURANT_LIST = 1;
        public final static int ROULETTE = 2;
        public final static int MORE_INFO = 3;
    }
}
