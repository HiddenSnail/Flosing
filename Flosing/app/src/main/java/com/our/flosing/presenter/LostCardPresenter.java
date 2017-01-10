package com.our.flosing.presenter;

import android.util.Log;

import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.User;
import com.our.flosing.model.LostCardModel;
import com.our.flosing.view.IBaseView;
import com.our.flosing.view.ILostDetailView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by huangrui on 2016/12/30.
 */

public class LostCardPresenter implements ILostCardPresenter {
    private LostCardModel lostCardModel;
    private ILostDetailView lostDetailView;

    public LostCardPresenter(ILostDetailView lostDetailView) {
        this.lostDetailView = lostDetailView;
        this.lostCardModel = new LostCardModel();
    }

    @Override
    public void takeView(IBaseView baseView) {
        this.lostDetailView = (ILostDetailView) baseView;
    }

    @Override
    public void getLostDetail(final String lid) {
        lostCardModel.getLostByLid(lid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LostCard>() {
                    @Override
                    public void call(LostCard lostCard) {
                        lostDetailView.initLostDetail(lostCard);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        lostDetailView.showError(throwable.getMessage());
                    }
                });
    }

    @Override
    public void getOwner(final String lid) {
        lostCardModel.getOwnerByLid(lid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        lostDetailView.initOwnerInfo(user);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        lostDetailView.showError(throwable.getMessage());
                    }
                });

    }

    @Override
    public void getPicker(final String lid) {
        lostCardModel.getPickerByLid(lid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        lostDetailView.initPickerInfo(user);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        lostDetailView.showError(throwable.getMessage());
                    }
                });
    }
}
