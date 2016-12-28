package com.our.flosing.model;

import android.content.Intent;

import com.our.flosing.bean.User;

import rx.Observable;

/**
 * Created by huangrui on 2016/12/27.
 */

public interface IUserModel {
    Observable<Boolean> register(final String name, final String email, final String password);
    Observable<Boolean> login(final String username, final String password);
}
