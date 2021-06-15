package com.wikimedia.assigment.cards;

import java.util.ArrayList;

public class RandomArticleCard {

    private String pageid;
    private String title;
    private String revisions ;

    public RandomArticleCard(String pageid, String title, String revisions) {
        this.pageid = pageid;
        this.title = title;
        this.revisions = revisions;
    }

    //getters and setters

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRevisions() {
        return revisions;
    }

    public void setRevisions(String revisions) {
        this.revisions = revisions;
    }
}
