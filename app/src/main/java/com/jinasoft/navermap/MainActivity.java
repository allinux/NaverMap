package com.jinasoft.navermap;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.map.NaverMapSdk;

public class MainActivity extends AppCompatActivity {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            NaverMapSdk.getInstance(this).setClient(
                    new NaverMapSdk.NaverCloudPlatformClient("d4onuthfhd"));


        }

    }


