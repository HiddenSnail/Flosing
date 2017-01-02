package com.our.flosing.view;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.our.flosing.R;
import com.our.flosing.bean.LostCard;
import com.our.flosing.model.LostCardModel;
import com.our.flosing.model.UserModel;
import com.our.flosing.presenter.LostCardPresenter;
import com.our.flosing.presenter.LostSearchPresenter;
import com.our.flosing.presenter.RegisterPresenter;

import java.util.List;

/**
 * Created by huangrui on 2016/12/31.
 */

public class TestActivity extends AppCompatActivity implements IBaseView {
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);
        LostCardModel lostCardModel = new LostCardModel();
        LostSearchPresenter lostSearchPresenter = new LostSearchPresenter(this);
//        lostSearchPresenter.searchLosts("现金", null, null, 1);
        UserModel userModel = new UserModel();
        userModel.uploadAvatar();
    }
}
