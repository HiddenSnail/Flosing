package com.our.flosing.presenter;

import com.avos.avoscloud.okhttp.internal.http.CacheStrategy;
import com.our.flosing.bean.FoundCard;
import com.our.flosing.model.FoundCardModel;
import com.our.flosing.view.IBaseView;
import com.our.flosing.view.IFoundFragmentView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by huangrui on 2017/1/2.
 */

public class FoundFragmentPresenter implements IFoundFragmentPresenter {
    private FoundCardModel foundCardModel;
    private IFoundFragmentView foundFragmentView;

    public FoundFragmentPresenter(IFoundFragmentView foundFragmentView) {
        this.foundFragmentView = foundFragmentView;
        this.foundCardModel = new FoundCardModel();
    }

    @Override
    public void takeView(IBaseView baseView) {
        this.foundFragmentView = (IFoundFragmentView) baseView;
    }

    @Override
    public void getPageOfFounds(final Integer pageNumber) {
        foundCardModel.getPageOfFounds(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<FoundCard>>() {
                    @Override
                    public void call(List<FoundCard> foundCards) {
                        foundFragmentView.refreshView(foundCards);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        foundFragmentView.showError(throwable.getMessage());
                    }
                });
    }
}
