package com.axb.hotfix;

import android.app.Application;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import com.axb.hotfix.lib.HotFix;

import java.io.File;

public class HotFixApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        HotFix.installPatch(this,new File(Environment.getExternalStorageDirectory().getPath()+File.separator+"patch.dex"));
    }
}
