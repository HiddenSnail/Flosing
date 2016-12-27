package com.our.flosing;

import android.app.DatePickerDialog;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import java.util.Date;
import java.util.Locale;

/**
 * Created by RunNishino on 2016/12/26.
 */

public class LostPublishActivity extends AppCompatActivity {
    private EditText titleView;
    private EditText descriptionView;
    private Spinner typesView;
    private TextView startDateView;
    private TextView endDateView;

    private TextView dateChange = null;

    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.avtivity_lost_publish);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("发布");

        titleView = (EditText) findViewById(R.id.edittext_title_publish);
        descriptionView = (EditText) findViewById(R.id.edittext_description_publish);
        typesView = (Spinner) findViewById(R.id.spinner_types_publish);
        startDateView = (TextView) findViewById(R.id.textview_startdate_publish);
        endDateView = (TextView) findViewById(R.id.textview_enddate_publish);

        //设置calendar对象时间为现在时间
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        java.util.Date date = new java.util.Date();
        calendar.setTime(date);

        year=calendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month=calendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day=calendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        startDateView.setText(year+"-"+(month+1)+"-"+day); //显示当前的年月日
        endDateView.setText(year+"-"+(month+1)+"-"+day);

        startDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateChange = startDateView;
                DatePickerDialog datePickerDialog = new DatePickerDialog(LostPublishActivity.this,dateListener,year,month,day);
                datePickerDialog.show();
            }
        });

        endDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateChange = endDateView;
                DatePickerDialog datePickerDialog = new DatePickerDialog(LostPublishActivity.this,dateListener,year,month,day);
                datePickerDialog.show();
            }
        });


        final Button submitPublish = (Button) findViewById(R.id.submit_publish_button);
        submitPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLostPublish();

            }
        });
    }

    private DatePickerDialog.OnDateSetListener dateListener=new DatePickerDialog.OnDateSetListener()
    {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear, int dayOfMonth) {


            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            year=myyear;
            month=monthOfYear;
            day=dayOfMonth;
            //更新日期
            updateDate();

        }
        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate()
        {
            //在TextView上显示日期
            dateChange.setText(year+"-"+(month+1)+"-"+day);
            System.out.println(dateChange.getText().toString());
            dateChange = null;
        }
    };

    private void attemptLostPublish(){

        boolean cancel = false;
        View focusView = null;

        if ("请选择类型".equals(typesView.getSelectedItem().toString())){
            Toast.makeText(LostPublishActivity.this,"请选择类型",Toast.LENGTH_SHORT).show();
            cancel = true;
        }
        if ("".equals(startDateView.getText().toString())){
            Toast.makeText(LostPublishActivity.this,"请选择起始时间",Toast.LENGTH_SHORT).show();
            cancel = true;
        }
        if ("".equals(endDateView.getText().toString())){
            Toast.makeText(LostPublishActivity.this,"请选择结束时间",Toast.LENGTH_SHORT).show();
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
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate;
            Date endDate;
            try {

//                String t = sdf.format(startDate);
                startDate = sdf.parse(startDateView.getText().toString());
                endDate = sdf.parse(endDateView.getText().toString());

                AVObject lost = new AVObject("Lost");

                lost.put("title",titleView.getText().toString());
                lost.put("type",typesView.getSelectedItem().toString());
                lost.put("description",descriptionView.getText().toString());
                lost.put("owner", AVUser.getCurrentUser());
                lost.put("startDate",startDate);
                lost.put("endDate",endDate);
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
            } catch (Exception e) {
                Toast.makeText(LostPublishActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

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
