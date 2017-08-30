package com.rocksea.api;

import com.rocksea.api.entry.Channel;
import com.rocksea.api.entry.GPS;
import com.rocksea.api.entry.HighStrain;
import com.rocksea.api.entry.LowStrain;
import com.rocksea.api.entry.Section;
import com.rocksea.api.entry.SonicWave;
import com.rocksea.api.entry.StaticLoad;
import com.rocksea.api.entry.StaticLoadSample;
import com.rocksea.api.entry.Ticket;
import com.rocksea.api.http.HttpClientManager;
import com.rocksea.api.http.RequestCallBack;
import com.rocksea.api.http.ResultCallback;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 吴志华 on 2017/8/14.
 *
 */
public class WebApi implements Api {

    private final HttpClientManager httpClientManager;
    private RequestCallBack mRequestCallBack;
    public WebApi()
    {
        httpClientManager = HttpClientManager.getInstance();
    }
    @Override
    public void getTickey(Ticket ticket,RequestCallBack mRequestCallBack)
    {
        this.mRequestCallBack = mRequestCallBack;
        httpClientManager.post(RS.TICKET,ticket.getTicketJson(),mCallback);
    }
    @Override
    public void postJyDetailsData(StaticLoadSample staticLoadSample,RequestCallBack mRequestCallBack)
    {
        this.mRequestCallBack = mRequestCallBack;
        httpClientManager.post(RS.JZ_DETAILS,staticLoadSample.sample(),mCallback);
    }
    @Override
    public void correntJyDetailsData(StaticLoadSample staticLoadSample,RequestCallBack mRequestCallBack)
    {
        this.mRequestCallBack = mRequestCallBack;
        httpClientManager.put(RS.JZ_PUT_DETAILS,staticLoadSample.sample(),mCallback);
    }
    @Override
    public void postJzLog(RequestCallBack mRequestCallBack)
    {
        this.mRequestCallBack = mRequestCallBack;

    }
    @Override
    public void postJzBasic(StaticLoad staticLoad,RequestCallBack mRequestCallBack)
    {
        this.mRequestCallBack = mRequestCallBack;
        httpClientManager.post(RS.JZ_BASE,staticLoad.staticLoadJson(),mCallback);
    }
    @Override
    public void upDataGPS(GPS gps,String baseInfoId,RequestCallBack mRequestCallBack)
    {
        this.mRequestCallBack = mRequestCallBack;
        httpClientManager.put(String.format(RS.JZ_UPDATA_GPS,baseInfoId),gps.GPSJson(),mCallback);
    }
    @Override
    public void overJzTest(String baseInfoId,RequestCallBack mRequestCallBack)
    {
        this.mRequestCallBack = mRequestCallBack;
        httpClientManager.patch(String.format(RS.JZ_OVERTEST,baseInfoId),null,mCallback);
    }
    @Override
    public void postScBasic(SonicWave sonicWave,RequestCallBack mRequestCallBack)
    {
        this.mRequestCallBack = mRequestCallBack;
        httpClientManager.post(RS.SC_BASE,sonicWave.sonicWaveJson(),mCallback);
    }
    @Override
    public void postScSection(Section section,RequestCallBack mRequestCallBack)
    {
        this.mRequestCallBack = mRequestCallBack;
        httpClientManager.post(RS.SC_SECTION,section.sectinJson(),mCallback);
    }
    @Override
    public void postDyBasic(LowStrain lowStrain,RequestCallBack mRequestCallBack)
    {
        this.mRequestCallBack = mRequestCallBack;
        httpClientManager.post(RS.DY_BASE,lowStrain.lowStrainJson(),mCallback);
    }
    @Override
    public void postDyChannel(Channel channel,RequestCallBack mRequestCallBack)
    {
        this.mRequestCallBack = mRequestCallBack;
        httpClientManager.post(RS.CHANNEL_DATA,channel.channelJson(),mCallback);
    }
    @Override
    public void postGyBasic(HighStrain strain,RequestCallBack mRequestCallBack)
    {
        this.mRequestCallBack = mRequestCallBack;
        httpClientManager.post(RS.GY_BASE,strain.HighStarinJson(),mCallback);
    }
    @Override
    public void postGyChannel(Channel channel, RequestCallBack mRequestCallBack)
    {
        this.mRequestCallBack = mRequestCallBack;
        httpClientManager.post(RS.CHANNEL_DATA,channel.channelJson(),mCallback);
    }

    @Override
    public void post(String url, String json, RequestCallBack mRequestCallBack)
    {
        this.mRequestCallBack = mRequestCallBack;
        httpClientManager.post(url,json,mCallback);
    }

    @Override
    public boolean post(Channel channel)
    {
        return httpClientManager.post(RS.CHANNEL_DATA,channel.channelJson());
    }

    @Override
    public boolean post(int MethodCode, String json)
    {
        return httpClientManager.post(MethodCode,json);
    }


    private ResultCallback mCallback = new ResultCallback()
    {
        @Override
        public void onRequestBefore()
        {
        }
        @Override
        public void onFailure(Request request, Exception e)
        {
        }
        @Override
        public void onSuccess(Response response, String s)
        {
            try
            {
                JSONObject json = new JSONObject(s);
                int code = json.getInt("code");
                if (code==0)
                    mRequestCallBack.onSuccess(s);
                else
                    mRequestCallBack.onFail(code,s);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                mRequestCallBack.onFail(-1008,e.toString());
            }
        }
        @Override
        public void onError(Response response, int errorCode, Exception e)
        {
            mRequestCallBack.onFail(errorCode,e.toString());
        }
    };

}
