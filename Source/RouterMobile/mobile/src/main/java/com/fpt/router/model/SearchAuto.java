package com.fpt.router.model;

/**
 * Created by asus on 10/6/2015.
 */
public class SearchAuto {
    private String title;
    private int image;

    public SearchAuto(){

    }

    public SearchAuto(String title, int image){
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
