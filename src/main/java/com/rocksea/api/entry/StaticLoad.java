package com.rocksea.api.entry;

import android.util.Xml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Mrw on 2017/8/14.
 *
 */

public class StaticLoad
{

    public String   BaseInfoId;
    public byte     TestMode;
    public byte     MaxGrade;
    public byte     FirstGrade;
    public byte     StableSwitch;
    public int      GradeTime;
    public byte     StableCount;
    public double   StableSensor;
    public int      StableTime;
    public int      MinGradeTime;
    public int      MaxGradeTime;
    public byte[]   LoadInterval = new byte[10];
    public byte[]   UnloadInterval = new byte[10];
    public int      UnloadGrade;
    public int      UnloadGradeTime;
    public int      UnloadZeroTime;
    public byte     AddLoadMode;
    public byte     ReloadSwitch;
    public byte     DropLoadSetting;
    public int      AllowDropLoad;
    public double   MaxSensor;
    public byte     PressCalcMethod;
    public byte     JackNumber;
    public int      JackDiameter;
    public String   JackNo;
    public byte[]   SensorUsed = new byte[12];
    public int[]    SensorNo = new int[12];
    public byte[]   SensorDirection = new byte[12];
    public byte     PressType;
    public int      PressRange;
    public String   PressNo;
    public String   PileNo;
    public int      TestType;
    public byte     IsGroundTest;
    public double   MaxLoad;
    public double   BoardArea = 1f;
    public String   StartTime;
    public byte     IsTesting;
    public String   ShangGangZheng;
    public String   MachineId;
    public String   SerialNo;
    public byte     GpsIsValid;
    public double   GpsLongitude = 0.0f;
    public double   GpsLatitude = 0.0f;

    public StaticLoad(String MachineId,String baseInfoId,byte[] sourceData)
    {
        this.MachineId = MachineId;
        this.BaseInfoId = baseInfoId;
        try {
            String xml = new String(sourceData,"GB2312");
            pullParserXml(xml);

            if (GpsLatitude!=0.0&&GpsLongitude!=0.0)
                GpsIsValid = 1;

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    private void pullParserXml(String xml)
    {
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(new StringReader(xml));
            int event = parser.getEventType();
            boolean isPressId = false;
            while (event!=XmlPullParser.END_DOCUMENT){
                event = parser.getEventType();
                switch (event){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if (tag.contains("检测流水号")){
                            SerialNo = parser.nextText();
                        } else if (tag.contains("桩号")){
                            PileNo = parser.nextText();
                        } else if (tag.contains("检测人员上岗证号")){
                            ShangGangZheng = parser.nextText();
                        } else if (tag.contains("试验方法")){
                            String type = parser.nextText();
                            if (type.contains("水平"))
                                TestMode = 1;
                            else
                                TestMode = 0;
                            if (type.contains("地基"))
                                IsGroundTest = 1;
                            else
                                IsGroundTest = 0;
                            TestType = checkTestType(type);
                        }else if (tag.contains("最大预估荷载")){
                            String maxLoad = parser.nextText();
                            MaxLoad = Double.parseDouble(maxLoad.split(" ")[0]);
                        }else if (tag.contains("开始试验时间")){
                            StartTime=parser.nextText();
                        }else if (tag.contains("定位信息")){
                            String info = parser.nextText();
                            if (!info.contains("无效定位")){
                                String[] gpss = info.split(",");
                                GpsLongitude= Double.parseDouble(gpss[0]);
                                GpsLatitude = Double.parseDouble(gpss[1]);
                            }
                        }else if (tag.contains("预分级数")){
                            MaxGrade = (byte) Integer.parseInt(parser.nextText());
                        } else if (tag.contains("加载采样时间间隔")){
                            String loadIntervaStr = parser.nextText();
                            String[] loadIntervas = loadIntervaStr.split(",");
                            if (loadIntervas.length!=0)
                            {
                                for (int i = 0; i < LoadInterval.length; i++)
                                {
                                    if (i<loadIntervas.length-1)
                                        LoadInterval[i] = (byte) Integer.parseInt(loadIntervas[i]);
                                }
                            }
                        }else if (tag.contains("卸载采样时间间隔")){
                            String unLoadIntervaStr = parser.nextText();
                            String[] unLoadIntervas = unLoadIntervaStr.split(",");
                            if (unLoadIntervas.length!=0) {
                                for (int i = 0; i < UnloadInterval.length; i++)
                                {
                                    if (i<unLoadIntervas.length-1)
                                        UnloadInterval[i] = (byte) Integer.parseInt(unLoadIntervas[i]);
                                }
                            }
                        }else if (tag.contains("传感器类型")){
                            String type = parser.nextText();
                            if (type.contains("压力")){
                                PressType = 1;
                            }else {
                                PressType = 2;
                            }
                        }else if (tag.contains("量程")){
                            String fsensorMaxStr = parser.nextText();
                            PressRange = (int) Double.parseDouble(fsensorMaxStr.split(" ")[0]);
                        }else if (tag.contains("传感器用途")){
                            String sensorUseds = parser.nextText();
                            String[] SensorUseds = sensorUseds.split(",");
                            for (int i = 0; i < SensorUseds.length; i++)
                            {
                                if (i<SensorUseds.length)
                                {
                                    switch (SensorUseds[i]) {
                                        case "试桩":
                                        case "基桩":
                                        case "下桩":
                                        case "下表":
                                            SensorUsed[i] = 1;
                                            break;
                                        case "锚桩":
                                        case "上桩":
                                        case "上表":
                                            SensorUsed[i] = 2;
                                            break;
                                        case "桩顶":
                                            SensorUsed[i] = 3;
                                        case "未用":
                                            SensorUsed[i] = 0;
                                            break;
                                    }
                                }else {
                                    SensorUsed[i] = 0;
                                }
                            }
                        }else if (tag.contains("传感器编号"))
                        {
                            String sensorIds = parser.nextText();
                            String[] SensorIds = sensorIds.split(",");
                            for (int i = 0; i < SensorIds.length; i++)
                            {
                                if (i<SensorIds.length){
                                    if (SensorIds[i].contains("未用"))
                                        SensorNo[i] = 0;
                                    else
                                        SensorNo[i] = Integer.parseInt(SensorIds[i]);
                                }
                                else
                                    SensorNo[i] = 0;
                            }
                        }else if (tag.contains("压力参数"))
                        {
                            isPressId = true;
                        }
                        else if (tag.contains("编号")){
                            if (isPressId)
                            {
                                PressNo = parser.nextText();
                                JackNo = PressNo;
                            }
                        }
                        else if (tag.contains("传感器方向"))
                        {
                            String sensordirc = parser.nextText();
                            String[] sensordircs = sensordirc.split(",");
                            for (int i = 0; i < SensorDirection.length; i++) {
                                if (i<sensordircs.length)
                                {
                                    switch (sensordircs[i])
                                    {
                                        case "伸长":
                                            SensorDirection[i] = 1;
                                            break;
                                        default:
                                            SensorDirection[i] = 0;
                                            break;
                                    }
                                }else
                                    SensorDirection[i] = 0;
                            }

                        }
                        else if (tag.contains("千斤顶内径"))
                        {
                            String diameter = parser.nextText();
                            JackDiameter = Integer.parseInt(diameter.split(" ")[0]);
                        }else if (tag.contains("千斤顶数量")){
                            JackNumber = (byte) Integer.parseInt(parser.nextText());
                        }else if (tag.contains("压力计算方法"))
                        {
                            String type = parser.nextText();
                            PressCalcMethod =(byte) (type.contains("计算")?0:1);
                        }else if (tag.contains("加载方式")){
                            String addtype = parser.nextText();
                            switch (addtype){
                                case "全自动":
                                    AddLoadMode = 0;
                                    break;
                                case "半自动 ":
                                    AddLoadMode = 1;
                                    break;
                                case "人工":
                                    AddLoadMode = 2;
                                    break;
                            }
                        }else if (tag.contains("补载开关")){
                            String addswitch  = parser.nextText();
                            switch (addswitch){
                                case "允许":
                                    ReloadSwitch = 1;
                                    break;
                                default:
                                    ReloadSwitch = 0;
                                    break;
                            }
                        }else if (tag.contains("掉载量设置"))
                        {
                            String dropSetting = parser.nextText();
                            switch (dropSetting){
                                case "固定值":
                                    DropLoadSetting = 1;
                                    break;
                                default:
                                    DropLoadSetting = 0;
                                    break;
                            }
                        }else if (tag.contains("判稳标准"))
                        {
                            String mStableSwitch = parser.nextText();
                            switch (mStableSwitch){
                                case "不判稳":
                                    StableSwitch = 0;
                                    break;
                                case "判稳":
                                    StableSwitch = 1;
                                    break;
                                case "收敛":
                                    StableSwitch = 2;
                                    break;
                                default:
                                    break;
                            }

                        }else if (tag.contains("每级测试时间"))
                        {
                            GradeTime = Integer.parseInt(parser.nextText());
                        }else if (tag.contains("首次加载级数")){
                            FirstGrade =(byte) Integer.parseInt(parser.nextText());
                        }else if (tag.contains("判稳时间")){
                            StableTime = Integer.parseInt(parser.nextText().split(" ")[0]);
                            GradeTime = StableTime;
                        }else if (tag.contains("稳定次数")){
                            StableCount = (byte) Integer.parseInt(parser.nextText());
                        }
                        else if (tag.contains("稳定值"))
                        {
                            StableSensor  = Double.parseDouble(parser.nextText().split(" ")[0]);
                        }else if (tag.contains("最小稳定时间"))
                        {
                            MinGradeTime = Integer.parseInt(parser.nextText().split(" ")[0]);
                        }else if (tag.contains("最大稳定时间")){
                            MaxGradeTime = Integer.parseInt(parser.nextText().split(" ")[0]);
                        }else if (tag.contains("每次卸载")){
                            UnloadGrade = Integer.parseInt(parser.nextText().split(" ")[0]);
                        }else if (tag.contains("回零测试时间")){
                            UnloadZeroTime = Integer.parseInt(parser.nextText().split(" ")[0]);
                        }else if (tag.contains("非零测试时间")){
                            UnloadGradeTime = Integer.parseInt(parser.nextText().split(" ")[0]);
                        }else if (tag.contains("允许掉载量"))
                        {
                            AllowDropLoad = Integer.parseInt(parser.nextText().split(" ")[0]);
                        }else if (tag.contains("允许位移量")){
                            MaxSensor = Double.parseDouble(parser.nextText().split(" ")[0]);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
        }

    }

    private int checkTestType(String type)
    {
        if (type.contains("慢速"))
            return 1;
        else if (type.contains("快速"))
            return 2;
        else if (type.contains("地基"))
            return 3;
        else if (type.contains("基岩"))
            return 4;
        else if (type.contains("平板"))
            return 5;
        else if (type.contains("锚杆"))
            return 6;
        else if (type.contains("抗拔"))
            return 7;
        else if (type.contains("自平衡"))
            return 8;
        else
            return 0;
    }


    public String staticLoadJson(){
       JSONObject object = new JSONObject();
       try {
           object.put("BaseInfoId",BaseInfoId);
           object.put("TestMode",TestMode);
           object.put("MaxGrade",MaxGrade);
           object.put("FirstGrade",FirstGrade);
           object.put("StableSwitch",StableSwitch);
           object.put("GradeTime",GradeTime);
           object.put("StableCount",StableCount);
           object.put("StableSensor",StableSensor);
           object.put("StableTime",StableTime);
           object.put("MinGradeTime",MinGradeTime);
           object.put("MaxGradeTime",MaxGradeTime);

           JSONArray loadInterval = new JSONArray(LoadInterval);
           object.put("LoadInterval",loadInterval);

           JSONArray unloadGradeTime = new JSONArray(UnloadInterval);
           object.put("UnloadInterval",unloadGradeTime);

           object.put("UnloadGrade",UnloadGrade);
           object.put("UnloadGradeTime",UnloadGradeTime);
           object.put("UnloadZeroTime",UnloadZeroTime);
           object.put("AddLoadMode",AddLoadMode);
           object.put("ReloadSwitch",ReloadSwitch);
           object.put("DropLoadSetting",DropLoadSetting);
           object.put("AllowDropLoad",AllowDropLoad);
           object.put("MaxSensor",MaxSensor);
           object.put("PressCalcMethod",PressCalcMethod);
           object.put("JackNumber",JackNumber);
           object.put("JackDiameter",JackDiameter);
           object.put("JackNo",JackNo);

           JSONArray sensorUsed = new JSONArray(SensorUsed);
           object.put("SensorUsed",sensorUsed);

           JSONArray sensorNo = new JSONArray(SensorNo);
           object.put("SensorNo",sensorNo);

           JSONArray sensorDirect = new JSONArray(SensorDirection);
           object.put("SensorDirection",sensorDirect);

           object.put("PressType",PressType);
           object.put("PressRange",PressRange);
           object.put("PressNo",PressNo);
           object.put("PileNo",PileNo);
           object.put("TestType",TestType);
           object.put("IsGroundTest",IsGroundTest);
           object.put("MaxLoad",MaxLoad);
           object.put("BoardArea",BoardArea);
           object.put("StartTime",StartTime);
           object.put("IsTesting",IsTesting);
           object.put("ShangGangZheng",ShangGangZheng);
           object.put("MachineId","JYD");
           object.put("SerialNo",SerialNo);
           object.put("GpsIsValid",GpsIsValid);
           object.put("GpsLongitude",GpsLongitude);
           object.put("GpsLatitude",GpsLatitude);
       }catch (JSONException e){
           e.printStackTrace();
       }
        return object.toString();
   }


}
