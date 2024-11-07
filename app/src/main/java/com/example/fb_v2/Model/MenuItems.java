package com.example.fb_v2.Model;


public class MenuItems {
    private String name;
    private int iconResId;

    public MenuItems(String name, int iconResId) {
        this.name = name;
        this.iconResId = iconResId;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }


}
