package com.mad.emottionanalysis.contracts;

import android.graphics.Bitmap;

import com.mad.emottionanalysis.presenter.BasePresenter;
import com.mad.emottionanalysis.view.BaseView;

public interface EmotionAnalysisResultContract {

    interface View extends BaseView<Presenter> {
        void showResultInTextView(String s);
    }

    interface Presenter extends BasePresenter {
        void start(Bitmap bitmap);

    }
}
