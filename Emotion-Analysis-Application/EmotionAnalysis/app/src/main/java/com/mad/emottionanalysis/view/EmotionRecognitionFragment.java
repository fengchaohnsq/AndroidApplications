package com.mad.emottionanalysis.view;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.emottionanalysis.R;
import com.mad.emottionanalysis.contracts.EmotionRecognitionContract;
import com.mad.emottionanalysis.activity.EmotionAnalysisResultActivity;
import com.mad.emottionanalysis.activity.MainActivity;

import static android.graphics.BitmapFactory.decodeFile;

public class EmotionRecognitionFragment extends Fragment implements EmotionRecognitionContract.View {
    ImageView mSelectedImageView;
    TextView mCancel;
    TextView mSubmit;
    Intent mIntent;
    Bitmap mBitmap;
    String mPath = "empty";

    public EmotionRecognitionFragment() {
        // Requires empty public constructor
    }

    @NonNull
    public static EmotionRecognitionFragment newInstance() {
        return new EmotionRecognitionFragment();
    }

    @Override
    public void setPresenter(EmotionRecognitionContract.Presenter presenter) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_emotion_recognition, container, false);

        mSelectedImageView = (ImageView) root.findViewById(R.id.imageViewSelected);
        mIntent = getActivity().getIntent();
        Log.d("fengchao", "onCreateView: "+mIntent);
        if (mIntent.getExtras().getInt("type") == MainActivity.TAKE_PHOTO) {
            mBitmap = mIntent.getExtras().getParcelable("bitmap");
            mSelectedImageView.setImageBitmap(mBitmap);
        }
        if (mIntent.getExtras().getInt("type") == MainActivity.SELECTED_PHOTO) {
            mPath = mIntent.getExtras().getString("path");
            mBitmap = decodeFile(mPath);
            mSelectedImageView.setImageBitmap(mBitmap);
        }

        // this code will cancel analysis and back to main page
        mCancel = (TextView) root.findViewById(R.id.textViewCancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        mSubmit = (TextView) root.findViewById(R.id.textViewSubmit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EmotionAnalysisResultActivity.class);
                if (mPath != "empty") {
                    intent.putExtra("path", mPath);
                } else {
                    intent.putExtra("bitmap", mBitmap);
                }
                startActivity(intent);
            }
        });
        return root;
    }

}
