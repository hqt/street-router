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
        BusLocation A = null;
        BusLocation B = null;
        BusLocation C = null;
        BusLocation D = null;
        String addressA = null;
        String addressB = null;
        String addressC = null;
        String addressD = null;
        Calendar now = Calendar.getInstance();
        if(busLocations.size() == 2){
           A =  busLocations.get(0);
           B =  busLocations.get(1);

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
        }else if(busLocations.size() == 3){
            A =  busLocations.get(0);
            B =  busLocations.get(1);
            C = busLocations.get(2);
            try{
                addressA = URLEncoder.encode(A.getAddress(), "UTF-8");
                addressB = URLEncoder.encode(B.getAddress(),"UTF-8");
                addressC = URLEncoder.encode(C.getAddress(),"UTF-8");
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }

            String url = AppConstants.API.SEARCH_BUS_ROUTE + "?latA="+ A.getLatitude()
                    +"&latB="+B.getLatitude()+"&longA="+A.getLongitude()
                    +"&longB="+B.getLongitude()+"&hour="+now.get(Calendar.HOUR_OF_DAY)+"&minute="+now.get(Calendar.MINUTE)
                    +"&addressA="+addressA+"&addressB="+addressB;
            String json = NetworkUtils.download(url);
            return json ;
        }else{
            A =  busLocations.get(0);
            B =  busLocations.get(1);
            C = busLocations.get(2);
            D = busLocations.get(3);
            try{
                addressA = URLEncoder.encode(A.getAddress(), "UTF-8");
                addressB = URLEncoder.encode(B.getAddress(),"UTF-8");
                addressC = URLEncoder.encode(C.getAddress(),"UTF-8");
                addressD = URLEncoder.encode(D.getAddress(),"UTF-8");
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }

            String url = AppConstants.API.SEARCH_BUS_ROUTE_FOUR_POINT + "?latA="+ A.getLatitude()
                    +"&latB="+B.getLatitude()+"&longA="+A.getLongitude()
                    +"&longB="+B.getLongitude()
                    +"&latC="+C.getLatitude()+"&longC="+C.getLongitude()
                    +"&latD="+D.getLatitude()+"&longD="+D.getLongitude()
                    +"&hour="+now.get(Calendar.HOUR_OF_DAY)+"&minute="+now.get(Calendar.MINUTE)
                    +"&addressA="+addressA+"&addressB="+addressB
                    +"&addressC="+addressC+"&addressD="+addressD+"&isOp="+ SearchRouteActivity.optimize;
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
