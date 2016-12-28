package com.our.flosing.model;

import com.our.flosing.bean.LostCard;

import rx.Observable;

/**
 * Created by huangrui on 2016/12/28.
 */

public interface ILostCardModel {
    Observable<Boolean> publishLost(final LostCard lostCard);
}
