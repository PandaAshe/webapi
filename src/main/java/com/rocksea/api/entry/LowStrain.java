package com.rocksea.api.entry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Mrw on 2017/8/14.
 *
 */

public class LowStrain {
    public String  BaseInfoId;
    public String  MachineId;
    public String  SerialNo;
    public String  PileNo;
    public String  TestTime;
    public float  PileLength;
    public String  PileDiameter;
    public float  PileVelocity;
    public String  ConcreteStrength;
    public byte  GpsIsValid;
    public double  GpsLongitude;
    public double  GpsLatitude;
    public String  ShangGangZhen;

    public LowStrain()
    {

    }
    public String lowStrainJson()
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

}
