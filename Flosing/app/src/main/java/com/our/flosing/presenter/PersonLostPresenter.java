package com.our.flosing.presenter;

import com.our.flosing.bean.LostCard;
import com.our.flosing.model.UserModel;
import com.our.flosing.view.IBaseView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by huangrui on 2017/1/3.
 */

public class PersonLostPresenter implements IPersonLostPresenter {
    private UserModel userModel;
    private IPersonLostFragment personLostFragment;

    public PersonLostPresenter(IPersonLostFragment personLostFragment) {
        this.personLostFragment = personLostFragment;
        userModel = new UserModel();
    }

    @Override
    public void takeView(IBaseView baseView) {
        personLostFragment = (IPersonLostFragment) baseView;
    }

    void getPersonLost() {
        userModel.getUserLost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<LostCard>>() {
                    @Override
                    public void call(List<LostCard> lostCards) {
                        personLostFragment.refreshView(lostCards);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        personLostFragment.showError(throwable.getMessage());
                    }
                });
    }
}
