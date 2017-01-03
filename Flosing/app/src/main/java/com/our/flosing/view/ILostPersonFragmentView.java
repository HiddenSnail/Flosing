package com.our.flosing.view;

import com.our.flosing.bean.LostCard;

import java.util.List;

/**
 * Created by RunNishino on 2017/1/3.
 */

public interface ILostPersonFragmentView extends IBaseView {
    void updateView();  //指activity之间的跳转
    void refreshView(List<LostCard> lostCards);
    void showError(String msg);
}
