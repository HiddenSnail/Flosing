package com.our.flosing.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.internal.schedulers.NewThreadWorker;

/**
 * Created by huangrui on 2016/12/28.
 */

public class LostCardModel implements ILostCardModel {
    static private final Integer EPN = 10;

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
                avLost.put("isFinish", lostCard.getIsFinish());
                avLost.put("contactWay", lostCard.getContactWay());
                avLost.put("contactDetail", lostCard.getContactDetail());
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

    public Observable<List<LostCard>> getPageOfLosts(final Integer pageNumber) {
        return Observable.create(new Observable.OnSubscribe<List<LostCard>>() {
            @Override
            public void call(final Subscriber<? super List<LostCard>> subscriber) {

                AVQuery<AVObject> queryLost = new AVQuery<>("Lost");
                List<String> keys = Arrays.asList("title", "name", "type", "startDate", "endDate");
                queryLost.selectKeys(keys).limit(EPN)
                        .skip((pageNumber-1)*EPN)
                        .whereEqualTo("isFinish", false)
                        .orderByDescending("createdAt");

                queryLost.findInBackground(new FindCallback<AVObject>() {

                    @Override
                    public void done(List<AVObject> list, AVException e) {

                        if (e == null) {
                            List<LostCard> losts = new ArrayList<>();
                            for (AVObject object:list) {
                                LostCard lostCard = new LostCard();
                                lostCard.setId(object.getObjectId());
                                lostCard.setTitle(object.getString("title"));
                                lostCard.setName(object.getString("name"));
                                lostCard.setType(object.getString("type"));
                                lostCard.setSDate(object.getDate("startDate"));
                                lostCard.setEDate(object.getDate("endDate"));
                                losts.add(lostCard);
                            }
                            subscriber.onNext(losts);
                            subscriber.onCompleted();
                        } else subscriber.onError(e);
                    }
                });
            }
        });
    }

    public Observable<LostCard> getLostByLid(final String lid) {
        return Observable.create(new Observable.OnSubscribe<LostCard>() {
            @Override
            public void call(final Subscriber<? super LostCard> subscriber) {

                final AVObject avLost = AVObject.createWithoutData("Lost", lid);
                avLost.fetchInBackground(new GetCallback<AVObject>() {
                    @Override
                    public void done(AVObject avObject, AVException e) {
                        if (e == null) {
                            LostCard lostCard = new LostCard();
                            lostCard.setId(avObject.getObjectId());
                            lostCard.setTitle(avObject.getString("title"));
                            lostCard.setName(avObject.getString("name"));
                            lostCard.setType(avObject.getString("type"));
                            lostCard.setSDate(avObject.getDate("startDate"));
                            lostCard.setEDate(avObject.getDate("endDate"));
                            lostCard.setContactWay(avObject.getString("contactWay"));
                            lostCard.setContactDetail(avObject.getString("contactDetail"));
                            lostCard.setDescription(avObject.getString("description"));

                            subscriber.onNext(lostCard);
                            subscriber.onCompleted();
                        } else subscriber.onError(e);
                    }
                });
            }
        });
    }

    public Observable<User> getOwnerByLid(final String lid) {

        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                AVObject avLost = AVObject.createWithoutData("Lost", lid);
                try {
                    avLost.fetch("owner");
                    User owner = new User();
                    owner.setId(avLost.getAVUser("owner").getObjectId());
                    owner.setUsername(avLost.getAVUser("owner").getUsername());
                    subscriber.onNext(owner);
                    subscriber.onCompleted();
                } catch (AVException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<List<LostCard>> searchLosts(final String type, final String name,
                                                  final Date pickdate, final Integer pageNumber)
    {
        return Observable.create(new Observable.OnSubscribe<List<LostCard>>() {
            @Override
            public void call(final Subscriber<? super List<LostCard>> subscriber) {

                if (type.isEmpty() || type == null) {
                    subscriber.onError(new Throwable("类型为空无法返回"));
                    return;
                }

                AVQuery<AVObject> typeQuery = new AVQuery<AVObject>("Lost");
                if (type != null && !type.isEmpty()) { typeQuery.whereEqualTo("type", type); }

                AVQuery<AVObject>  nameQuery = new AVQuery<AVObject>("Lost");
                if (name != null && !name.isEmpty()) { nameQuery.whereContains("name", name); }

                AVQuery<AVObject> dateQuery = new AVQuery<AVObject>("Lost");
                if (pickdate != null) { dateQuery.whereLessThanOrEqualTo("startDate", pickdate); }

                AVQuery<AVObject> query = AVQuery.and(Arrays.asList(typeQuery, nameQuery, dateQuery));
                List<String> keys = Arrays.asList("title", "name", "type", "startDate", "endDate");
                query.selectKeys(keys)
                        .limit(EPN).skip((pageNumber-1)*EPN)
                        .whereEqualTo("isFinish", false)
                        .orderByDescending("createdAt");

                query.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {

                        if (e == null) {
                            List<LostCard> losts = new ArrayList<>();
                            for (AVObject object:list) {
                                LostCard lostCard = new LostCard();
                                lostCard.setId(object.getObjectId());
                                lostCard.setTitle(object.getString("title"));
                                lostCard.setName(object.getString("name"));
                                lostCard.setType(object.getString("type"));
                                lostCard.setSDate(object.getDate("startDate"));
                                lostCard.setEDate(object.getDate("endDate"));
                                losts.add(lostCard);
                            }
                            subscriber.onNext(losts);
                            subscriber.onCompleted();
                        } else subscriber.onError(e);
                    }
                });

            }
        });
    }

}
