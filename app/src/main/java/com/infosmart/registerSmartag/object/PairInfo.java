package com.infosmart.registerSmartag.object;

import org.json.JSONObject;

public class PairInfo {

    private static String cardId;
    private static String helmetId;
    private static String workerId;
    private static String hexCardId;
    private static Worker worker;

    private static String title;

    public PairInfo() {

    }

    public static String getCardId() {
        return cardId;
    }

    public static void setCardId(String cardId) {
        PairInfo.cardId = cardId;
    }

    public static String getHelmetId() {
        return helmetId;
    }

    public static void setHelmetId(String helmetId) {
        PairInfo.helmetId = helmetId;
    }

    public static String getWorkerId() {
        return workerId;
    }

    public static void setWorkerId(String workerId) {
        PairInfo.workerId = workerId;
    }

    public static Worker getWorker() {
        return worker;
    }

    public static void setWorker(Worker worker) {
        PairInfo.worker = worker;
    }

    public static String getHexCardId() {
        return hexCardId;
    }

    public static void setHexCardId(String hexCardId) {
        PairInfo.hexCardId = hexCardId;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        PairInfo.title = title;
    }

    public static String getJson() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cardId", cardId);
            jsonObject.put("helmetId", helmetId);
            jsonObject.put("workerId", workerId);
            jsonObject.put("hexCardId", hexCardId);
            return jsonObject.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }
}
