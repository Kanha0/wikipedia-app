package com.wikimedia.assigment.data;

import com.wikimedia.assigment.cards.CategoryCard;
import com.wikimedia.assigment.cards.ImageCard;
import com.wikimedia.assigment.cards.RandomArticleCard;

import java.util.ArrayList;

public class Query {

    private ArrayList<ImageCard> ImageList = new ArrayList<>();
    private ArrayList<CategoryCard> CategoryList = new ArrayList<>();
    private ArrayList<RandomArticleCard> ArticleList = new ArrayList<>();

    public Query(ArrayList<ImageCard> imageList, ArrayList<CategoryCard> categoryList, ArrayList<RandomArticleCard> articleList) {
        ImageList = imageList;
        CategoryList = categoryList;
        ArticleList = articleList;
    }

    // getters and setters
    public ArrayList<ImageCard> getImageList() {
        return ImageList;
    }

    public void setImageList(ArrayList<ImageCard> imageList) {
        ImageList = imageList;
    }

    public ArrayList<CategoryCard> getCategoryList() {
        return CategoryList;
    }

    public void setCategoryList(ArrayList<CategoryCard> categoryList) {
        CategoryList = categoryList;
    }

    public ArrayList<RandomArticleCard> getArticleList() {
        return ArticleList;
    }

    public void setArticleList(ArrayList<RandomArticleCard> articleList) {
        ArticleList = articleList;
    }
}
