package com.our.flosing.presenter;

import java.util.Date;

/**
 * Created by huangrui on 2017/1/2.
 */

public interface ILostSearchPresenter extends IBasePresenter {
    void searchLosts(final String type, final String name, final Date pickdate, final Integer pageNumber );
}
