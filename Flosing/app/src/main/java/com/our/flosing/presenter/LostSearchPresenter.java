package com.our.flosing.presenter;

import android.util.Log;

import com.our.flosing.bean.LostCard;
import com.our.flosing.model.LostCardModel;
import com.our.flosing.view.IBaseView;
import com.our.flosing.view.TestActivity;

import java.util.Date;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by huangrui on 2017/1/2.
 */

public class LostSearchPresenter implements ILostSearchPresenter {
    private LostCardModel lostCardModel;
    private TestActivity testActivity;

    public LostSearchPresenter(TestActivity testActivity) {
        this.testActivity = testActivity;
        lostCardModel = new LostCardModel();
    }

    @Override
    public void takeView(IBaseView baseView) {
        this.testActivity = (TestActivity) baseView;
    }

    public void searchLosts(final String type, final String name,
                            final Date pickdate, final Integer pageNumber)
    {
        lostCardModel.searchLosts(type, name, pickdate, pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<LostCard>>() {
                    @Override
                    public void call(List<LostCard> lostCards) {
                        Log.e("数组长度", String.valueOf(lostCards.size()));
                    }
                });
    }

}