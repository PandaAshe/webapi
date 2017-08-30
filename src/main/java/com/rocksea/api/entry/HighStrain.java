package com.rocksea.api.entry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mrw on 2017/8/14.
 *
 */

public class HighStrain
{
    public String BaseInfoId;
    public String MachineId;
    public String SerialNo;
    public String PileNo;
    public String TestTime;
    public float  PileLength;
    public String PileDiameter;
    public float  PileVelocity;
    public String ConcreteStrength;
    public byte   GpsIsValid;
    public double GpsLongitude;
    public double GpsLatitude;
    public String ShangGangZhen;
    public float LengthUnderSensor;
    public float HammerWeight;
    public float HammerDropHeight;
    public float SectionArea;
    public float BottomArea;
    public float Density;
    public float SectionCircum;
    public float Jc;
    public float DepthIn;
    public float Vs;

    public String HighStarinJson()
    {
        JSONObject object = new JSONObject();
        try {
            object.put("BaseInfoId",BaseInfoId);
            object.put("MachineId",MachineId);
            object.put("SerialNo",SerialNo);
            object.put("PileNo",PileNo);
            object.put("TestTime",TestTime);
            object.put("PileLength",PileLength);
            object.put("PileDiameter",PileDiameter);
            object.put("PileVelocity",PileVelocity);
            object.put("ConcreteStrength",ConcreteStrength);
            object.put("GpsIsValid",GpsIsValid);
            object.put("GpsLongitude",GpsLongitude);
            object.put("GpsLatitude",GpsLatitude);
            object.put("ShangGangZhen",ShangGangZhen);
            object.put("LengthUnderSensor",LengthUnderSensor);
            object.put("HammerWeight",HammerWeight);
            object.put("HammerDropHeight",HammerDropHeight);
            object.put("SectionArea",SectionArea);
            object.put("BottomArea",BottomArea);
            object.put("Density",Density);
            object.put("SectionCircum",SectionCircum);
            object.put("Jc",Jc);
            object.put("DepthIn",DepthIn);
            object.put("Vs",Vs);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return object.toString();
    }

}
