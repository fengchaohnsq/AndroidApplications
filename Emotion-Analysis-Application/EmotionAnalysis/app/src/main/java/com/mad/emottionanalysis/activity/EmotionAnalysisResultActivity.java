

package com.mad.emottionanalysis.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.MessageFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mad.emottionanalysis.R;
import com.mad.emottionanalysis.model.RecognizeResult;
import com.mad.emottionanalysis.networking.EmotionServiceClient;
import com.mad.emottionanalysis.networking.EmotionServiceException;
import com.mad.emottionanalysis.networking.EmotionServiceRestClient;
import com.mad.emottionanalysis.utility.ImageHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.graphics.BitmapFactory.decodeFile;

public class EmotionAnalysisResultActivity extends AppCompatActivity {
    TextView mTextView;
    ImageView mImageView;
    Bitmap mBitmap;
    Intent mIntent;
    EmotionServiceClient mClient;
    String mPath = null;
    RecognizeResult[] mResult = null;
    String mAnalysis = null;
    public static final String SUB_KEY = "5deda4bf4c7246e0bc5d6b2d4b9e3ad5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_analysis_result);
        mIntent = getIntent();
        mPath = mIntent.getExtras().getString("path");
        if (mPath != null) {
            mBitmap = decodeFile(mPath);
        } else {
            mBitmap = mIntent.getExtras().getParcelable("bitmap");
        }
        if (mClient == null) {
            try {
                mClient = new EmotionServiceRestClient(SUB_KEY);
            } catch (EmotionServiceException e) {
                e.printStackTrace();
            }
        }

        mImageView = (ImageView) findViewById(R.id.imageViewAnalysis);


        mTextView = (TextView) findViewById(R.id.textViewResult);
        mTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
        try {
            new EmotionAnalysis().execute();
        } catch (Exception e) {
            mTextView.setText(getResources().getString(R.string.analysis_fail));
        }


        Button mainPageBtn = (Button) findViewById(R.id.main_page_btn);
        mainPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionAnalysisResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    /**
     * this method is used to do the emotion analysis
     *
     * @return it will return the analysis result
     * @throws EmotionServiceException if the server throws any errors
     * @throws IOException             if the IO throws any errors
     */
    private RecognizeResult[] startEmotionAnalysis() throws EmotionServiceException, IOException {

        Gson gson = new Gson();

        // use input stream to pass image for analysis
        ByteArrayOutputStream analysisResult = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, analysisResult);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(analysisResult.toByteArray());

        RecognizeResult[] result = null;
        result = mClient.recognizeImage(inputStream);
        String json = gson.toJson(result);
        Log.d("fengchao", json);
        return result;
    }

    /**
     * this method handle the result array to a string
     *
     * @return a string
     */
    private String handleResult() {
        StringBuilder result = new StringBuilder();
        int i = 1;
        String face = getResources().getString(R.string.face);
        for (RecognizeResult aMResult : mResult) {
            result.append(MessageFormat.format(face, i)).append(getResources().getString(R.string.new_line));
            result.append(getResources().getString(R.string.gender)).append(aMResult.faceAttributes.gender).append(getResources().getString(R.string.new_line));
            result.append(getResources().getString(R.string.age)).append(aMResult.faceAttributes.age).append(getResources().getString(R.string.new_line));
            result.append(getResources().getString(R.string.emotion)).append(getResources().getString(R.string.new_line));
            result.append(getResources().getString(R.string.anger)).append(aMResult.faceAttributes.emotion.anger).append(getResources().getString(R.string.new_line));
            result.append(getResources().getString(R.string.contempt)).append(aMResult.faceAttributes.emotion.contempt).append(getResources().getString(R.string.new_line));
            result.append(getResources().getString(R.string.disgust)).append(aMResult.faceAttributes.emotion.disgust).append(getResources().getString(R.string.new_line));
            result.append(getResources().getString(R.string.fear)).append(aMResult.faceAttributes.emotion.fear).append(getResources().getString(R.string.new_line));
            result.append(getResources().getString(R.string.happiness)).append(aMResult.faceAttributes.emotion.happiness).append(getResources().getString(R.string.new_line));
            result.append(getResources().getString(R.string.neutral)).append(aMResult.faceAttributes.emotion.neutral).append(getResources().getString(R.string.new_line));
            result.append(getResources().getString(R.string.sadness)).append(aMResult.faceAttributes.emotion.sadness).append(getResources().getString(R.string.new_line));
            result.append(getResources().getString(R.string.surprise)).append(aMResult.faceAttributes.emotion.surprise).append(getResources().getString(R.string.new_line) + getResources().getString(R.string.new_line));
            i++;
        }

        return result.toString();
    }

    /**
     * this class used for async task to analysis the result and display it
     */
    private class EmotionAnalysis extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog = new ProgressDialog(EmotionAnalysisResultActivity.this);
        String result;
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            pDialog.setIndeterminate(true);
            String temp = getResources().getString(R.string.result_hint);
            String output = temp;
            pDialog.setMessage(output);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                mResult = startEmotionAnalysis();
            } catch (EmotionServiceException | IOException e) {
                e.printStackTrace();
            }
            result = handleResult();
            bitmap= ImageHelper.drawFaceRectanglesOnBitmap(mBitmap,mResult);
            Log.d("fengchao", "handleResult: " + result);
            return result;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            mTextView.setText(s);
            mImageView.setImageBitmap(bitmap);
            mAnalysis = s;
            pDialog.cancel();
        }
    }
}
