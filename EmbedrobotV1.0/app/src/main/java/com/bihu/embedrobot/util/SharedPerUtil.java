package com.bihu.embedrobot.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.bihu.embedrobot.activity.BiHuApplication;

/**
 * Created by houxianjun on 2017/6/27.
 */

public class SharedPerUtil {
    private static SharedPerUtil instance;
    // 保存数据对象
    private static SharedPreferences sharedPreferences;

    private static final String USER_TOKEN ="Token";

    private static final String USER_ETIME="ExpireTime";

    private static final String USER_INFO = "user_stu_info";



    public SharedPerUtil(Context context) {

        sharedPreferences = context.getSharedPreferences(USER_INFO,
                Context.MODE_PRIVATE);
    }

    // 实例化对象
    public static SharedPerUtil createInstanse(Context context) {

        if (instance == null) {
            instance = new SharedPerUtil(context.getApplicationContext());
        }
        return instance;
    }

    public synchronized static SharedPerUtil getInstanse(Context context) {

        if (instance == null) {
            instance = new SharedPerUtil(BiHuApplication.getBaseApplication()
                    .getApplicationContext());
        }

        return instance;
    }


    /**
     * 用户是否登录 token是否为空
     */
    public String getUserLoginState() {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        String token = sharedPreferences.getString(USER_TOKEN, "");

        editor.commit();

        return token;

    }

    /**
     * 用户登录状态 token
     */
    public void setUserLoginState(String token) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USER_TOKEN, token);

        editor.commit();
    }



    /**
     *获取 过期时间
     */
    public String getUserLoginETIME() {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        String token = sharedPreferences.getString(USER_ETIME, "");

        editor.commit();

        return token;

    }

    /**
     * 用户登录状态 过期时间
     */
    public void setUserLoginETIME(String time) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USER_ETIME, time);

        editor.commit();
    }

}
