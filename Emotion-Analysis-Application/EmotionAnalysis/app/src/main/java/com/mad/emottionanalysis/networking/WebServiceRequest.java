
package com.mad.emottionanalysis.networking;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class WebServiceRequest {

    private static final String headerKey = "ocp-apim-subscription-key";
    private HttpURLConnection mConn = null;
    private String subscriptionKey;
    private Gson gson = new Gson();

    /**
     * this method is a constructor method
     *
     * @param key subscription key
     * @throws EmotionServiceException service exception
     */
    public WebServiceRequest(String key) throws EmotionServiceException {
        this.subscriptionKey = key;
    }

    /**
     * this method is used to call by outside
     *
     * @param url                 URL link
     * @param data                request body data
     * @param contentType         request header
     * @param responseInputStream default false
     * @return json
     * @throws EmotionServiceException service exception
     * @throws IOException             IO exception
     */
    public Object post(URL url, byte[] data, String contentType, boolean responseInputStream) throws EmotionServiceException, IOException {
        return webInvoke("POST", url, data, contentType, responseInputStream);
    }

    /**
     * this method will connect server forrequest
     *
     * @param method              requet type
     * @param url                 URL link
     * @param data                request body data
     * @param contentType         request header
     * @param responseInputStream default false
     * @return json
     * @throws EmotionServiceException service exception
     * @throws IOException             IO exception
     */
    private Object webInvoke(String method, URL url, byte[] data, String contentType, boolean responseInputStream) throws EmotionServiceException, IOException {

        mConn = (HttpURLConnection) url.openConnection();
        mConn.setRequestMethod("POST");
        mConn.setDoOutput(true);
        mConn.setDoInput(true);
        mConn.setUseCaches(false);

        boolean isStream = false;

        /*Set header*/
        if (contentType != null && !contentType.isEmpty()) {
            // request.setHeader("Content-Type", contentType);
            mConn.setRequestProperty("Content-Type", contentType);
            if (contentType.toLowerCase(Locale.ENGLISH).contains("octet-stream")) {
                isStream = true;
            }
        }

        mConn.setRequestProperty(headerKey, this.subscriptionKey);
        try {
            if (isStream) {
                OutputStream outputStream = mConn.getOutputStream();
                outputStream.write(data);
                outputStream.flush();
                outputStream.close();
            }
            int responseCode = mConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = mConn.getInputStream();
                return readInput(inputStream);
            } else {
                throw new Exception("Error executing POST request! Received error code: " + responseCode);
            }
        } catch (Exception e)
        {
            throw new EmotionServiceException(e.getMessage());
        }
    }

    /**
     * this method is used to decode the input stream to json string
     *
     * @param is input stream
     * @return json String
     * @throws IOException IO exception
     */
    private String readInput(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer json = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            json.append(line);
        }
        return json.toString();
    }

}
