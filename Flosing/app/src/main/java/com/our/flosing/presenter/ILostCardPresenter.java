package com.our.flosing.presenter;

/**
 * Created by huangrui on 2016/12/30.
 */

public interface ILostCardPresenter extends IBasePresenter {
    void getLostDetail(final String lid);
    void getOwner(final String lid);
}
