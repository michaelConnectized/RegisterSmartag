package com.infosmart.registerSmartag.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.infosmart.registerSmartag.R;
import com.infosmart.registerSmartag.nfcCore.CardTappedEventArgs;
import com.infosmart.registerSmartag.nfcCore.NFCCardEventHandler;
import com.infosmart.registerSmartag.nfcCore.NFCCardEventListener;
import com.infosmart.registerSmartag.object.PairInfo;

public class HelmetPressingActivity extends AppCompatActivity implements NFCCardEventListener {

    private NFCCardEventHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_helmet_pressing);

        NFCCardEventHandler handler = new NFCCardEventHandler(this, this);
        this.handler = handler;
    }

    @Override
    public void onCardTapped(CardTappedEventArgs e){
        if (e.getTag()[1].equals("android.nfc.tech.Ndef")) {
            PairInfo.setHelmetId(e.getCardID().getRawHexadecimal().substring(2));
            next();
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
        Intent intent = new Intent(this, FinishMatchingActivity.class);
        startActivity(intent);
        finish();
    }
}