package com.wikimedia.assigment.cards;

public class Cards {

    private int viewType;
    private Object object;

    public Cards(int viewType, Object object) {
        this.viewType = viewType;
        this.object = object;
    }

    //getters

    public int getViewType() {
        return viewType;
    }

    public Object getObject() {
        return object;
    }
}
