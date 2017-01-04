package com.our.flosing.view;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.our.flosing.R;
import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.User;
import com.our.flosing.presenter.LostCardPresenter;

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
        lostDetailTypeView.setText("书籍");
        lostDetailNameView.setText(lostCard.getName());
        lostDetailDescriptionView.setText(lostCard.getDescription());
        lostDetailSDateView.setText(lostDetailSDate);
        lostDetailEDateView.setText(lostDetailEDate);
        lostDetailContactWayView.setText(lostCard.getContactWay());
        lostDetailContactDetailView.setText(lostCard.getContactDetail());


    }
    public void initOwnerInfo(User owner) {

        Log.d("lostDetail","ownerName:"+owner.getUsername());

        lostDetailUsernameView.setText(owner.getUsername());
    }

    public void showError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
