package com.our.flosing.qrcode;

import android.util.Log;

import com.our.flosing.model.UserModel;
import com.our.flosing.view.IBaseView;
import com.our.flosing.view.MainActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by huangrui on 2017/1/4.
 */

public class FindLostHandler {
    private UserModel userModel;
    private IBaseView baseView;

    public FindLostHandler(IBaseView baseView) {
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
            Log.e("ID报错","该id无法解析");
            ((MainActivity)baseView).onFailue("操作失败,id格式无法解析");
        }
    }

    private void findLost(String lid) {
        userModel.findLost(lid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        ((MainActivity)baseView).onSuccess("您已成功找回丢失物品!");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        ((MainActivity)baseView).onFailue("操作失败,id错误");
                 }
                });
    }

    private void giveFound(String fid) {
        userModel.giveFound(fid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        ((MainActivity)baseView).onSuccess("您已成功将失物归还失主!");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        ((MainActivity)baseView).onFailue("操作失败,id错误");
                    }
                });
    }

}
