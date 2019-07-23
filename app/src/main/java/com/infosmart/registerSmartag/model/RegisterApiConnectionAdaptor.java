package com.infosmart.registerSmartag.model;

import android.content.res.Resources;
import android.util.Log;

import com.infosmart.registerSmartag.object.ApiObject;
import com.infosmart.registerSmartag.object.Worker;
import com.infosmart.registerSmartag.utils.model.ApiConnectionAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisterApiConnectionAdaptor extends ApiConnectionAdaptor {
    public RegisterApiConnectionAdaptor(Resources resources) {
        super(resources);
    }

    public List<Worker> getWorkerList(String projectId) {
        String resultJson = tryExecuteAndGetFromServer("http://192.168.0.54/custom-report/admin/testing_admin/getWorkerRecord?projectId="+projectId, "");
        Log.e("test", "http://192.168.0.54/custom-report/admin/testing_admin/getWorkerRecord?projectId="+projectId);
        Log.e("test", resultJson);
        List<ApiObject> apiObjectList = tryGetObjectFromJsonWithoutName(resultJson, new Worker());

        return (List<Worker>)(List<?>)apiObjectList;
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
}
