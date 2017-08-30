package com.rocksea.api.entry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mrw on 2017/8/14.
 *
 */

public class Section
{
    public String   BaseInfoId;
    public String   SectionName;
    public byte     TestMode;
    public byte     MoveDirect;
    public short    TubeDistance;
    public short    Step;
    public float    SampleInterval;
    public short    SampleLength;
    public short    NodesCount;
    public byte     DataVersion;
    public String   Nodes;
    public int      serialNo;
    public int      zerotime;
    public int      XcHeight;
    public String sectinJson(){
        JSONObject object = new JSONObject();
        try {
        object.put("BaseInfoId",BaseInfoId);
        object.put("SectionName",SectionName);
        object.put("TestMode",TestMode);
        object.put("MoveDirect",MoveDirect);
        object.put("TubeDistance",TubeDistance);
        object.put("Step",Step);
        object.put("SampleInterval",SampleInterval);
        object.put("SampleLength",SampleLength);
        object.put("NodesCount",NodesCount);
        object.put("DataVersion",DataVersion);
        object.put("Nodes",Nodes);
        object.put("serialNo",serialNo);
        object.put("zerotime",zerotime);
        object.put("XcHeight",XcHeight);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return object.toString();
    }






}
