package com.rocksea.api.entry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mrw on 2017/8/14.
 *
 */

public class SonicWave
{
    public String BasicInfoId;
    public String MachineId;
    public String SerialNo;
    public String PileNo;
    public String TestTime;
    public double PileLength;
    public String PileDiameter;
    public String ConcreteStrength;
    public int      TubeCount;
    public int      Step;
    public int      SectionCount;
    public int      Angle;
    public int      GpsIsValid;
    public double   GpsLongitude;
    public double   GpsLatitude;
    public String   ShangGangZheng;

    public String sonicWaveJson(){
        JSONObject object = new JSONObject();
        try {
            object.put("BasicInfoId",BasicInfoId);
            object.put("MachineId",MachineId);
            object.put("SerialNo",SerialNo);
            object.put("PileNo",PileNo);
            object.put("TestTime",TestTime);
            object.put("PileLength",PileLength);
            object.put("PileDiameter",PileDiameter);
            object.put("ConcreteStrength",ConcreteStrength);
            object.put("TubeCount",TubeCount);
            object.put("Step",Step);
            object.put("SectionCount",SectionCount);
            object.put("Angle",Angle);
            object.put("GpsIsValid",GpsIsValid);
            object.put("GpsLongitude",GpsLongitude);
            object.put("GpsLatitude",GpsLatitude);
            object.put("ShangGangZheng",ShangGangZheng);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return object.toString();
    }
}
