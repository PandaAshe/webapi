package com.rocksea.api.entry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mrw on 2017/8/14.
 *
 */

public class GPS
{
    public byte GpsIsValid;
    public double GpsLongitude;
    public double GpsLatitude;
    public String GPSJson(){
        JSONObject object = new JSONObject();
        try {
            object.put("GpsIsValid",GpsIsValid);
            object.put("GpsLongitude",GpsLongitude);
            object.put("GpsLatitude",GpsLatitude);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return object.toString();
    }


}
