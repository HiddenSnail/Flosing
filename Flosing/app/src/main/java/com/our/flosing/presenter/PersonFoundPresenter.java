package com.our.flosing.presenter;

import com.our.flosing.bean.FoundCard;
import com.our.flosing.model.UserModel;
import com.our.flosing.view.IBaseView;
import com.our.flosing.view.IFoundPersonFragmentView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by huangrui on 2017/1/4.
 */

public class PersonFoundPresenter implements IPersonFoundPresenter {
    private UserModel userModel;
    private IFoundPersonFragmentView foundPersonFragmentView;

    public PersonFoundPresenter(IFoundPersonFragmentView foundPersonFragmentView) {
        this.foundPersonFragmentView = foundPersonFragmentView;
        this.userModel = new UserModel();
    }

    @Override
    public void takeView(IBaseView baseView) {
        this.foundPersonFragmentView = (IFoundPersonFragmentView) baseView;
    }

    public void getPersonFound(Integer pageNumber) {
        userModel.getUserFound(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<FoundCard>>() {
                    @Override
                    public void call(List<FoundCard> foundCards) {
                        foundPersonFragmentView.refreshView(foundCards);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        foundPersonFragmentView.showError(throwable.getMessage());
                    }
                });
    }


}
