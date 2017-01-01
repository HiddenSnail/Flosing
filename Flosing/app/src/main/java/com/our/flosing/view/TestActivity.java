package com.our.flosing.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.our.flosing.R;
import com.our.flosing.bean.LostCard;
import com.our.flosing.presenter.LostCardPresenter;
import com.our.flosing.presenter.RegisterPresenter;

/**
 * Created by huangrui on 2016/12/31.
 */

public class TestActivity extends AppCompatActivity {
    static private LostCardPresenter lostCardPresenter;
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);
    }
}
