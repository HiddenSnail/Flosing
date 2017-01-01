package com.our.flosing.model;

import com.our.flosing.bean.FoundCard;

import rx.Observable;

/**
 * Created by huangrui on 2016/12/28.
 */

public interface IFoundCardModel {
    Observable<Boolean> publishFound(final FoundCard foundCard);
}
