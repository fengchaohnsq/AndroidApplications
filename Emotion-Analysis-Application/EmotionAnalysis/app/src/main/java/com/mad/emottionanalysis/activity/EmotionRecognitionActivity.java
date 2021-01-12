package com.mad.emottionanalysis.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.mad.emottionanalysis.R;
import com.mad.emottionanalysis.view.EmotionRecognitionFragment;

public class EmotionRecognitionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_recognition);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                // this will start the Fragment
                EmotionRecognitionFragment emotionRecognitionFragment;
                emotionRecognitionFragment = EmotionRecognitionFragment.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.emotion_recognition_activity_frame, emotionRecognitionFragment);
                transaction.commit();
            }
        });

    }


}
