package com.mad.emottionanalysis.contracts;

import com.mad.emottionanalysis.presenter.BasePresenter;
import com.mad.emottionanalysis.view.BaseView;

public interface MainActivityContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
