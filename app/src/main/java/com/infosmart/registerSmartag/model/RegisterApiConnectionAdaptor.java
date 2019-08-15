package com.infosmart.registerSmartag.model;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.Nullable;

import com.infosmart.registerSmartag.R;
import com.infosmart.registerSmartag.object.ApiObject;
import com.infosmart.registerSmartag.object.Project;
import com.infosmart.registerSmartag.object.Worker;
import com.infosmart.registerSmartag.utils.Utils;
import com.infosmart.registerSmartag.utils.model.ApiConnectionAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RegisterApiConnectionAdaptor extends ApiConnectionAdaptor {
    private final String tag = "RegisterApiConnectAdap";
    private String baseUrl = "";
    private boolean isTestingServer;

    public RegisterApiConnectionAdaptor(Activity activity) {
        super(activity.getResources());
        Utils.setActivity(activity);

        isTestingServer = false;
        if (isTestingServer) {
            baseUrl = "http://test.infotronic-int.com/attend";
        } else {
            baseUrl = "http://app.infotronic-int.com/attend";
        }
    }

    public List<Worker> getWorkerList(String projectId) {
        String token = getLoginToken();
        if (!token.equals("")) {
            String url = baseUrl+"/publicApi/worker?projectId="+projectId;
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer "+token);
            Log.e("test", url);
            String resultJson = tryExecuteAndGetFromServer(url, "", "GET", headers);
            return (List<Worker>)(List<?>)tryJsonToApiObjectList("workers", resultJson, new Worker());
        } else {
            return new ArrayList<>();
        }
    }

    public List<Project> getProjectList() {
        String token = getLoginToken();
        if (!token.equals("")) {
            String url = baseUrl+"/publicApi/project";
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", "Bearer "+token);
            String resultJson = tryExecuteAndGetFromServer(url, "", "GET", headers);
            Log.e("test", resultJson);
            return (List<Project>)(List<?>)tryJsonToApiObjectList("projects", resultJson, new Project());
        } else {
            return new ArrayList<>();
        }
    }

    public boolean setWorkerCardId(String workerId, String cardId, Worker worker) {
        String token = getLoginToken();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer "+token);
        headers.put("Content-Type", "application/json");
        String resultJson = tryExecuteAndGetFromServer(baseUrl+"/publicApi/worker", getCardIdUpdateJsonData(workerId, cardId, worker), "PUT", headers);
        return isSuccess(resultJson);
    }

    public boolean registerHelmet(String workerCardId, String helmetId) {
        String resultJson = tryExecuteAndGetFromServer(baseUrl+"/api/helmetRegistration", getHelmetJsonPostData(workerCardId, helmetId));
        if (isSuccess(resultJson)) {
            return true;
        } else {
            Log.e("registerHelmet", resultJson);
            return false;
        }
    }

    public String getLoginToken() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Cache-Control", "no-cache");
        headers.put("Authorization", "Basic  YXR0ZW5kLWFwaTphdHRlbmQtc2VjcmV0");
        headers.put("Accept", "application/json");
        String resultJson = tryExecuteAndGetFromServer(baseUrl+"/publicApi/oauth/token", getLoginTokenPostData(), headers);
        return getValue(resultJson, "access_token");
    }


    private String getLoginTokenPostData() {
        Map<String, Object> loginData = new HashMap<>();
        loginData.put("scope", "api");
        loginData.put("grant_type", "password");
        loginData.put("password", "a77@min");
        loginData.put("username", "admin");
        return getUrlEncodedData(loginData);
    }

    private String getHelmetJsonPostData(String workerCardId, String helmetId) {
        Map<String, Object> helmetData = new HashMap<>();
        helmetData.put("projectId", Utils.getSharedPreferences().getString("projectId", res.getString(R.string.project_id)));
        helmetData.put("workerCardId", workerCardId);
        helmetData.put("helmetId", helmetId);
        helmetData.put("forceUpdate",true);
        return getJsonData(helmetData);
    }

    private String getCardIdUpdateJsonData(String workerId, String cardId, Worker worker) {
        Map<String, Object> cardData = new HashMap<>();
        cardData.put("workerId", workerId);
        cardData.put("cardId", cardId);

        cardData.put("contractorCode", worker.getContractorCode());
        cardData.put("startDate", worker.getStartDate());
        cardData.put("englishName", worker.getEnglishName());
        cardData.put("chineseName", worker.getChineseName());
        cardData.put("workerIdNo", worker.getWorkerIdNo());
        cardData.put("issueCountry", worker.getIssueCountry());
        cardData.put("contactPhone", worker.getContactPhone());
        cardData.put("workerIdDocument", worker.getWorkerIdDocument());
        cardData.put("remarks", worker.getRemarks());
        cardData.put("blacklisted", worker.isBlacklisted());

        return getJsonData(cardData);
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
