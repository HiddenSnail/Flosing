package com.our.flosing.qrcode;

import android.util.Log;

import com.our.flosing.model.UserModel;
import com.our.flosing.view.IBaseView;
import com.our.flosing.view.MainActivity;

import rx.functions.Action1;

/**
 * Created by huangrui on 2017/1/4.
 */

public class FindLostHandler {
    private UserModel userModel;
    private IBaseView baseView;

    FindLostHandler(IBaseView baseView) {
        userModel = new UserModel();
        this.baseView = (MainActivity) baseView;
    }

    public FindLostHandler() {
        this.userModel = new UserModel();
    }

    public void handleCode(String id) {
        char head = id.charAt(0);
        String realId = id.substring(1);
        if (head == 'L') {
            findLost(realId);
        } else if(head == 'F') {
            giveFound(realId);
        } else {
            System.out.println("该id无法解析");
        }
    }

    private void findLost(String lid) {
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

    private void giveFound(String fid) {
        userModel.giveFound(fid)
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
