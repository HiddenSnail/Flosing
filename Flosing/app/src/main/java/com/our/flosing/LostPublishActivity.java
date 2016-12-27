package com.our.flosing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import java.security.PublicKey;

/**
 * Created by RunNishino on 2016/12/26.
 */

public class LostPublishActivity extends AppCompatActivity {
    private EditText titleView;
    private EditText descriptionView;
    private Spinner typesView;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.avtivity_lost_publish);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("发布");

        titleView = (EditText) findViewById(R.id.edittext_title_publish);
        descriptionView = (EditText) findViewById(R.id.edittext_description_publish);
        typesView = (Spinner) findViewById(R.id.spinner_types_publish);
        //TODO:未完成开始时间和结束时间的选择，需要datepicker。


        final Button submitPublish = (Button) findViewById(R.id.submit_publish_button);
        submitPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLostPublish();

            }
        });
    }

    private void attemptLostPublish(){

        boolean cancel = false;
        View focusView = null;

        if ("请选择类型".equals(typesView.getSelectedItem().toString())){
            Toast.makeText(LostPublishActivity.this,"请选择类型",Toast.LENGTH_SHORT).show();
            cancel = true;
        }
        if ("".equals(titleView.getText().toString())){
            Toast.makeText(LostPublishActivity.this,"请输入标题",Toast.LENGTH_SHORT).show();
            cancel = true;
            focusView = titleView;
        }
        if ("".equals(descriptionView.getText().toString())){
            Toast.makeText(LostPublishActivity.this,"请输入标题",Toast.LENGTH_SHORT).show();
            cancel = true;
            focusView = descriptionView;
        }

        if(cancel){
            focusView.requestFocus();
        }else {
            AVObject lost = new AVObject("Lost");

            lost.put("title",titleView.getText().toString());
            lost.put("type",typesView.getSelectedItem().toString());
            lost.put("description",descriptionView.getText().toString());
            lost.put("owner", AVUser.getCurrentUser());
            lost.put("isFinish",false);
            lost.put("picker",null);

            lost.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e==null){
                        LostPublishActivity.this.finish();
                    }else {
                        Toast.makeText(LostPublishActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }
}
