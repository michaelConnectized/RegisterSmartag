package com.infosmart.registerSmartag.nfcCore;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;

import androidx.appcompat.app.AppCompatActivity;

public class NFCCardEventHandler {
    // same instance, 1 interface and 1 class
    // for callback usage
    private NFCCardEventListener listener;
    // for instance access
    private AppCompatActivity activity;

    // instance variables
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;
    private String[][] mTechLists;

    public NFCCardEventHandler(AppCompatActivity activity, NFCCardEventListener listener){
        // dependency injection
        this.activity = activity;
        this.listener = listener;

        // initialization
        this.initialize();
    }

    private void initialize(){
        // initialization===========================================================================
        // Check for available NFC Adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity);

        // if this device is unavailable, throw a runtime exception
        if (nfcAdapter == null) {
            throw new RuntimeException("This device does not support NFC.");
        }

        pendingIntent = PendingIntent.getActivity(activity, 0,
            new Intent(activity, activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        intentFiltersArray = new IntentFilter[]{
            new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED),
            new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED),
            new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        };

        try {
            for (IntentFilter filter : intentFiltersArray){
                filter.addDataType("*/*");
            }
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }

        // Setup a tech list for all NfcF tags
        mTechLists = new String[][] {
            new String[] {
                MifareClassic.class.getName()
            }, new String[]{
                NfcA.class.getName()
            }
        };

        resolveIntent(activity.getIntent());
    }

    public void resolveIntent(Intent intent) {
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
        || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
        || NfcAdapter.ACTION_TAG_DISCOVERED.equals(action))
        {
            Tag tag = intent.getParcelableExtra(nfcAdapter.EXTRA_TAG);

            // create a new event argument
            CardTappedEventArgs e = new CardTappedEventArgs(
                new CardID(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)),
                intent.getStringExtra(nfcAdapter.EXTRA_DATA),
                tag.getTechList()
            );

            // invoke callback method
            this.listener.onCardTapped(e);
        }
    }

    public void enableForegroundDispatch(){
        nfcAdapter.enableForegroundDispatch(activity, pendingIntent, intentFiltersArray, mTechLists);
    }

    public void disableForegroundDispatch(){
        nfcAdapter.disableForegroundDispatch(activity);
    }
}
