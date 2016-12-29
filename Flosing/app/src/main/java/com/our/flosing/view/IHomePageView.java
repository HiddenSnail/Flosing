package com.our.flosing.view;

import com.our.flosing.bean.LostCard;

import java.util.List;

/**
 * Created by huangrui on 2016/12/29.
 */

public interface IHomePageView extends IBaseView {
    void updateView(List<LostCard> lostCards);
    void showError(String msg);
//    void showProgressDialog();
//    void hideProgressDialog();
}
