package com.rocksea.api.entry;

import com.rocksea.api.RS;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 吴志华 on 2017/8/14.
 *
 */

public class Ticket
{
    private static String AppId;
    private static String AppSecret;
    private static String AccountId;
    private static String AccountSecret;
    private static long Timestamp;
    private static String Token;

    public Ticket(String AccountId,String AccountSecret)
    {
        AppId = RS.APPID;
        AppSecret = RS.APP_SECRET;
        Ticket.AccountId = AccountId;
        Ticket.AccountSecret = AccountSecret;
        Timestamp = (System.currentTimeMillis()/1000);
        Token = md5(String.format("%s%s%s",RS.APP_SECRET,AccountSecret,String.valueOf(Timestamp)));
    }
    public String getTicketJson()
    {
        JSONObject object = new JSONObject();
        try {
            object.put("AppId",AppId);
            object.put("AppSecret",AppSecret);
            object.put("AccountId",AccountId);
            object.put("AccountSecret",AccountSecret);
            object.put("Timestamp",Timestamp);
            object.put("Token",Token);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return object.toString();
    }


    public static String loginJosn(){

        JSONObject object = new JSONObject();
        try {
            object.put("appid","ROCKSEANDROID");
            object.put("username","jcmy");
            object.put("password","1234567");
            object.put("timestamp",(System.currentTimeMillis()/1000));
            object.put("token",md5("::"+"ROCKSEANDROID"+"::"+(System.currentTimeMillis()/1000)+"::").toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
    private static String md5(String string) {
        byte[] encodeBytes;
        try {
            encodeBytes = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException neverHappened) {
            throw new RuntimeException(neverHappened);
        }

        return toHexString(encodeBytes);
    }
    private static String toHexString(byte[] bytes) {
        if (bytes == null) return "";
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hex.append(hexDigits[(b >> 4) & 0x0F]);
            hex.append(hexDigits[b & 0x0F]);
        }
        return hex.toString();
    }
    private static final char hexDigits[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
}
