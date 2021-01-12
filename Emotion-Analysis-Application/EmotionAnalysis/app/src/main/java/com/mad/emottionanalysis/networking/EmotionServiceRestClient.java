
package com.mad.emottionanalysis.networking;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mad.emottionanalysis.model.FaceRectangle;
import com.mad.emottionanalysis.model.RecognizeResult;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

public class EmotionServiceRestClient implements EmotionServiceClient {
    private static final String DEFAULT_API_ROOT = "https://westus.api.cognitive.microsoft.com/face/v1.0";
    private final String apiRoot;
    private final WebServiceRequest restCall;
    private Gson gson = new Gson();

    public EmotionServiceRestClient(String subscriptionKey) throws EmotionServiceException {
        this(subscriptionKey, DEFAULT_API_ROOT);
    }

    public EmotionServiceRestClient(String subscriptionKey, String apiRoot) throws EmotionServiceException {
        this.apiRoot = apiRoot.replaceAll("/$", "");
        this.restCall = new WebServiceRequest(subscriptionKey);
    }


    @Override
    public RecognizeResult[] recognizeImage(InputStream stream) throws EmotionServiceException, IOException {
        String params="?returnFaceAttributes=age,gender,emotion";
        String path = apiRoot + "/detect"+params;
        Log.d("fengchao", "recognizeImage: "+path);
        URL url = new URL(path);

        byte[] data = IOUtils.toByteArray(stream);

        String json = (String) this.restCall.post(url, data, "application/octet-stream", false);

        Type listType = new TypeToken<List<RecognizeResult>>() {}.getType();
        List<RecognizeResult> recognizeResults =this.gson.fromJson(json, listType);
        Log.d("fengchao", "recognizeImage:result: "+json);
        return recognizeResults.toArray(new RecognizeResult[recognizeResults.size()]);
    }

    @Override
    public List<RecognizeResult> recognizeImage(InputStream inputStream, FaceRectangle[] faceRectangles) throws EmotionServiceException, IOException {
        return null;
    }

}

