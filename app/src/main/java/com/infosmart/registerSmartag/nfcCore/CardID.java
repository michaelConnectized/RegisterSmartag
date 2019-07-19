package com.infosmart.registerSmartag.nfcCore;

public class CardID {
    private byte[] cardId;

    public CardID(byte[] cardId){
        this.cardId = cardId;
    }

    public String getRawHexadecimal(){
        StringBuilder sb = new StringBuilder(cardId.length * 2);

        for(byte b: cardId)
            sb.append(String.format("%02x", b));

        return sb.toString();
    }

    public String getReversedHexadecimal(){
        StringBuilder sb = new StringBuilder(cardId.length * 2);

        for(byte b: cardId)
            sb.insert(0, String.format("%02x", b));

        return sb.toString();
    }

    public String getRawInteger(){
        return getRawInteger(true);
    }

    public String getRawInteger(boolean isComplement){
        String str = String.valueOf(Long.parseLong(getRawHexadecimal(), 16));

        return isComplement ? complement(str) : str;
    }

    public String getReversedInteger(){
        return getReversedInteger(true);
    }

    public String getReversedInteger(boolean isComplement){
        String str = String.valueOf(Long.parseLong(getReversedHexadecimal(), 16));

        return isComplement ? complement(str) : str;
    }

    private String complement(String str){
        int range;

        switch (cardId.length){
            case 4:
                range = 10;
                break;

            case 7:
                range = 17;
                break;

            default:
                range = 0;
                break;
        }

        for (int i = 0; i < range - str.length(); i++){
            str = "0" + str;
        }

        return str;
    }
}
