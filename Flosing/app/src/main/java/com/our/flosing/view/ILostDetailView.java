package com.our.flosing.view;

import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.User;

/**
 * Created by huangrui on 2016/12/31.
 */

public interface ILostDetailView extends IBaseView {
    void initLostDetail(LostCard lostCard);
    void initOwnerInfo(User owner);
    void showError(String msg);
}
