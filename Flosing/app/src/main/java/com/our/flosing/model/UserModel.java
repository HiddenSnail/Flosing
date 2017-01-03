package com.our.flosing.model;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;
import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

import static android.os.Environment.DIRECTORY_DCIM;

/**
 * Created by huangrui on 2016/12/27.
 */

public class UserModel implements IUserModel {

    public Observable<Boolean> login(final String username, final String password) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber subscriber) {
                AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (e == null) {
                            subscriber.onNext(true);
                            subscriber.onCompleted();
                        } else subscriber.onError(e);
                    }
                });
            }
        });
    }

    public Observable<Boolean> register(final String username, final String email, final String password) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final  Subscriber subscriber) {
                AVUser user = new AVUser();
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
                user.saveInBackground(new SaveCallback() {
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

    public Observable<User> getCurrentUser() {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                AVUser avUser = AVUser.getCurrentUser();
                if (avUser != null) {
                    User user = new User();
                    user.setId(avUser.getObjectId());
                    user.setEmail(avUser.getEmail());
                    user.setUsername(avUser.getUsername());

                    subscriber.onNext(user);
                    subscriber.onCompleted();
                } else subscriber.onError(new Throwable("用户未登陆"));
            }
        });
    }

    public Observable<List<LostCard>> getUserLost() {
        return Observable.create(new Observable.OnSubscribe<List<LostCard>>() {
            @Override
            public void call(final Subscriber<? super List<LostCard>> subscriber) {
                AVUser avUser = AVUser.getCurrentUser();
                if (avUser != null) {
                    AVQuery<AVObject> query = new AVQuery<AVObject>("Lost");
                    query.whereEqualTo("owner", avUser);
                    query.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            if (list.size() > 0) {
                                List<LostCard> losts = new ArrayList<>();
                                for (AVObject avObject:list) {
                                    LostCard lostCard = new LostCard();
                                    lostCard.setId(avObject.getObjectId());
                                    lostCard.setTitle(avObject.getString("title"));
                                    lostCard.setName(avObject.getString("name"));
                                    lostCard.setType(avObject.getString("type"));
                                    lostCard.setSDate(avObject.getDate("startDate"));
                                    lostCard.setEDate(avObject.getDate("endDate"));
                                    lostCard.setIsFinish(avObject.getBoolean("isFinish"));
                                    losts.add(lostCard);
                                }
                                subscriber.onNext(losts);
                                subscriber.onCompleted();
                            } else subscriber.onError(e);
                        }
                    });
                } else subscriber.onError(new Throwable("用户未登陆"));
            }
        });
    }
    //PersonLost
}
