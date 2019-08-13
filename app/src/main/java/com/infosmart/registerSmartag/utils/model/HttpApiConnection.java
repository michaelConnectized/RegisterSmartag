package com.infosmart.registerSmartag.utils.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class HttpApiConnection extends ApiConnection {
    private final String tag = "HttpApiConnection";

    public HttpApiConnection(String fullUrl, String postData, String method, Map<String, String> headers) {
        super(fullUrl, postData, method, headers);
    }

    public String connect(String postData) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL( fullUrl );
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                urlConnection.setRequestMethod(method);
                if (headers.isEmpty()) {
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                } else {
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }

                if (!postData.equals("")) {
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
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
                    while ((line=br.readLine()) != null) {
                        response.append(line);
                    }
                    Log.e(tag, "Response: "+response);
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
