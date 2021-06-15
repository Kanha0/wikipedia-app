package com.wikimedia.assigment.cards;

import java.util.ArrayList;

public class CategoryCard {

    private ArrayList<String> category ;
    private static int viewType;

    public CategoryCard(ArrayList<String> category) {
        this.category = category;
    }


// getters and setters


    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }

}
