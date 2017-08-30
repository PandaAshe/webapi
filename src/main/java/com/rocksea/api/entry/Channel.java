package com.rocksea.api.entry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mrw on 2017/8/14.
 *
 */

public class Channel
{
    public String  BaseInfoId;
    public byte  SignalType;
    public float  SampleInterval;
    public int  SampleGain;
    public int  SampleLength;
    public float  SensorSensitive;
    public int  FilterFrequency;
    public String  SampleTime;
    public String  ChannelData;
    public int DataVersion;

    public Channel()
    {

    }
    public String channelJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("BaseInfoId", BaseInfoId);
            jsonObject.put("SignalType", SignalType);
            jsonObject.put("SampleInterval", SampleInterval);
            jsonObject.put("SampleGain", SampleGain);
            jsonObject.put("SampleLength", SampleLength);
            jsonObject.put("SensorSensitive", SensorSensitive);
            jsonObject.put("FilterFrequency", FilterFrequency);
            jsonObject.put("SampleTime", SampleTime);
            jsonObject.put("ChannelData", ChannelData);
            jsonObject.put("DataVersion", DataVersion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}

