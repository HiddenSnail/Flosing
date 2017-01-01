package com.our.flosing.presenter;

/**
 * Created by huangrui on 2016/12/28.
 */

public interface IRegisterPresenter extends IBasePresenter {
    void register(final String username, final String email, final String password);
}
