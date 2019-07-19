package com.infosmart.registerSmartag.object;

import org.json.JSONObject;

public class PairInfo {
    private static String cardId;
    private static String helmetId;

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

    public static String getJson() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cardId", cardId);
            jsonObject.put("helmetId", helmetId);
            return jsonObject.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }
}
