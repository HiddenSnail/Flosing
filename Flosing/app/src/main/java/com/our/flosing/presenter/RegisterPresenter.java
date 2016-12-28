package com.our.flosing.presenter;

import com.our.flosing.model.UserModel;
import com.our.flosing.view.IRegisterView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huangrui on 2016/12/28.
 */

public class RegisterPresenter implements IRegisterPresenter {
    private IRegisterView registerView;
    private UserModel userModel;

    public RegisterPresenter(IRegisterView registerView) {
        this.registerView = registerView;
        userModel = new UserModel();
    }

    public void register(final String username, final String email, final String password) {
        userModel.register(username, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        registerView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean state) {
                        registerView.updateView();
                    }
                });
    }
}
