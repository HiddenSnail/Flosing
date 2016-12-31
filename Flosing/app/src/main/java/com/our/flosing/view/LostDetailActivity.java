package com.our.flosing.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.our.flosing.R;

import java.util.Date;

/**
 * Created by RunNishino on 2016/12/31.
 */

public class LostDetailActivity extends AppCompatActivity {

    String lostDetailTitle;
    String lostDetailType;
    String lostDetailName;
    String lostDetailDescription;
    Date lostDetailSDate;
    Date lostDetailEDate;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_lost_detail);

        Intent intent = getIntent();
        String lostDetailId = intent.getStringExtra("lostDetailId");



    }
}
