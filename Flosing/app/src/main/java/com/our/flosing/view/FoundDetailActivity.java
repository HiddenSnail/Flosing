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


/**
 * Created by RunNishino on 2017/1/2.
 */

public class FoundDetailActivity extends AppCompatActivity implements IFoundDetailView {
//    static private FoundCardPresenter foundCardPresenter;

    String foundDetailSDate;
    String foundDetailEDate;

    TextView foundDetailTitleView;
    TextView foundDetailTypeView;
    TextView foundDetailNameView;
    TextView foundDetailSDateView;
    TextView foundDetailEDateView;
    TextView foundDetailDescriptionView;
    TextView foundDetailContactWayView;
    TextView foundDetailContactDetailView;

    TextView foundDetailUsernameView;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_lostandfound_detail);

//        if (foundCardPresenter == null) foundCardPresenter = new FoundCardPresenter(this);
//        foundCardPresenter.takeView(this);

        foundDetailTitleView = (TextView) findViewById(R.id.textview_title_lostDetail);
        foundDetailTypeView = (TextView) findViewById(R.id.textview_type_lostDetail);
        foundDetailNameView = (TextView) findViewById(R.id.textview_name_lostDetail);
        foundDetailSDateView = (TextView) findViewById(R.id.textview_startdate_lostDetail);
        foundDetailEDateView = (TextView) findViewById(R.id.textview_enddate_lostDetail);
        foundDetailDescriptionView = (TextView) findViewById(R.id.textview_description_lostDetail);
        foundDetailUsernameView = (TextView) findViewById(R.id.textview_username_lostDetail);
        foundDetailContactWayView = (TextView) findViewById(R.id.textview_contactway_lostDetail);
        foundDetailContactDetailView = (TextView) findViewById(R.id.textview_contactdetial_lostDetail);

        Intent intent = getIntent();
        String foundDetailId = intent.getStringExtra("foundDetailId");

//        foundCardPresenter.getLostDetail(foundDetailId);
//        foundCardPresenter.getOwner(foundDetailId);
    }

    public void initFoundDetail(LostCard lostCard) {

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        foundDetailSDate = sdf.format(lostCard.getSDate());
        foundDetailEDate = sdf.format(lostCard.getEDate());

        Log.d("lostDetail","title:"+lostCard.getTitle());
        Log.d("lostDetail","type:"+lostCard.getType());
        Log.d("lostDetail","name:"+lostCard.getName());
        Log.d("lostDetail","description:"+lostCard.getDescription());

        foundDetailTitleView.setText(lostCard.getTitle());
        foundDetailTypeView.setText("书籍");
        foundDetailNameView.setText(lostCard.getName());
        foundDetailDescriptionView.setText(lostCard.getDescription());
        foundDetailSDateView.setText(foundDetailSDate);
        foundDetailEDateView.setText(foundDetailEDate);
        foundDetailContactWayView.setText(lostCard.getContactWay());
        foundDetailContactDetailView.setText(lostCard.getContactDetail());
    }
    public void initOwnerInfo(User owner) {

        Log.d("lostDetail","ownerName:"+owner.getUsername());

        foundDetailUsernameView.setText(owner.getUsername());
    }

    public void showError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
