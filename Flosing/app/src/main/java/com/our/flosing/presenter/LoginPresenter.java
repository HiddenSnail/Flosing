package com.our.flosing.presenter;

import com.our.flosing.model.UserModel;
import com.our.flosing.view.ILoginView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huangrui on 2016/12/27.
 */

public class LoginPresenter implements ILoginPresenter {
    private ILoginView loginView;
    private UserModel userModel;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        userModel = new UserModel();
    }

    public void login(final String username, final String password) {
        loginView.showProgressDialog();
        userModel.login(username, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginView.hideProgressDialog();
                        loginView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean state) {
                        loginView.hideProgressDialog();
                        loginView.updateView();
                    }
                });
    }
}
