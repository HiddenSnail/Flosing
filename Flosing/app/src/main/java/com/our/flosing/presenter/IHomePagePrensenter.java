package com.our.flosing.presenter;

import com.our.flosing.bean.LostCard;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by huangrui on 2016/12/29.
 */

public interface IHomePagePrensenter extends IBasePresenter {
    void getPageOfLosts(final Integer pageNumber);
}
