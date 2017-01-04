package com.our.flosing.presenter;

import android.util.Log;

import com.our.flosing.bean.LostCard;
import com.our.flosing.model.UserModel;
import com.our.flosing.view.IBaseView;
import com.our.flosing.view.ILostPersonFragmentView;
import com.our.flosing.view.PersonLostFragment;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by huangrui on 2017/1/3.
 */

public class PersonLostPresenter implements IPersonLostPresenter {
    private UserModel userModel;
    private ILostPersonFragmentView personLostFragment;

    public PersonLostPresenter(ILostPersonFragmentView personLostFragment) {
        this.personLostFragment = personLostFragment;
        this.userModel = new UserModel();
    }

    @Override
    public void takeView(IBaseView baseView) {
        this.personLostFragment = (ILostPersonFragmentView) baseView;
    }

    public void getPersonLost(Integer pageNumber) {
        userModel.getUserLost(pageNumber)
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
                        throwable.printStackTrace();
                        personLostFragment.showError(throwable.getMessage());
                    }
                });
    }
}
