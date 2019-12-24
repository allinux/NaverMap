package com.jinasoft.navermap;


import android.app.Application;

import com.naver.maps.map.NaverMapSdk;

public class MainActivity extends Application {

    public class YourApplication extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            NaverMapSdk.getInstance(this).setClient(
                    new NaverMapSdk.NaverCloudPlatformClient("npvl4yeea7"));
        }
    }
}

