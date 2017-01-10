package com.our.flosing.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.our.flosing.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by RunNishino on 2017/1/6.
 */

public class ImageDetailActivity extends AppCompatActivity {
    ImageView imageView;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    imageView.setImageBitmap(bitmap);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        imageView = (ImageView) findViewById(R.id.image_detail);

        final String url = getIntent().getStringExtra("url");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = getURLimage(url);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = bitmap;
                handler.sendMessage(msg);
            }
        }).start();
    }

    public Bitmap getURLimage(String url){
        Bitmap bitmap = null;
        try {
            URL myurl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) myurl.openConnection();
            connection.setConnectTimeout(6000);//设置超时
            connection.setDoInput(true);
            connection.setUseCaches(false);//不缓存
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
