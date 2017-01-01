package com.our.flosing.model;

import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.User;

import java.util.List;

import rx.Observable;

/**
 * Created by huangrui on 2016/12/28.
 */

public interface ILostCardModel {
    Observable<Boolean> publishLost(final LostCard lostCard);
    Observable<List<LostCard>> getPageOfLosts(final Integer pageNumber);
    Observable<LostCard> getLostByLid(final String lid);
    Observable<User> getOwnerByLid(final String lid);
}
