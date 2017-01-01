package com.our.flosing.presenter;

import com.our.flosing.view.IBaseView;
import com.our.flosing.view.IRegisterView;

/**
 * Created by huangrui on 2016/12/27.
 */

public interface ILoginPresenter extends IBasePresenter {
    void login(final String username, final String password);
    void isLogin();
}
