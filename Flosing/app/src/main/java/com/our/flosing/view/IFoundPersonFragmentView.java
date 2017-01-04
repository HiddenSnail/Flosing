package com.our.flosing.view;


import com.our.flosing.bean.FoundCard;
import com.our.flosing.bean.LostCard;

import java.util.List;

/**
 * Created by huangrui on 2017/1/4.
 */

public interface IFoundPersonFragmentView extends IBaseView {
    void updateView();

    void refreshView(List<FoundCard> foundCards);

    void showError(String msg);

}
