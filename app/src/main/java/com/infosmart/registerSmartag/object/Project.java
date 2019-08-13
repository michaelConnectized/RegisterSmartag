package com.infosmart.registerSmartag.object;

import org.json.JSONException;
import org.json.JSONObject;

public class Project extends ApiObject {
    private String projectId;
    private String titleChi;
    private String titleEng;

    public Project getObjectFromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        Project project = new Project();

        project.setProjectId(jsonObject.getString("projectId"));
        project.setTitleChi(jsonObject.getString("titleChi"));
        project.setTitleEng(jsonObject.getString("titleEng"));

        return project;
    }

    public Project() {
        super("projects");
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTitleChi() {
        return titleChi;
    }

    public void setTitleChi(String titleChi) {
        this.titleChi = titleChi;
    }

    public String getTitleEng() {
        return titleEng;
    }

    public void setTitleEng(String titleEng) {
        this.titleEng = titleEng;
    }
}
