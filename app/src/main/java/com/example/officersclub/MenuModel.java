package com.example.officersclub;


public class MenuModel {

    public String menuName;
    public boolean hasChildren, isGroup;
    public int resID;

    public MenuModel(String menuName,int resID, boolean isGroup, boolean hasChildren) {

        this.menuName = menuName;
        this.resID = resID;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}