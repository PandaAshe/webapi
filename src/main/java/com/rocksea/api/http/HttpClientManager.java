package com.rocksea.api.http;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.rocksea.api.RS;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 吴志华 on 2017/7/5.
 * 所有网络请求类
 */

public class HttpClientManager {

    private static HttpClientManager mInstance;
    private final OkHttpClient okHttpClient;
    private Handler mHandler;
    public static final int LowStrain = 0x0001;
    public static final int Channel = 0x0002;
    public static final int StaticLoad = 0x0003;
    public static final int StaticLoad_TEST = 0x0004;
    public static final int StaticLoad_LOG = 0x0005;
    public static final int StaticLoad_Sample = 0x0006;
    private enum HttpMethodType
    {
        GET,
        POST,
        PUT,
        PATCH
    }

    public static HttpClientManager getInstance()
    {
        if (mInstance==null)
        {
            synchronized (HttpClientManager.class)
            {
                mInstance = new HttpClientManager();
            }
        }
        return mInstance;
    }

    private HttpClientManager()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS);
        okHttpClient = builder.build();
        mHandler = new Handler(Looper.getMainLooper());
    }

    private void request(final Request request, final ResultCallback callback)
    {
        //在请求之前所做的事，比如弹出对话框等
        callback.onRequestBefore();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callbackFailure(call.request(), callback, e);
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ResponseBody mBody = response.body();
                if (response.isSuccessful()) {
                    //返回成功回调
                    if (mBody!=null){
                        String resString = mBody.string();
                        callbackSuccess(response,resString,callback);
                    }else {
                        callbackError(response, callback, new Exception("未知错误"));
                    }
                } else {
                    //返回错误
                    callbackError(response, callback, new Exception(response.message()));
                }
            }
        });
    }


    private void callbackSuccess(final Response response, final String o, final ResultCallback callback)
    {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, o);
            }
        });
    }
    private void callbackError(final Response response, final ResultCallback callback,final Exception e)
    {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), e);
            }
        });
    }
    private void callbackFailure(final Request request, final ResultCallback callback, final IOException e)
    {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request, e);
            }
        });
    }

    public void get(String url, ResultCallback callback)
    {
        Request request = buildRequest(url, null, HttpMethodType.GET);
        request(request, callback);
    }
    public void post(String url, String json, ResultCallback callback)
    {
        Request request = buildRequest(url, json, HttpMethodType.POST);
        request(request, callback);
    }
    public void put(String url,String params,ResultCallback callback)
    {
        Request request = buildRequest(url,params, HttpMethodType.PUT);
        request(request,callback);
    }
    public void patch(String url,String params,ResultCallback callback)
    {
        Request request = buildRequest(url,params, HttpMethodType.PATCH);
        request(request,callback);
    }
    private Request buildRequest(String url, String json, HttpMethodType type)
    {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (RS.Taken!=null)
            builder.addHeader("Authorization",String.format("Basic %s",RS.Taken));
        if (type == HttpMethodType.GET)
        {
            builder.get();
        } else if (type == HttpMethodType.POST)
        {
            builder.addHeader("Content-Type","application/json; charset=UTF-8");
            builder.post(buildRequestBody(json));
        }else if (type == HttpMethodType.PATCH)
        {
            builder.addHeader("Content-Type","application/json; charset=UTF-8");
            builder.patch(buildRequestBody(json));
        }else if (type == HttpMethodType.PUT)
        {
            builder.addHeader("Content-Type","application/json; charset=UTF-8");
            builder.put(buildRequestBody(json));
        }
        return builder.build();
    }

    public boolean post(int methodCode,String json)
    {
        String url;
        switch (methodCode){
            case LowStrain:
                url = RS.DY_BASE;
                break;
            case Channel:
                url = RS.CHANNEL_DATA;
                break;
            case StaticLoad:
                url = RS.JZ_BASE;
                break;
            case StaticLoad_LOG:
                url = RS.JZ_LOG;
                break;
            case StaticLoad_TEST:
                url = RS.JZ_DETAILS;
                break;
            case StaticLoad_Sample:
                url = RS.JZ_DETAILS;
                break;
            default:
                url = "";
                break;
        }
        return post(url,json);
    }
    public boolean post(String url,String json)
    {
        Log.i("post: ",json);
        try {
            URL mURL = new URL(url);
            HttpURLConnection mHttpURLConnection = (HttpURLConnection) mURL.openConnection();
            mHttpURLConnection.setConnectTimeout(10000);
            mHttpURLConnection.setRequestMethod("POST");
            mHttpURLConnection.setDoOutput(true);
            mHttpURLConnection.setDoInput(true);
            mHttpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            mHttpURLConnection.setRequestProperty("Authorization",String.format("Basic %s",RS.Taken));
            OutputStream os = mHttpURLConnection.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            int responseCode = mHttpURLConnection.getResponseCode();
            if (responseCode==200)
            {
                String message = changeInputStream(mHttpURLConnection.getInputStream());
                JSONObject object = new JSONObject(message);
                Log.d("post: ",message);
                return object.getInt("code") == 0||object.getInt("code")==1;
            }
            else
            {
                Log.d("post: ",responseCode+"");
                String message = changeInputStream(mHttpURLConnection.getInputStream());
                JSONObject object = new JSONObject(message);
                return object.getInt("code") == 0||object.getInt("code")==1;
            }
        }
        catch (Exception e)
        {

            Log.e("post: ",e.toString());
            return false;
        }
    }

    /**
     * 通过Map的键值对构建请求对象的body
     * @param params<StringObject> params
     * @return RequestBody
     */
    private RequestBody buildRequestBody(String params)
    {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Log.d("buildRequestBody: ",params);
        return RequestBody.create(JSON,params);
    }
    private  String changeInputStream(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] data = new byte[1024];
        int len = inputStream.read(data);
        while (len != -1)
        {
            outputStream.write(data, 0, len);
            len = inputStream.read(data);
        }
        return new String(outputStream.toByteArray());
    }
}
