package com.infosmart.registerSmartag.object;

import org.json.JSONException;
import org.json.JSONObject;

public class Worker extends ApiObject{
    private String id;
    private String projectId;
    private String contractorCode;
    private String cardId;
    private String internalCardId;
    private String startDate;
    private String englishName;
    private String chineseName;
    private String workerIdNo;
    private String issueCountry;
    private String contactPhone;
    private String workerIdDocument;
    private String remarks;
    private boolean blacklisted;

    public Worker getObjectFromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        Worker worker = new Worker();

        worker.setId(jsonObject.getString("workerId"));
        worker.setProjectId(jsonObject.getString("projectId"));
        worker.setContractorCode(jsonObject.getString("contractorCode"));
        worker.setCardId(jsonObject.getString("cardId"));
        worker.setInternalCardId(jsonObject.getString("internalCardId"));
        worker.setEnglishName(jsonObject.getString("englishName"));
        worker.setChineseName(jsonObject.getString("chineseName"));
        worker.setStartDate(jsonObject.getString("startDate"));
        worker.setWorkerIdNo(jsonObject.getString("workerIdNo"));
        worker.setIssueCountry(jsonObject.getString("issueCountry"));
        worker.setContactPhone(jsonObject.getString("contactPhone"));
        worker.setWorkerIdDocument(jsonObject.getString("workerIdDocument"));
        worker.setRemarks(jsonObject.getString("remarks"));
        worker.setBlacklisted(jsonObject.getBoolean("blacklisted"));

        return worker;
    }


    public Worker() {
        super("workers");
    }

    @Override
    public boolean equals(Object obj) {
        boolean retVal = false;

        if (obj instanceof Worker){
            Worker worker = (Worker) obj;
            retVal = worker.workerIdNo.equals(this.workerIdNo);
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getContractorCode() {
        return contractorCode;
    }

    public void setContractorCode(String contractorCode) {
        this.contractorCode = contractorCode;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getInternalCardId() {
        return internalCardId;
    }

    public void setInternalCardId(String internalCardId) {
        this.internalCardId = internalCardId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getWorkerIdNo() {
        return workerIdNo;
    }

    public void setWorkerIdNo(String workerIdNo) {
        this.workerIdNo = workerIdNo;
    }

    public String getIssueCountry() {
        return issueCountry;
    }

    public void setIssueCountry(String issueCountry) {
        this.issueCountry = issueCountry;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getWorkerIdDocument() {
        return workerIdDocument;
    }

    public void setWorkerIdDocument(String workerIdDocument) {
        this.workerIdDocument = workerIdDocument;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isBlacklisted() {
        return blacklisted;
    }

    public void setBlacklisted(boolean blacklisted) {
        this.blacklisted = blacklisted;
    }


}
