package com.infosmart.registerSmartag.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.infosmart.registerSmartag.R;
import com.infosmart.registerSmartag.model.RegisterApiConnectionAdaptor;
import com.infosmart.registerSmartag.nfcCore.CardTappedEventArgs;
import com.infosmart.registerSmartag.nfcCore.NFCCardEventHandler;
import com.infosmart.registerSmartag.nfcCore.NFCCardEventListener;
import com.infosmart.registerSmartag.object.PairInfo;
import com.infosmart.registerSmartag.object.Worker;

import java.util.List;

public class CardPressingActivity extends AppCompatActivity implements NFCCardEventListener {

    private NFCCardEventHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_card_pressing);

        ((TextView)findViewById(R.id.tv_title)).setText(PairInfo.getTitle());

        NFCCardEventHandler handler = new NFCCardEventHandler(this, this);
        this.handler = handler;
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sound3);
        mp.start();
    }

    @Override
    public void onCardTapped(CardTappedEventArgs e){
        if (e.getTag()[1].equals("android.nfc.tech.MifareClassic") || e.getTag()[1].equals("android.nfc.tech.NfcA")) {
            PairInfo.setCardId(e.getCardID().getRawInteger());
            PairInfo.setHexCardId(e.getCardID().getReversedHexadecimal());

            if (isInList(PairInfo.getCardId())) {
                next();
            } else {
                Toast.makeText(this, "此卡未登記, 請重拍\nPlease tap again", Toast.LENGTH_LONG).show();
            }

//            checkAndUpdateCardId();
        } else {
            Toast.makeText(this, "拍卡錯誤, 請重拍\nPlease tap again", Toast.LENGTH_LONG).show();
            for (int i=0; i<e.getTag().length; i++) {
                Log.e("FinishMatchingActivity", e.getTag()[i]);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.enableForegroundDispatch();
    }

    @Override
    public void onNewIntent(Intent intent) {
        handler.resolveIntent(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.disableForegroundDispatch();
    }

    public void next() {
        Intent intent = new Intent(this, HelmetPressingActivity.class);
        startActivity(intent);
        finish();
    }

    public void checkAndUpdateCardId() {
        if (PairInfo.getCardId()!=null && PairInfo.getWorkerId()!=null) {
            if (new RegisterApiConnectionAdaptor(this).setWorkerCardId(PairInfo.getWorkerId(), PairInfo.getCardId(), PairInfo.getWorker())) {
                next();
            } else {
                Toast.makeText(this, "Card Update Fail!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean isInList(String cardId) {
        boolean isSet = false;
        List<Worker> workers = PairInfo.getWorkers();
        for (int i=0; i<workers.size(); i++) {
            if (workers.get(i).getCardId().equals(cardId)) {
                isSet = true;
            }
        }
        return isSet;
    }
}