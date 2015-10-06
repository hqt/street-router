package com.fpt.router.model.bus;

/**
 * Created by asus on 10/5/2015.
 */
public class DetailRoute {
    private String title;
    private String subTitle;
    private int image;

    public DetailRoute(){

    }

    public DetailRoute(String title, String subTitle, int image){
        this.title = title;
        this.subTitle = subTitle;
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }
}
