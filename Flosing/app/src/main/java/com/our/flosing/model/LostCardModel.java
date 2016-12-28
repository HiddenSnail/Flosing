package com.our.flosing.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.User;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by huangrui on 2016/12/28.
 */

public class LostCardModel implements ILostCardModel {
    public Observable<Boolean> publishLost(final LostCard lostCard) {
        return Observable.create(new Observable.OnSubscribe<Boolean>()
        {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber)
            {
                AVObject avLost = new AVObject("Lost");
                avLost.put("type", lostCard.getType());
                avLost.put("name", lostCard.getName());
                avLost.put("title", lostCard.getTitle());
                avLost.put("description", lostCard.getDescription());
                avLost.put("startDate", lostCard.getSDate());
                avLost.put("endDate", lostCard.getEDate());
                avLost.put("owner", AVUser.getCurrentUser());
                avLost.saveInBackground(new SaveCallback()
                {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            subscriber.onNext(true);
                            subscriber.onCompleted();
                        } else subscriber.onError(e);
                    }
                });
            }
        });
    }



}
