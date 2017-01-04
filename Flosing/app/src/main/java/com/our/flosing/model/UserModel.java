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
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;
import com.our.flosing.bean.FoundCard;
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
    static private final Integer EPN = 10;

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
                }
            }
        });
    }

    public Observable<List<LostCard>> getUserLost(final Integer pageNumber) {
        return Observable.create(new Observable.OnSubscribe<List<LostCard>>() {
            @Override
            public void call(final Subscriber<? super List<LostCard>> subscriber) {
                AVUser avUser = AVUser.getCurrentUser();
                if (avUser != null) {
                    AVQuery<AVObject> query = new AVQuery<AVObject>("Lost");
                    query.whereEqualTo("owner", avUser);
                    query.limit(EPN).skip((pageNumber-1)*EPN);
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

    public Observable<List<FoundCard>> getUserFound(final Integer pageNumber) {
        return Observable.create(new Observable.OnSubscribe<List<FoundCard>>() {
            @Override
            public void call(final Subscriber<? super List<FoundCard>> subscriber) {
                AVUser avUser = AVUser.getCurrentUser();
                if (avUser == null) {
                    subscriber.onError(new Throwable("用户未登陆"));
                }
                else{
                    AVQuery<AVObject> query = new AVQuery<AVObject>("Found");
                    query.whereEqualTo("picker", avUser);
                    query.limit(EPN).skip((pageNumber-1));
                    query.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            if (list.size() > 0) {
                                List<FoundCard> founds = new ArrayList<>();
                                for (AVObject avObject:list) {
                                    FoundCard foundCard = new FoundCard();
                                    foundCard.setId(avObject.getObjectId());
                                    foundCard.setTitle(avObject.getString("title"));
                                    foundCard.setName(avObject.getString("name"));
                                    foundCard.setType(avObject.getString("type"));
                                    foundCard.setSDate(avObject.getDate("startDate"));
                                    foundCard.setEDate(avObject.getDate("endDate"));
                                    foundCard.setIsFinish(avObject.getBoolean("isFinish"));
                                    founds.add(foundCard);
                                }
                                subscriber.onNext(founds);
                                subscriber.onCompleted();
                            } else subscriber.onError(e);
                        }
                    });
                }
            }
        });
    }


    /**
     * 方法说明：寻物启示找到东西（Lost结帖）
     * @param lid
     * @return
     */
    public Observable<Boolean> findLost(final String lid) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                final AVUser picker = AVUser.getCurrentUser();
                if (picker == null) subscriber.onError(new Throwable("用户未登陆"));
                AVObject avLost = AVObject.createWithoutData("Lost", lid);
                try {
                    avLost.fetch();
                    avLost.put("isFinish", true);
                    avLost.put("picker", picker);
                    avLost.save();
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } catch (AVException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 方法说明：失物招领找到主人（Found结帖）
     * @param fid
     * @return
     */
    public Observable<Boolean> giveFound(final String fid) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                final AVUser owner = AVUser.getCurrentUser();
                if (owner == null) subscriber.onError(new Throwable("用户未登陆"));
                AVObject avFound = AVObject.createWithoutData("Found", fid);
                try {
                    avFound.fetch();
                    avFound.put("isFinish", true);
                    avFound.put("owner", owner);
                    avFound.save();
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } catch (AVException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
