package com.our.flosing;

import android.app.Application;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;

/**
 * Created by RunNishino on 2016/12/25.
 */

public class FlosingApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        AVOSCloud.initialize(this,"o0hgIj3eXndNi9oimoBf63sq-gzGzoHsz", "XaIIq0CsmKlY75CXPOUGISIW");

    }
}
