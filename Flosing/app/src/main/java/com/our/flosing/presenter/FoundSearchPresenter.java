package com.our.flosing.presenter;

import com.our.flosing.bean.FoundCard;
import com.our.flosing.model.FoundCardModel;
import com.our.flosing.view.IBaseView;
import com.our.flosing.view.IFoundSearchResultFragmentView;

import java.util.Date;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by huangrui on 2017/1/2.
 */

public class FoundSearchPresenter implements IFoundSearchPresenter {
    private FoundCardModel foundCardModel;
    private IFoundSearchResultFragmentView foundSearchResultFragmentView;

    public FoundSearchPresenter(IFoundSearchResultFragmentView foundSearchResultFragmentView) {
        this.foundSearchResultFragmentView = foundSearchResultFragmentView;
        foundCardModel = new FoundCardModel();
    }

    @Override
    public void takeView(IBaseView baseView) {
        this.foundSearchResultFragmentView = (IFoundSearchResultFragmentView) baseView;
    }

    public void searchFounds(final String type, final String name,
                             final Date lostdate, final Integer pageNumber)
    {
        foundCardModel.searchFounds(type, name, lostdate, pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<FoundCard>>() {
                    @Override
                    public void call(List<FoundCard> foundCards) {
                        foundSearchResultFragmentView.refreshView(foundCards);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
//                        foundSearchResultFragmentView.showError(throwable.getMessage());
                    }
                });
    }
}
