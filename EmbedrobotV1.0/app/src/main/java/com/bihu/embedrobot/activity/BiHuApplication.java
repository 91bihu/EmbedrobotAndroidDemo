package com.bihu.embedrobot.activity;

import android.app.Application;

/**
 * Created by houxianjun on 16/7/19.
 */
public class BiHuApplication extends Application {


    public static BiHuApplication self;

    @Override
    public void onCreate() {
        super.onCreate();


        setApplication(this);


    }

    public static void setApplication(BiHuApplication application) {
        self = application;



    }

    public static BiHuApplication getApplication(){
        return self;
    }
    public static BiHuApplication getBaseApplication() {
        return self;
    }



}
