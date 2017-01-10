package com.our.flosing.model;

import android.graphics.Bitmap;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.our.flosing.bean.BitmapOperation;
import com.our.flosing.bean.FoundCard;
import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by huangrui on 2017/1/2.
 */

public class FoundCardModel implements IFoundCardModel {
    static private final Integer EPN = 10;

    public Observable<Boolean> publishFound(final FoundCard foundCard) {
        return Observable.create(new Observable.OnSubscribe<Boolean>()
        {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber)
            {
                AVObject avFound = new AVObject("Found");
                avFound.put("type", foundCard.getType());
                avFound.put("name", foundCard.getName());
                avFound.put("title", foundCard.getTitle());
                avFound.put("description", foundCard.getDescription());
                avFound.put("startDate", foundCard.getSDate());
                avFound.put("endDate", foundCard.getEDate());
                avFound.put("picker", AVUser.getCurrentUser());
                avFound.put("isFinish", foundCard.getIsFinish());
                avFound.put("contactWay", foundCard.getContactWay());
                avFound.put("contactDetail", foundCard.getContactDetail());

                if (foundCard.getPics() != null && foundCard.getPics().size() > 0) {
                    for (Bitmap picture:foundCard.getPics()) {
                        String timeStamp = String.valueOf(System.currentTimeMillis()/1000);
                        AVFile file = new AVFile(timeStamp+".jpeg", BitmapOperation.getBitmapByte(picture));
                        avFound.put("FoundPicture", file);
                    }
                }
                avFound.saveInBackground(new SaveCallback()
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

    public Observable<List<FoundCard>> getPageOfFounds(final Integer pageNumber) {
        return Observable.create(new Observable.OnSubscribe<List<FoundCard>>() {
            @Override
            public void call(final Subscriber<? super List<FoundCard>> subscriber) {

                AVQuery<AVObject> queryFound = new AVQuery<>("Found");
                List<String> keys = Arrays.asList("title", "name", "type", "startDate", "endDate");
                queryFound.selectKeys(keys).limit(EPN)
                        .skip((pageNumber-1)*EPN)
                        .whereEqualTo("isFinish", false)
                        .orderByDescending("createdAt");

                queryFound.findInBackground(new FindCallback<AVObject>() {

                    @Override
                    public void done(List<AVObject> list, AVException e) {

                        if (e == null) {
                            List<FoundCard> founds = new ArrayList<>();
                            for (AVObject object:list) {
                                FoundCard foundCard = new FoundCard();
                                foundCard.setId(object.getObjectId());
                                foundCard.setTitle(object.getString("title"));
                                foundCard.setName(object.getString("name"));
                                foundCard.setType(object.getString("type"));
                                foundCard.setSDate(object.getDate("startDate"));
                                foundCard.setEDate(object.getDate("endDate"));
                                founds.add(foundCard);
                            }
                            subscriber.onNext(founds);
                            subscriber.onCompleted();
                        } else subscriber.onError(e);
                    }
                });
            }
        });
    }

    public Observable<FoundCard> getFoundByFid(final String fid) {
        return Observable.create(new Observable.OnSubscribe<FoundCard>() {
            @Override
            public void call(final Subscriber<? super FoundCard> subscriber) {

                final AVObject avFound = AVObject.createWithoutData("Found", fid);
                avFound.fetchInBackground(new GetCallback<AVObject>() {
                    @Override
                    public void done(AVObject avObject, AVException e) {
                        if (e == null) {
                            FoundCard foundCard = new FoundCard();
                            foundCard.setId(avObject.getObjectId());
                            foundCard.setTitle(avObject.getString("title"));
                            foundCard.setName(avObject.getString("name"));
                            foundCard.setType(avObject.getString("type"));
                            foundCard.setSDate(avObject.getDate("startDate"));
                            foundCard.setEDate(avObject.getDate("endDate"));
                            foundCard.setContactWay(avObject.getString("contactWay"));
                            foundCard.setContactDetail(avObject.getString("contactDetail"));
                            foundCard.setDescription(avObject.getString("description"));
                            foundCard.setIsFinish(avObject.getBoolean("isFinish"));

                            AVFile avFile = avObject.getAVFile("FoundPicture");
                            if (avFile != null) {
                                foundCard.setPicUrls(Arrays.asList(avFile.getUrl()));
                            }

                            subscriber.onNext(foundCard);
                            subscriber.onCompleted();
                        } else subscriber.onError(e);
                    }
                });
            }
        });
    }

    public Observable<User> getPickerByFid(final String fid) {

        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                AVObject avFound = AVObject.createWithoutData("Found", fid);
                try {
                    avFound.fetch("picker");
                    User picker = new User();
                    picker.setId(avFound.getAVUser("picker").getObjectId());
                    picker.setUsername(avFound.getAVUser("picker").getUsername());
                    subscriber.onNext(picker);
                    subscriber.onCompleted();
                } catch (AVException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<User> getOwnerByFid(final String fid) {

        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                AVObject avFound = AVObject.createWithoutData("Fpund", fid);
                try {
                    avFound.fetch("owner");
                    User owner = new User();
                    owner.setId(avFound.getAVUser("owner").getObjectId());
                    owner.setUsername(avFound.getAVUser("owner").getUsername());
                    subscriber.onNext(owner);
                    subscriber.onCompleted();
                } catch (AVException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<List<FoundCard>> searchFounds(final String type, final String name,
                                                  final Date lostdate, final Integer pageNumber)
    {
        return Observable.create(new Observable.OnSubscribe<List<FoundCard>>() {
            @Override
            public void call(final Subscriber<? super List<FoundCard>> subscriber) {

                if (type.isEmpty() || type == null) {
                    subscriber.onError(new Throwable("类型为空无法返回"));
                    return;
                }

                AVQuery<AVObject> typeQuery = new AVQuery<AVObject>("Found");
                if (type != null && !type.isEmpty()) { typeQuery.whereEqualTo("type", type); }

                AVQuery<AVObject>  nameQuery = new AVQuery<AVObject>("Found");
                if (name != null && !name.isEmpty()) { nameQuery.whereContains("name", name); }

                AVQuery<AVObject> dateQuery = new AVQuery<AVObject>("Found");
                if (lostdate != null) { dateQuery.whereGreaterThanOrEqualTo("endDate", lostdate); }

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
                            List<FoundCard> founds = new ArrayList<>();
                            for (AVObject object:list) {
                                FoundCard foundCard = new FoundCard();
                                foundCard.setId(object.getObjectId());
                                foundCard.setTitle(object.getString("title"));
                                foundCard.setName(object.getString("name"));
                                foundCard.setType(object.getString("type"));
                                foundCard.setSDate(object.getDate("startDate"));
                                foundCard.setEDate(object.getDate("endDate"));
                                founds.add(foundCard);
                            }
                            subscriber.onNext(founds);
                            subscriber.onCompleted();
                        } else subscriber.onError(e);
                    }
                });

            }
        });
    }
}
