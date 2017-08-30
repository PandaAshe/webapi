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
import com.rocksea.api.http.RequestCallBack;

/**
 * Created by 吴志华 on 2017/8/14.
 *
 */

public interface Api
{

    void getTickey(Ticket ticket,RequestCallBack mRequestCallBack);

    void postJyDetailsData(StaticLoadSample staticLoadSample,RequestCallBack mRequestCallBack);

    void correntJyDetailsData(StaticLoadSample staticLoadSample,RequestCallBack mRequestCallBack);

    void postJzLog(RequestCallBack mRequestCallBack);

    void postJzBasic(StaticLoad staticLoad,RequestCallBack mRequestCallBack);

    void upDataGPS(GPS gps,String baseInfoId,RequestCallBack mRequestCallBack);

    void overJzTest(String baseInfoId,RequestCallBack mRequestCallBack);

    void postScBasic(SonicWave sonicWave,RequestCallBack mRequestCallBack);

    void postScSection(Section section,RequestCallBack mRequestCallBack);

    void postDyBasic(LowStrain lowStrain,RequestCallBack mRequestCallBack);

    void postDyChannel(Channel channel,RequestCallBack mRequestCallBack);

    void postGyBasic(HighStrain highStrain,RequestCallBack mRequestCallBack);

    void postGyChannel(Channel channel,RequestCallBack mRequestCallBack);

    void post(String url,String json,RequestCallBack mRequestCallBack);

    boolean post(Channel channel);

    boolean post(int MethodCode,String json);
}
