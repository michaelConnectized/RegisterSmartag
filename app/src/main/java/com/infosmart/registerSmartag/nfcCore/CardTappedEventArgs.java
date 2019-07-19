package com.infosmart.registerSmartag.nfcCore;

public class CardTappedEventArgs {
    private CardID cardID;
    private String data;
    private String[] tag;

    public CardTappedEventArgs(CardID cardID, String data, String[] tag){
        this.cardID = cardID;
        this.data = data;
        this.tag = tag;
    }

    public CardID getCardID() {
        return cardID;
    }

    public String getData() {
        return data;
    }

    public String[] getTag() {
        return tag;
    }
}
