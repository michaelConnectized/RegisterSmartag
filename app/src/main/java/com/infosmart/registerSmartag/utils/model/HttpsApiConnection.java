package com.infosmart.registerSmartag.utils.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class HttpsApiConnection extends ApiConnection{
    private final String tag = "HttpApiConnection";

    public HttpsApiConnection(String fullUrl, String postData, String method, Map<String, String> headers) {
        super(fullUrl, postData, method, headers);
    }

    public String connect(String postData) {
        StringBuilder response = new StringBuilder("");
        try {
            URL url = new URL( fullUrl );
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                urlConnection.setRequestProperty("Content-Type", "application/json");
                if (!postData.equals("")) {
                    urlConnection.setRequestMethod(method);
                    urlConnection.setFixedLengthStreamingMode(postData.getBytes().length);
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os, "UTF-8"));
                    writer.write(postData);
                    writer.flush();
                    writer.close();
                    os.close();
                }
                int responseCode=urlConnection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response.append(line);
                    }
                } else {
                    Log.e(tag, "ResponseCode: "+responseCode);
                }
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e(tag, e.toString());
        }
        return response.toString();
    }
}
