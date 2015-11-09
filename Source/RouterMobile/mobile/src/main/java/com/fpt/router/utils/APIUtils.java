package com.fpt.router.utils;

import android.util.Log;

import com.fpt.router.activity.SearchRouteActivity;
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
        BusLocation startPoint = null;
        BusLocation endPoint = null;
        BusLocation middleFirstPoint = null;
        BusLocation middleSecondPoint = null;
        String addressStart = null;
        String addressEnd = null;
        String addressMiddleFirst = null;
        String addressMiddleSecond = null;
        int hour = SearchRouteActivity.pHour;
        int minute =SearchRouteActivity.pMinute;
        Calendar now = Calendar.getInstance();
        if((hour < 0) || (minute < 0)){
            hour = now.get(Calendar.HOUR_OF_DAY);
            minute = now.get(Calendar.MINUTE);
        }
        if(busLocations.size() == 2){
           startPoint =  busLocations.get(0);
           endPoint =  busLocations.get(1);

            try{
                addressStart = URLEncoder.encode(startPoint.getAddress(), "UTF-8");
                addressEnd = URLEncoder.encode(endPoint.getAddress(),"UTF-8");
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }

            String url = AppConstants.API.SEARCH_BUS_ROUTE + "?latStart="+ startPoint.getLatitude()
                    +"&latEnd="+endPoint.getLatitude()+"&longStart="+startPoint.getLongitude()
                    +"&longEnd="+endPoint.getLongitude()+"&hour="+hour+"&minute="+minute
                    +"&addressStart="+addressStart+"&addressEnd="+addressEnd
                    +"&walkingDistance="+ SearchRouteActivity.walkingDistance+"&transferTurn="+SearchRouteActivity.transferNumber;
            Log.d("URL TWO POINT : ",url);
            String json = NetworkUtils.download(url);
            return json ;
        }else if(busLocations.size() == 3){
            startPoint =  busLocations.get(0);
            endPoint =  busLocations.get(1);
            middleFirstPoint = busLocations.get(2);
            try{
                addressStart = URLEncoder.encode(startPoint.getAddress(), "UTF-8");
                addressEnd = URLEncoder.encode(endPoint.getAddress(),"UTF-8");
                addressMiddleFirst = URLEncoder.encode(middleFirstPoint.getAddress(),"UTF-8");
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }

            String url = AppConstants.API.SEARCH_BUS_ROUTE_FOUR_POINT + "?latStart="+ startPoint.getLatitude()
                    +"&latEnd="+endPoint.getLatitude()+"&longStart="+startPoint.getLongitude()
                    +"&longEnd="+endPoint.getLongitude()
                    +"&latMidFirst="+middleFirstPoint.getLatitude()+"&longMidFirst="+middleFirstPoint.getLongitude()
                    +"&hour="+hour+"&minute="+minute
                    +"&addressStart="+addressStart+"&addressEnd="+addressEnd
                    +"&addressMidFirst="+addressMiddleFirst+"&isOp="+ SearchRouteActivity.optimize
                    +"&walkingDistance="+ SearchRouteActivity.walkingDistance+"&transferTurn="+SearchRouteActivity.transferNumber;
            Log.d("URL THREE POINT : ",url);
            String json = NetworkUtils.download(url);
            return json ;
        }else{
            startPoint =  busLocations.get(0);
            endPoint =  busLocations.get(1);
            middleFirstPoint = busLocations.get(2);
            middleSecondPoint = busLocations.get(3);
            try{
                addressStart = URLEncoder.encode(startPoint.getAddress(), "UTF-8");
                addressEnd = URLEncoder.encode(endPoint.getAddress(),"UTF-8");
                addressMiddleFirst = URLEncoder.encode(middleFirstPoint.getAddress(),"UTF-8");
                addressMiddleSecond = URLEncoder.encode(middleSecondPoint.getAddress(),"UTF-8");
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }

            String url = AppConstants.API.SEARCH_BUS_ROUTE_FOUR_POINT + "?latStart="+ startPoint.getLatitude()
                    +"&latEnd="+endPoint.getLatitude()+"&longStart="+startPoint.getLongitude()
                    +"&longEnd="+endPoint.getLongitude()
                    +"&latMidFirst="+middleFirstPoint.getLatitude()+"&longMidFirst="+middleFirstPoint.getLongitude()
                    +"&latMidSecond="+middleSecondPoint.getLatitude()+"&longMidSecond="+middleSecondPoint.getLongitude()
                    +"&hour="+hour+"&minute="+minute
                    +"&addressStart="+addressStart+"&addressEnd="+addressEnd
                    +"&addressMidFirst="+addressMiddleFirst+"&addressMidSecond="+addressMiddleSecond+"&isOp="+ SearchRouteActivity.optimize
                    +"&walkingDistance="+ SearchRouteActivity.walkingDistance+"&transferTurn="+SearchRouteActivity.transferNumber;
            Log.d("URL FOUR POINT : ",url);
            String json = NetworkUtils.download(url);
            return json ;
        }
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
