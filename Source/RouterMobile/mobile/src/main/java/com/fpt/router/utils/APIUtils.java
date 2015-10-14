package com.fpt.router.utils;

import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.model.bus.BusLocation;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Huynh Quang Thao on 10/14/15.
 */
public class APIUtils {
    public static String getJsonFromServer(List<BusLocation> busLocations){
        BusLocation A = busLocations.get(0);
        BusLocation B = busLocations.get(1);
        Calendar now = Calendar.getInstance();
        String addressA = null;
        String addressB = null;
        try{
            addressA = URLEncoder.encode(A.getAddress(), "UTF-8");
            addressB = URLEncoder.encode(B.getAddress(),"UTF-8");
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }

        String url = AppConstants.API.SEARCH_BUS_ROUTE + "?latA="+ A.getLatitude()
                +"&latB="+B.getLatitude()+"&longA="+A.getLongitude()
                +"&longB="+B.getLongitude()+"&hour="+now.get(Calendar.HOUR_OF_DAY)+"&minute="+now.get(Calendar.MINUTE)
                +"&addressA="+addressA+"&addressB="+addressB;
        String json = NetworkUtils.download(url);
        return json ;
    }

    public static String getLocationGoogleAPI(String addressOfLocation){
        String key = AppConstants.GOOGLE_KEY;
        String address = addressOfLocation;
        try {
            address = URLEncoder.encode(address,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://maps.google.com/maps/api/geocode/json?address="+address;
        String json = NetworkUtils.download(url);
        return json;
    }


}
