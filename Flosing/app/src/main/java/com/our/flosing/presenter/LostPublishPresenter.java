package com.our.flosing.presenter;

import com.our.flosing.bean.LostCard;
import com.our.flosing.model.ILostCardModel;
import com.our.flosing.model.LostCardModel;
import com.our.flosing.view.ILostPublishView;

import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by huangrui on 2016/12/28.
 */

public class LostPublishPresenter implements ILostPublishPresenter {
    private ILostPublishView lostPublishView;
    private LostCardModel lostCardModel;

    public LostPublishPresenter(ILostPublishView lostPublishView) {
        this.lostPublishView = lostPublishView;
        this.lostCardModel = new LostCardModel();
    }

    public void publishLost(LostCard lostCard) {
        lostCardModel.publishLost(lostCard)
                .observeOn(Schedulers.io())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        lostPublishView.updateView();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        lostPublishView.showError(throwable.getMessage());
                    }
                });
    }
}
