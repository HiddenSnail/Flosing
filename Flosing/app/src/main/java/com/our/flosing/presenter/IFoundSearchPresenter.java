package com.our.flosing.presenter;

import java.util.Date;

/**
 * Created by huangrui on 2017/1/2.
 */

public interface IFoundSearchPresenter extends IBasePresenter {
    void searchFounds(final String type, final String name, final Date lostdate, final Integer pageNumber);
}
