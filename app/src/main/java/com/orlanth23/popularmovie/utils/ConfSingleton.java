package com.orlanth23.popularmovie.utils;

public final class ConfSingleton {
    private static volatile ConfSingleton INSTANCE;

    private static boolean twoPane;

    public final static ConfSingleton getInstance(){
        if (ConfSingleton.INSTANCE == null) {
            synchronized(ConfSingleton.class) {
                if (ConfSingleton.INSTANCE == null) {
                    ConfSingleton.INSTANCE = new ConfSingleton();
                }
            }
        }
        return ConfSingleton.INSTANCE;
    }


    private ConfSingleton() {
        super();
    }

    public static boolean isTwoPane() {
        return twoPane;
    }

    public static void setTwoPane(boolean twoPane) {
        ConfSingleton.twoPane = twoPane;
    }
}
