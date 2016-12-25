package com.our.flosing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.SaveCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //测试连接代码
//        AVObject testObject = new AVObject("TestObject");
//        testObject.put("words","Hello World!");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null){
//                    LogUtil.log.d("save","success");
//                }
//            }
//        });
    }
}
