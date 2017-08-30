package com.rocksea.api;

import com.rocksea.api.entry.Ticket;

/**
 * Created by 吴志华 on 2017/8/14.
 *
 */

public class RS
{
    private static final String HOST_NAME ="http://whrocksea.vicp.cc:8886";



    public static final String TICKET = HOST_NAME+"/api/Ticket";

    public static final String CHANNEL_DATA = HOST_NAME+"/api/DcChannelData";
    public static final String GY_BASE = HOST_NAME+"/api/GyData";
    public static final String DY_BASE = HOST_NAME+"/api/DyData";
    public static final String SC_BASE = HOST_NAME+"/api/ScData";
    public static final String SC_SECTION = HOST_NAME+"/api/ScSectionData";

    public static final String JZ_BASE = HOST_NAME+"/api/JyData";
    public static final String JZ_UPDATA_GPS = HOST_NAME+"/api/JyData/%s";
    public static final String JZ_OVERTEST = HOST_NAME+"/api/JyData/%s";
    public static final String JZ_DETAILS = HOST_NAME+"/api/JyDetailsData";
    public static final String JZ_PUT_DETAILS = HOST_NAME+"/api/JyDetailsData";
    public static final String JZ_LOG = HOST_NAME+"/api/JyLogsData";

    public static final String APPID = "rocksea";
    public static final String APP_SECRET = "87339549";

    public static String Taken;



}
