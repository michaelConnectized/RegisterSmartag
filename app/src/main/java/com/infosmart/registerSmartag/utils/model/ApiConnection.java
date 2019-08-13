package com.infosmart.registerSmartag.utils.model;

import android.os.AsyncTask;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class ApiConnection extends AsyncTask<URL, Integer, String> {
    protected String fullUrl;
    protected String postData;
    protected String method;
    protected Map<String, String> headers;

    public ApiConnection(String fullUrl, String postData, String method, Map<String, String> headers) {
        super();
        this.fullUrl = fullUrl;
        this.postData = postData;
        this.method = method;
        this.headers = headers;
    }

    protected String doInBackground(URL... urls) {
        return connect(postData);
    }

    public abstract String connect(String postData);

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        this.cancel(true);
    }
}
