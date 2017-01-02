package com.our.flosing.view;

import com.our.flosing.bean.FoundCard;
import com.our.flosing.bean.LostCard;

import java.util.List;

/**
 * Created by RunNishino on 2017/1/2.
 */

public interface IFoundSearchResultFragmentView extends IBaseView {
    void updateView();  //指activity之间的跳转
    void refreshView(List<FoundCard> foundCards);
    void showError(String msg);
}
