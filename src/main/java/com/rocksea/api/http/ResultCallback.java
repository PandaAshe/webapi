package com.rocksea.api.http;


import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 吴志华 on 2017/7/5.
 *
 */

public abstract class ResultCallback
{
    public ResultCallback() {}
    public abstract void onRequestBefore();
    public abstract void onFailure(Request request, Exception e);
    public abstract void onSuccess(Response response, String t);
    public abstract void onError(Response response, int errorCode, Exception e);
}
