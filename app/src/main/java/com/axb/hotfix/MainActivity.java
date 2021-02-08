package com.axb.hotfix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String version = Build.VERSION.SDK_INT + "";
        Log.e("123","当前操作系统的版本号为：" + Build.VERSION.SDK_INT);

        Utils.aaa();
    }
}