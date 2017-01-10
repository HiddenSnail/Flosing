package com.our.flosing.presenter;

/**
 * Created by huangrui on 2017/1/2.
 */

public interface IFoundCardPresenter extends IBasePresenter {
    void getFoundDetail(final String fid);
    void getPicker(final String fid);
    void getOwner(final String fid);
}
