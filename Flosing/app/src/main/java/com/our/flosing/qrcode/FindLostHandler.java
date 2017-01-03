package com.our.flosing.qrcode;

import android.util.Log;

import com.our.flosing.model.UserModel;

import rx.functions.Action1;

/**
 * Created by huangrui on 2017/1/4.
 */

public class FindLostHandler {
    private UserModel userModel;

    public FindLostHandler() {
        this.userModel = new UserModel();
    }

    public void findLost(final String lid) {
        userModel.findLost(lid)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                 }
                });
    }

}
