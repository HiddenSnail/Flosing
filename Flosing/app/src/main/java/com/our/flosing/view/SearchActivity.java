package com.our.flosing.view;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.our.flosing.R;

import java.util.Date;
import java.util.Locale;

/**
 * Created by RunNishino on 2017/1/1.
 */

public class SearchActivity extends AppCompatActivity {
    EditText searchNameView;
    ImageButton searchStartView;
    Spinner searchTypeView;
    TextView searchDateView;

    private int year;
    private int month;
    private int day;

    private Date searchDate;
    private String searchDateStr;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search);

        searchNameView = (EditText) findViewById(R.id.edittext_name_search);
        searchStartView = (ImageButton) findViewById(R.id.button_start_search);
        searchTypeView = (Spinner) findViewById(R.id.spinner_types_search);
        searchDateView = (TextView) findViewById(R.id.textview_date_search);

        //设置calendar对象时间为现在时间
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        java.util.Date date = new java.util.Date();
        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month = calendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day = calendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天

        searchDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchActivity.this,dateListener,year,month,day);
                datePickerDialog.show();
            }
        });

        searchStartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean cancel = false;

                if ("请选择类型".equals(searchTypeView.getSelectedItem().toString())){
                    cancel = true;
                    Toast.makeText(SearchActivity.this,"必须选择类型",Toast.LENGTH_SHORT).show();
                }
                if ("".equals(searchDateView.getText().toString())){
                    cancel = true;
                    Toast.makeText(SearchActivity.this,"请选择时间",Toast.LENGTH_SHORT).show();
                }

                if (!cancel){
                    attemptSearch();
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
            year = myyear;
            month = monthOfYear;
            day = dayOfMonth;
            //更新日期
            updateDate();

        }
        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate()
        {
            //在TextView上显示日期
            searchDateView.setText(year+"-"+(month+1)+"-"+day);

            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try{
                searchDate = sdf.parse(searchDateView.getText().toString());
            }catch (Exception e){
            }
        }
    };

    private void attemptSearch(){
        Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);

        intent.putExtra("searchName",searchNameView.getText().toString());
        intent.putExtra("searchType",searchTypeView.getSelectedItem().toString());

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        searchDateStr = sdf.format(searchDate);
        intent.putExtra("searchDate",searchDateStr);

        startActivity(intent);
    }
}
