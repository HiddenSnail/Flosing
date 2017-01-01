package com.our.flosing.view;

import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.User;

/**
 * Created by RunNishino on 2017/1/2.
 */

public interface IFoundDetailView extends IBaseView {
    void initFoundDetail(LostCard lostCard);
    void initOwnerInfo(User owner);
    void showError(String msg);
}
