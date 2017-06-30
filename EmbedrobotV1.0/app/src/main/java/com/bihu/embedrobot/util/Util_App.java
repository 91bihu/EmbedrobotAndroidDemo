package com.bihu.embedrobot.util;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by houxianjun on 2017/6/27.
 */

public class Util_App {


    public static String getDeviceId(Context context){
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice="", tmSerial, tmPhone, androidId;
        try {

            tmDevice = "" + tm.getDeviceId();
            System.out.print("设备码:" + tmDevice);
        }catch (Exception ex){

        }
        return tmDevice;
    }
}
