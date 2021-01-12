package com.mad.emottionanalysis.presenter;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.mad.emottionanalysis.contracts.EmotionAnalysisResultContract;
import com.mad.emottionanalysis.networking.EmotionServiceClient;
import com.mad.emottionanalysis.networking.EmotionServiceRestClient;
import com.mad.emottionanalysis.networking.EmotionServiceException;
import com.mad.emottionanalysis.model.RecognizeResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EmotionAnalysisResultPresenter implements EmotionAnalysisResultContract.Presenter {
    private EmotionAnalysisResultContract.View mView;
    private RecognizeResult[] mResult = null;
    public static final String SUB_KEY = "5deda4bf4c7246e0bc5d6b2d4b9e3ad5";
    // public String mAnalysisResult;

    public EmotionAnalysisResultPresenter(EmotionAnalysisResultContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start(Bitmap bitmap) {
        new EmotionAnalysis(bitmap).execute();
    }

    @Override
    public void start() {
    }

    /**
     * this class used for async task to analysis the result and display it
     */
    private class EmotionAnalysis extends AsyncTask<Void, Void, String> {
        private Bitmap bitmap;

        EmotionAnalysis(Bitmap image) {
            bitmap = image;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result;
            try {
                mResult = startEmotionAnalysis(bitmap, SUB_KEY);
            } catch (EmotionServiceException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            result = handleResult(mResult);
            return result;
        }

//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("fengchao", "onPostExecute: " + s);
            // mAnalysisResult = s;
            mView.showResultInTextView(s);
        }
    }

//    public String returnResult() {
//        return mAnalysisResult;
//    }

    /**
     * this method is used to do the emotion analysis
     *
     * @return it will return the analysis result
     * @throws EmotionServiceException if the server throws any errors
     * @throws IOException             if the IO throws any errors
     */
    public RecognizeResult[] startEmotionAnalysis(Bitmap bitmap, String key) throws IOException, EmotionServiceException {
        EmotionServiceClient client;
        RecognizeResult[] result = null;
        try {
            client = new EmotionServiceRestClient(key);
            // use input stream to pass image for analysis
            ByteArrayOutputStream analysisResult = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, analysisResult);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(analysisResult.toByteArray());
            // call server for result
            result = client.recognizeImage(inputStream);
        } catch (EmotionServiceException e) {
            e.printStackTrace();
        }

//        Gson gson = new Gson();
//        String json = gson.toJson(result);
//        Log.d("fengchao", json);

        return result;
    }

    /**
     * this method handle the result array to a string
     *
     * @param results array result
     * @return a string
     */
    public String handleResult(RecognizeResult[] results) {
        StringBuilder result = new StringBuilder();
        if (results.length != 0) {
            for (int i = 0; i < results.length; i++) {
                result.append("gender:").append(results[i].faceAttributes.gender).append("\n");
                result.append("age:").append(results[i].faceAttributes.age).append("\n");
                result.append("Emotion: ");
                result.append("anger:").append(results[i].faceAttributes.emotion.anger);
                result.append(",contempt:").append(results[i].faceAttributes.emotion.contempt);
                result.append(",disgust:").append(results[i].faceAttributes.emotion.disgust);
                result.append(",fear:").append(results[i].faceAttributes.emotion.fear);
                result.append(",happiness:").append(results[i].faceAttributes.emotion.happiness);
                result.append(",neutral:").append(results[i].faceAttributes.emotion.neutral);
                result.append(",sadness:").append(results[i].faceAttributes.emotion.sadness);
                result.append(",surprise:").append(results[i].faceAttributes.emotion.surprise).append("\n");
            }
        } else {
            result = new StringBuilder("This image can not be analysis, Please try again!");
        }
        return result.toString();
    }

}
