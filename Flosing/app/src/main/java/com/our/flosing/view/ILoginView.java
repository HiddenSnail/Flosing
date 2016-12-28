package com.our.flosing.view;
import android.app.FragmentManager;

import com.our.flosing.bean.User;

/**
 * Created by huangrui on 2016/12/27.
 */

public interface ILoginView extends BaseView {
    void updateView();
    void showError(String msg);
    void showProgressDialog();
    void hideProgressDialog();
}
