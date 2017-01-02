package com.our.flosing.view;

import android.app.DatePickerDialog;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.our.flosing.R;
import com.our.flosing.bean.FoundCard;
import com.our.flosing.presenter.FoundPublishPresenter;

import java.util.Date;
import java.util.Locale;

/**
 * Created by RunNishino on 2017/1/2.
 */

public class FoundPublishActivity extends AppCompatActivity implements IFoundPublishView {

    static private FoundPublishPresenter foundPublishPresenter;

    private EditText titleView;
    private EditText descriptionView;
    private Spinner typesView;
    private EditText nameView;
    private TextView startDateView;
    private TextView endDateView;
    private Spinner contactWayView;
    private EditText contactDetailView;

    private Date startDate;
    private Date endDate;

    private TextView dateChange = null;

    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_lostandfound_publish);

        if(foundPublishPresenter == null) foundPublishPresenter = new FoundPublishPresenter(this);
        foundPublishPresenter.takeView(this);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("发布失物招领");

        titleView = (EditText) findViewById(R.id.edittext_title_publish);
        descriptionView = (EditText) findViewById(R.id.edittext_description_publish);
        typesView = (Spinner) findViewById(R.id.spinner_types_publish);
        nameView = (EditText) findViewById(R.id.edittext_name_publish);
        startDateView = (TextView) findViewById(R.id.textview_startdate_publish);
        endDateView = (TextView) findViewById(R.id.textview_enddate_publish);
        contactWayView = (Spinner) findViewById(R.id.spinner_contactWay_publish);
        contactDetailView = (EditText) findViewById(R.id.edittext_contactDetail_publish);

        //设置calendar对象时间为现在时间
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        java.util.Date date = new java.util.Date();
        calendar.setTime(date);

        year=calendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month=calendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day=calendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        startDateView.setText(year+"-"+(month+1)+"-"+day); //显示当前的年月日
        endDateView.setText(year+"-"+(month+1)+"-"+day);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            startDate = sdf.parse(startDateView.getText().toString());
            endDate = sdf.parse(endDateView.getText().toString());
        }catch (Exception e){
        }

        startDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateChange = startDateView;
                DatePickerDialog datePickerDialog = new DatePickerDialog(FoundPublishActivity.this,dateListener,year,month,day);
                datePickerDialog.show();
            }
        });

        endDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateChange = endDateView;
                DatePickerDialog datePickerDialog = new DatePickerDialog(FoundPublishActivity.this,dateListener,year,month,day);
                datePickerDialog.show();
            }
        });

        final Button submitPublish = (Button) findViewById(R.id.submit_publish_button);
        submitPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean cancel = false;
                View focusView = null;


/*                if ("".equals(startDateView.getText().toString())){
                    Toast.makeText(LostPublishActivity.this,"请选择起始时间",Toast.LENGTH_SHORT).show();
                    cancel = true;
                }
                if ("".equals(endDateView.getText().toString())){
                    Toast.makeText(LostPublishActivity.this,"请选择结束时间",Toast.LENGTH_SHORT).show();
                    cancel = true;
                }*/

                if ("选择联系方式".equals(contactWayView.getSelectedItem().toString())){
                    Toast.makeText(FoundPublishActivity.this,"请选择联系方式",Toast.LENGTH_SHORT).show();
                    cancel = true;
                }
                if ("".equals(contactDetailView.getText().toString())){
                    Toast.makeText(FoundPublishActivity.this,"请输入联系方式",Toast.LENGTH_SHORT).show();
                    cancel = true;
                    focusView = contactDetailView;
                }
                if ("".equals(descriptionView.getText().toString())){
                    Toast.makeText(FoundPublishActivity.this,"请输入正文",Toast.LENGTH_SHORT).show();
                    cancel = true;
                    focusView = descriptionView;
                }
                if ("".equals(titleView.getText().toString())){
                    Toast.makeText(FoundPublishActivity.this,"请输入标题",Toast.LENGTH_SHORT).show();
                    cancel = true;
                    focusView = titleView;
                }
                if ((!"现金".equals(typesView.getSelectedItem().toString())) && "".equals(nameView.getText().toString())){
                    Toast.makeText(FoundPublishActivity.this,"请输入名称",Toast.LENGTH_SHORT).show();
                    cancel = true;
                    focusView = nameView;
                }
                if (startDate.after(endDate)){
                    Toast.makeText(FoundPublishActivity.this,"开始时间必须早于或等于结束时间",Toast.LENGTH_SHORT).show();
                    cancel = true;
                }
                if ("请选择类型".equals(typesView.getSelectedItem().toString())){
                    Toast.makeText(FoundPublishActivity.this,"请选择类型",Toast.LENGTH_SHORT).show();
                    cancel = true;
                }

                if(cancel){
                    if (focusView !=null ) focusView.requestFocus();
                }else{
                    attemptFoundPublish();
                }

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
            Log.d("dateChange",dateChange.getText().toString());
            dateChange = null;

            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try{
                startDate = sdf.parse(startDateView.getText().toString());
                endDate = sdf.parse(endDateView.getText().toString());
            }catch (Exception e){
            }
        }
    };


    private void attemptFoundPublish() {

        try {
            FoundCard foundCard = new FoundCard();

            foundCard.setTitle(titleView.getText().toString());
            foundCard.setType(typesView.getSelectedItem().toString());
            foundCard.setDescription(descriptionView.getText().toString());
            foundCard.setName(nameView.getText().toString());
            foundCard.setSDate(startDate);
            foundCard.setEDate(endDate);
            foundCard.setIsFinish(false);
            foundCard.setContactWay(contactWayView.getSelectedItem().toString());
            foundCard.setContactDetail(contactDetailView.getText().toString());

            //test
            Log.d("lostCard",foundCard.getTitle());
            Log.d("lostCard",foundCard.getType());
            Log.d("lostCard",foundCard.getDescription());
            Log.d("lostCard",foundCard.getSDate().toString());
            Log.d("lostCard",foundCard.getEDate().toString());
            Log.d("lostCard",foundCard.getContactWay());
            Log.d("lostCard",foundCard.getContactDetail());

            foundPublishPresenter.publishFound(foundCard);

        } catch (Exception e) {

        }
    }

    @Override
    public void updateView() {
        Toast.makeText(FoundPublishActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(FoundPublishActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}