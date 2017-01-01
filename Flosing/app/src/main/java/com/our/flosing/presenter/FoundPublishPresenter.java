package com.our.flosing.presenter;

import com.our.flosing.bean.FoundCard;
import com.our.flosing.model.FoundCardModel;
import com.our.flosing.view.IBaseView;
import com.our.flosing.view.IFoundPublishView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by huangrui on 2017/1/2.
 */

public class FoundPublishPresenter implements IFoundPublishPrsenter {
    private IFoundPublishView foundPublishView;
    private FoundCardModel foundCardModel;

    public FoundPublishPresenter(IFoundPublishView foundPublishView) {
        this.foundPublishView = foundPublishView;
        this.foundCardModel = new FoundCardModel();
    }

    @Override
    public void takeView(IBaseView baseView) { this.foundPublishView = (IFoundPublishView) baseView; }

    @Override
    public void publishFound(final FoundCard foundCard) {
        foundCardModel.publishFound(foundCard)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        foundPublishView.updateView();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        foundPublishView.showError(throwable.getMessage());
                    }
                });
    }

}
