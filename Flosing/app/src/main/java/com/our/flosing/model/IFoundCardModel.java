package com.our.flosing.model;

import com.our.flosing.bean.FoundCard;
import com.our.flosing.bean.User;

import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * Created by huangrui on 2016/12/28.
 */

public interface IFoundCardModel {
    Observable<Boolean> publishFound(final FoundCard foundCard);
    Observable<List<FoundCard>> getPageOfFounds(final Integer pageNumber);
    Observable<FoundCard> getFoundByFid(final String fid);
    Observable<User> getPickerByFid(final String fid);
    Observable<List<FoundCard>> searchFounds(final String type, final String name,
                                             final Date lostdate, final Integer pageNumber);
}
