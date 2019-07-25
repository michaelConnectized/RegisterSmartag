package com.infosmart.registerSmartag.model;

import android.content.res.Resources;
import android.util.Log;

import com.infosmart.registerSmartag.R;
import com.infosmart.registerSmartag.object.ApiObject;
import com.infosmart.registerSmartag.object.Worker;
import com.infosmart.registerSmartag.utils.model.ApiConnectionAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisterApiConnectionAdaptor extends ApiConnectionAdaptor {
    private final String tag = "RegisterApiConnectAdap";

    public RegisterApiConnectionAdaptor(Resources resources) {
        super(resources);
    }

    public List<Worker> getWorkerList(String projectId) {
        String url = "http://192.168.0.54/custom-report/admin/testing_admin/getWorkerRecord?projectId="+projectId;
        String resultJson = tryExecuteAndGetFromServer(url, "");
        Log.e("test", "http://192.168.0.54/custom-report/admin/testing_admin/getWorkerRecord?projectId="+projectId);
        Log.e("test", resultJson);
        List<ApiObject> apiObjectList = tryGetObjectFromJsonWithoutName(resultJson, new Worker());

        return (List<Worker>)(List<?>)apiObjectList;
    }

    public boolean setWorkerCardId(String workerId, String workerCardId) {
        String resultJson = tryExecuteAndGetFromServer("http://192.168.0.54/custom-report/admin/testing_admin/setWorkerCardId?workerId="+workerId+"&workerCardId="+workerCardId+"&projectId="+res.getString(R.string.project_id), "");
        if (Boolean.parseBoolean(resultJson)) {
            Log.e(tag, "Update Successful: "+workerCardId);
        } else {
            Log.e(tag, "Update Fail: "+resultJson);
        }
        return Boolean.parseBoolean(resultJson);
        //test zoner
    }

    public boolean registerHelmet(String workerCardId, String helmetId) {
        String resultJson = tryExecuteAndGetFromServer("http://test.infotronic-int.com/attend/api/helmetRegistration", getHelmetJsonPostData(workerCardId, helmetId));
        if (isSuccess(resultJson)) {
            return true;
        } else {
            Log.e("registerHelmet", resultJson);
            return false;
        }
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

    private String getHelmetJsonPostData(String workerCardId, String helmetId) {
        JSONObject postData = new JSONObject();
        try {
            postData.put("projectId", res.getString(R.string.project_id));
            postData.put("workerCardId", workerCardId);
            postData.put("helmetId", helmetId);
            postData.put("forceUpdate", true);
        } catch (JSONException e) {
            Log.e(tag, "getJsonPostData: "+e.toString());
        }
        Log.e(tag, "Post Data: "+postData.toString());
        return postData.toString();
    }

//    private String getErrorMessageFromJson() {
//        String msg = "";
//        try {
//
//        } catch (JSONException e) {
//
//        }
//        return msg;
//    }
}
