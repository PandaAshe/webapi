package com.rocksea.api.entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mrw on 2017/8/14.
 *
 */

public class StaticLoadSample
{
      public String BaseInfoId;
      public String SampleTime;
      public byte LoadDirect;
      public byte Grade;
      public int SampleCount;
      public int TimeCount;
      public double Loading;
      public double RealLoading;
      public double RealPress;
      public double[] zSensor = new double[12];
      public double[] mSensor = new double[12];
      public double SAverage;


    public String sample(){
        JSONObject object = new JSONObject();
        try {
            object.put("BaseInfoId",BaseInfoId);
            object.put("SampleTime",SampleTime);
            object.put("LoadDirect",LoadDirect);
            object.put("Grade",Grade);
            object.put("SampleCount",SampleCount);
            object.put("TimeCount",TimeCount);
            object.put("Loading",Loading);
            object.put("RealLoading",RealLoading);
            object.put("RealPress",RealPress);


            JSONArray zSensors = new JSONArray();
            for (double aZSensor : zSensor) {
                if (aZSensor == Float.MAX_VALUE)
                    zSensors.put(null);
                else
                    zSensors.put(aZSensor);
            }
            object.put("zSensor",zSensors);
            JSONArray mSensors = new JSONArray();
            for (double aMSensor : mSensor) {
                if (aMSensor == Float.MAX_VALUE)
                    mSensors.put(null);
                else
                    mSensors.put(aMSensor);
            }
            object.put("mSensor",mSensors);
            object.put("SAverage",SAverage);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return object.toString();
    }
}
