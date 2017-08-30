package com.rocksea.api.http;

/**
 * Created by 吴志华 on 2017/8/14.
 *
 */

public interface RequestCallBack
{
    void onSuccess(String message);
    void onFail(int code,String message);
}
