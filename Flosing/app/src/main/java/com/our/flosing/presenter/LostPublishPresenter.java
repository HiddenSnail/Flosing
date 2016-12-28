package com.our.flosing.presenter;

import com.our.flosing.bean.LostCard;
import com.our.flosing.bean.User;
import com.our.flosing.model.UserModel;
import com.our.flosing.view.ILostPublishView;

/**
 * Created by huangrui on 2016/12/28.
 */

public class LostPublishPresenter implements ILostPublishPresenter {
    private ILostPublishView lostPublishView;
    private UserModel userModel;

    public LostPublishPresenter(ILostPublishView lostPublishView){
        this.lostPublishView = lostPublishView;
        userModel = new UserModel();
    }

    @Override
    public void publishLost(LostCard lostCard){

    }
}
