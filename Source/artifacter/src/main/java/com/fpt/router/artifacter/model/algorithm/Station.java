package com.fpt.router.artifacter.model.algorithm;

import com.fpt.router.artifacter.model.helper.Location;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 9/26/2015.
 */
public class Station implements IAlgorithmModel {

    public int id;

    public String code;

    public String name;

    public String street;

    public Location location;

    // public List<PathInfo> from;

    // public List<PathInfo> to;

    // list all routes go through this station (this field will be built in CityMap)
    public List<Route> routes;

    // list all foot-path stationMap go through this station
    public List<Station> stations;


    public Station(){

    }


    @Override
    public void build() {

    }
}
