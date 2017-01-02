package com.our.flosing.model;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;
import com.our.flosing.bean.User;

import java.io.File;
import java.io.IOException;
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
                }
            }
        });
    }

//    public Observable<Boolean> uploadAvatar() {
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                try {
//                    AVFile avatarFile = AVFile.withAbsoluteLocalPath("test.png",  Environment.getExternalStorageDirectory() + "/test.png");
//
//
//
//                } catch (IOException e) {
//                    subscriber.onError(e);
//                }
//            }
//        });
//    }

    public void uploadAvatar() {
        try {
//            AVFile avatarFile = AVFile.withAbsoluteLocalPath("test.png",
//                    Environment.getExternalStorageDirectory() + "/test.png");
            File files = Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM);
            files.createNewFile();

            File[] fileList = files.listFiles();

//            File ss = new File(image.getPath(), "/jj.txt");
            Log.e("路径为", files.getPath());
//            System.out.println("image是文件么？:" + image.isDirectory());
//            Log.e("长度为", files.toURI().toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
