package com.our.flosing.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.our.flosing.R;
import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.User;
import com.our.flosing.presenter.LostCardPresenter;

/**
 * Created by RunNishino on 2016/12/31.
 */

public class LostDetailActivity extends AppCompatActivity implements ILostDetailView {
    static private LostCardPresenter lostCardPresenter;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_lost_detail);
        if (lostCardPresenter == null) lostCardPresenter = new LostCardPresenter(this);

    }

    public void initLostDetail(LostCard lostCard) {}
    public void initOwnerInfo(User owner) {}
    public void showError(String msg) {}
}
