package com.olssonjonas.grocerylistapp.model;

import java.io.Serializable;

public class GroceryList implements Serializable {

    private static final long serialVersionUID = 2L;

    private Integer id;
    private String itemName;
    private int listName;

    public GroceryList(String itemName, int listName) {
        this.id = null;
        this.itemName = itemName;
        this.listName = listName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getListName() {
        return listName;
    }

    public void setListName(int listName) {
        this.listName = listName;
    }

    @Override
    public String toString() {
        return "GroceryList{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", listName=" + listName +
                '}';
    }
}
