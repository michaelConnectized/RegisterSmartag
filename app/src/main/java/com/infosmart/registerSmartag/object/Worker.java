package com.infosmart.registerSmartag.object;

import org.json.JSONException;
import org.json.JSONObject;

public class Worker extends ApiObject{
    private String id;
    private String englishName;
    private String chineseName;

    public Worker getObjectFromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        Worker worker = new Worker();
        worker.setId(jsonObject.getString("workerIdNo"));
        worker.setEnglishName(jsonObject.getString("englishName"));
        worker.setChineseName(jsonObject.getString("chineseName"));
        return worker;
    }


    public Worker() {
        super("Worker");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    @Override
    public boolean equals(Object obj) {
        boolean retVal = false;

        if (obj instanceof Worker){
            Worker worker = (Worker) obj;
            retVal = worker.id.equals(this.id);
        }

        return retVal;
    }

    public String toString() {
        String result = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("englishName", englishName);
            result = jsonObject.toString();
        } catch (JSONException e) {
            result = "";
        }
        return result;
    }
}
