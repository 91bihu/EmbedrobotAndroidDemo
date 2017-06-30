package com.bihu.embedrobot.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bihu.embedrobot.R;
import com.bihu.embedrobot.util.SharedPerUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by houxianjun on 2017/6/27.
 */

public class Activity_Loading extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.layout_loading);


        //检测是否登录过，没有登录跳转到登录页面
        if(SharedPerUtil.getInstanse(Activity_Loading.this).getUserLoginState().equals("")){

            Intent itent = new Intent(getApplicationContext(),
                    Activity_UserLogin.class);
            startActivity(itent);
            finish();

        }
        //曾经登录过应用
        else {

            //比对有效时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date date = new Date();

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(date);

            date = calendar.getTime();
            //获取当前时间戳
            long  current_expiretamp = date.getTime() / 1000;

            long expiretamp =Long.parseLong( SharedPerUtil.getInstanse(Activity_Loading.this).getUserLoginETIME());


            //token过期,重新登录
            if (current_expiretamp>expiretamp){

                Intent itent = new Intent(getApplicationContext(),
                        Activity_UserLogin.class);
                startActivity(itent);
                finish();

            }
            //未过期,进入续保页面
            else {
                Intent itent = new Intent(getApplicationContext(),
                        Activity_WebView.class);
                itent.putExtra("token",SharedPerUtil.getInstanse(Activity_Loading.this).getUserLoginState());
                startActivity(itent);
                finish();
            }

        }


    }




}
