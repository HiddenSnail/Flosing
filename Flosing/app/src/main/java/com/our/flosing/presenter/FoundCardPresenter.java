package com.our.flosing.presenter;

import com.our.flosing.bean.FoundCard;
import com.our.flosing.bean.User;
import com.our.flosing.model.FoundCardModel;
import com.our.flosing.view.IBaseView;
import com.our.flosing.view.IFoundDetailView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by huangrui on 2017/1/2.
 */

public class FoundCardPresenter implements IFoundCardPresenter{
    private FoundCardModel foundCardModel;
    private IFoundDetailView foundDetailView;

    public FoundCardPresenter(IFoundDetailView foundDetailView) {
        this.foundDetailView = foundDetailView;
        this.foundCardModel = new FoundCardModel();
    }

    @Override
    public void takeView(IBaseView baseView) { this.foundDetailView = (IFoundDetailView) baseView; }

    @Override
    public void getFoundDetail(final String fid) {
        foundCardModel.getFoundByFid(fid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FoundCard>() {
                    @Override
                    public void call(FoundCard foundCard) {
                        foundDetailView.initFoundDetail(foundCard);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        foundDetailView.showError(throwable.getMessage());
                    }
                });

    }

    @Override
    public void getPicker(final String fid) {
        foundCardModel.getPickerByFid(fid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        foundDetailView.initPickerInfo(user);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        foundDetailView.showError(throwable.getMessage());
                    }
                });

    }
}
