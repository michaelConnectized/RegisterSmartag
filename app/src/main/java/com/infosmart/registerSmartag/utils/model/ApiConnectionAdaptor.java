package com.infosmart.registerSmartag.utils.model;

import android.content.res.Resources;
import android.util.Log;

import com.infosmart.registerSmartag.R;
import com.infosmart.registerSmartag.object.ApiObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiConnectionAdaptor {
    private static final String tag = "ApiConnectionAdaptor";

    protected Resources res;
    private boolean isHttps;

    public ApiConnectionAdaptor(Resources res) {
        this.res = res;
        isHttps = res.getBoolean(R.bool.is_https);
    }

    protected List<ApiObject> tryJsonToApiObjectList(String apiJsonName, String json, ApiObject objClass) {
        try {
            return jsonToApiObjectList(apiJsonName, json, objClass);
        } catch (JSONException e) {
            Log.e(tag, e.toString());
            return new ArrayList<>();
        }
    }

    protected List<ApiObject> jsonToApiObjectList(String apiJsonName, String json, ApiObject objClass) throws JSONException {
        List<ApiObject> apiObjectList = new ArrayList<>();;
        if (isSuccess(json)) {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray apiObjectsJson = jsonObject.getJSONArray(apiJsonName);
            for (int i=0; i<apiObjectsJson.length(); i++) {
                apiObjectList.add(objClass.getObjectFromJson(apiObjectsJson.get(i).toString()));
            }
        }
        return apiObjectList;
    }

    protected String getJsonData(Map<String, Object> data) {
        JSONObject postData = new JSONObject();
        try {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                postData.put(entry.getKey(), entry.getValue());
            }
        } catch (JSONException e) {
            Log.e(tag, "getJsonData: "+e.toString());
        }
        Log.e(tag, "Post Data: "+postData.toString());
        return postData.toString();
    }

    protected String getUrlEncodedData(Map<String, Object> data) {
        String postData = "";
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            postData += "&"+entry.getKey()+"="+entry.getValue();
        }
        if (postData.length()>0) {
            postData = postData.substring(1);
        }
        Log.e(tag, "Post Data: "+postData);
        return postData;
    }

    protected boolean isSuccess(String json) {
        boolean success = false;
        try {
            JSONObject jsonObject = new JSONObject(json);
            if ( (jsonObject.has("result")) && (jsonObject.getString("result").equals("success"))) {
                success = true;
            }
        } catch (Exception e) {
            Log.e(tag, e.toString());
        }
        return success;
    }

    protected String getValue(String json, String key) {
        String value = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has(key)) {
                value = jsonObject.getString(key);
            }
        } catch (Exception e) {
            Log.e(tag, e.toString());
        }
        return value;
    }

    protected List<ApiObject> tryGetObjectFromJsonWithoutName(String json, ApiObject objClass) {
        try {
            return getObjectFromJsonWithoutName(json, objClass);
        } catch (JSONException e) {
            return new ArrayList<>();
        }
    }

    protected List<ApiObject> getObjectFromJsonWithoutName(String json, ApiObject objClass) throws JSONException {
        List<ApiObject> apiObjectList = new ArrayList<>();;

        JSONArray apiObjectsJson = new JSONArray(json);
        for (int i=0; i<apiObjectsJson.length(); i++) {
            apiObjectList.add(objClass.getObjectFromJson(apiObjectsJson.get(i).toString()));
        }

        return apiObjectList;
    }


    protected String tryExecuteAndGetFromServer(String fullUrl, String postData) {
        return tryExecuteAndGetFromServer(fullUrl, postData, "POST", new HashMap<String, String>());
    }

    protected String tryExecuteAndGetFromServer(String fullUrl, String postData, Map<String, String> headers) {
        return tryExecuteAndGetFromServer(fullUrl, postData, "POST", headers);
    }

    protected String tryExecuteAndGetFromServer(String fullUrl, String postData, String method, Map<String, String> headers) {
        String response;
        try {
            response = getApiConnection(fullUrl, postData, method, headers).execute().get();
            Log.e(tag, "tryExecuteAndGetFromServer: "+response);
        } catch (Exception e) {
            Log.e(tag, "tryExecuteAndGetFromServer: "+e.toString());
            response = "";
        }
        return response;
    }

    private ApiConnection getApiConnection(String fullUrl, String postData, String method, Map<String, String> headers) {
        ApiConnection apiConnection;
        if (isHttps)
            apiConnection = new HttpsApiConnection(fullUrl, postData, method, headers);
        else
            apiConnection = new HttpApiConnection(fullUrl, postData, method, headers);
        return apiConnection;
    }
}
