package com.mad.emottionanalysis.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.emottionanalysis.R;
import com.mad.emottionanalysis.contracts.EmotionAnalysisResultContract;

import static android.graphics.BitmapFactory.decodeFile;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class EmotionAnalysisResultFragment extends Fragment implements EmotionAnalysisResultContract.View {
    TextView mTextView;
    ImageView mImageView;
    Bitmap mBitmap;
    Intent mIntent;
    String mPath = null;
    private EmotionAnalysisResultContract.Presenter mPresenter;

    public EmotionAnalysisResultFragment() {
        // Requires empty public constructor
    }

    @NonNull
    public static EmotionAnalysisResultFragment newInstance() {
        return new EmotionAnalysisResultFragment();
    }

    @Override
    public void setPresenter(@NonNull EmotionAnalysisResultContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_emotion_analysis_result, container, false);
        //get the image from the former activity
        mIntent = getActivity().getIntent();
        mPath = mIntent.getExtras().getString("path");
        if (mPath != null) {
            mBitmap = decodeFile(mPath);
        } else {
            mBitmap = mIntent.getExtras().getParcelable("bitmap");
        }
        // set the image in the image view
        mImageView = (ImageView) rootView.findViewById(R.id.imageViewAnalysis);
        mImageView.setImageBitmap(mBitmap);
        mTextView = (TextView) rootView.findViewById(R.id.textViewResult);
        // mTextView.setText("hello,again!");
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start(mBitmap);
    }

    @Override
    public void showResultInTextView(String s) {
        Log.d("fengchao", "showResultInTextView: "+s);
        mTextView.setText(s);
    }


}
