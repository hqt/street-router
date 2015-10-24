package com.fpt.router.library.model.motorbike;

/**
 * Created by Nguyen Trung Nam on 10/24/2015.
 */
public class AutocompleteObject {
    private String name;
    private String place_id;

    public AutocompleteObject(String name, String place_id) {
        this.name = name;
        this.place_id = place_id;
    }

    public AutocompleteObject() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }
}
