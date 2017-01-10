package com.our.flosing.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.our.flosing.R;
import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.User;
import com.our.flosing.presenter.LostCardPresenter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 * Created by RunNishino on 2016/12/31.
 */

public class LostDetailActivity extends AppCompatActivity implements ILostDetailView {
    static private LostCardPresenter lostCardPresenter;

    String lostDetailSDate;
    String lostDetailEDate;

    TextView lostDetailTitleView;
    TextView lostDetailTypeView;
    TextView lostDetailNameView;
    TextView lostDetailSDateView;
    TextView lostDetailEDateView;
    TextView lostDetailDescriptionView;
    TextView lostDetailContactWayView;
    TextView lostDetailContactDetailView;
    TextView lostDetailUsernameView;

    TextView lostDetailPickerContactDetailView;
    TextView lostDetailPickerNameView;
    View lostDetailPickerInfo;

    ImageView lostDetailImageView;

    //测试用：
    private String url = null;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    lostDetailImageView.setImageBitmap(bitmap);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_lostandfound_detail);

        if (lostCardPresenter == null) lostCardPresenter = new LostCardPresenter(this);
        lostCardPresenter.takeView(this);

        lostDetailTitleView = (TextView) findViewById(R.id.textview_title_lostDetail);
        lostDetailTypeView = (TextView) findViewById(R.id.textview_type_lostDetail);
        lostDetailNameView = (TextView) findViewById(R.id.textview_name_lostDetail);
        lostDetailSDateView = (TextView) findViewById(R.id.textview_startdate_lostDetail);
        lostDetailEDateView = (TextView) findViewById(R.id.textview_enddate_lostDetail);
        lostDetailDescriptionView = (TextView) findViewById(R.id.textview_description_lostDetail);
        lostDetailUsernameView = (TextView) findViewById(R.id.textview_username_lostDetail);
        lostDetailContactWayView = (TextView) findViewById(R.id.textview_contactway_lostDetail);
        lostDetailContactDetailView = (TextView) findViewById(R.id.textview_contactdetial_lostDetail);
        lostDetailImageView = (ImageView) findViewById(R.id.imageview_image_lostDetail);

        lostDetailPickerInfo = findViewById(R.id.lostDetial_pickerInfo);
        lostDetailPickerNameView = (TextView) findViewById(R.id.textview_pickername_lostDetail);
        lostDetailPickerContactDetailView = (TextView) findViewById(R.id.textview_pickercontactdetial_lostDetail);



        Intent intent = getIntent();
        String lostDetailId = intent.getStringExtra("lostDetailId");

        lostCardPresenter.getLostDetail(lostDetailId);
        lostCardPresenter.getOwner(lostDetailId);

    }

    public void initLostDetail(LostCard lostCard) {

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        lostDetailSDate = sdf.format(lostCard.getSDate());
        lostDetailEDate = sdf.format(lostCard.getEDate());

        Log.d("lostDetail","title:"+lostCard.getTitle());
        Log.d("lostDetail","type:"+lostCard.getType());
        Log.d("lostDetail","name:"+lostCard.getName());
        Log.d("lostDetail","description:"+lostCard.getDescription());

        lostDetailTitleView.setText(lostCard.getTitle());
        lostDetailTypeView.setText(lostCard.getType());
        lostDetailNameView.setText(lostCard.getName());
        lostDetailDescriptionView.setText(lostCard.getDescription());
        lostDetailSDateView.setText(lostDetailSDate);
        lostDetailEDateView.setText(lostDetailEDate);
        lostDetailContactWayView.setText(lostCard.getContactWay());
        lostDetailContactDetailView.setText(lostCard.getContactDetail());

        if (lostCard.getIsFinish()){
            lostDetailPickerInfo.setVisibility(View.GONE);
        }else{
//            lostDetailPickerNameView.setText();
        }

        if (url != ""){
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
        }else{
            lostDetailImageView.setVisibility(View.GONE);
        }

        lostDetailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LostDetailActivity.this, ImageDetailActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });

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

    public void initOwnerInfo(User owner) {

        Log.d("lostDetail","ownerName:"+owner.getUsername());

        lostDetailUsernameView.setText(owner.getUsername());
    }

    public void showError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
