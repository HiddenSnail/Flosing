package com.our.flosing.model;

import com.avos.avoscloud.AVObject;
import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.User;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by huangrui on 2016/12/28.
 */

public class LostCardModel implements ILostCardModel {
    public Observable<Boolean> publishLost(final LostCard lostCard) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                AVObject avLost = new AVObject("Lost");
//                avLost.put("");
            }
        });
    }

}
