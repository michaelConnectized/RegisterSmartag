package com.infosmart.registerSmartag.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.infosmart.registerSmartag.R;
import com.infosmart.registerSmartag.nfcCore.CardTappedEventArgs;
import com.infosmart.registerSmartag.nfcCore.NFCCardEventHandler;
import com.infosmart.registerSmartag.nfcCore.NFCCardEventListener;
import com.infosmart.registerSmartag.object.PairInfo;

public class HelmetPressingActivity extends AppCompatActivity implements NFCCardEventListener {

    private NFCCardEventHandler handler;
    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_helmet_pressing);
        ((TextView)findViewById(R.id.tv_title)).setText(PairInfo.getTitle());

        NFCCardEventHandler handler = new NFCCardEventHandler(this, this);
        this.handler = handler;
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sound4);
        mp.start();

        activity = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!activity.isDestroyed()) {
                    redirectTapCardActivity();
                }
            }
        }, 4000);
    }

    @Override
    public void onCardTapped(CardTappedEventArgs e){
        if (e.getTag()[1].equals("android.nfc.tech.Ndef")) {
            PairInfo.setHelmetId(e.getCardID().getRawHexadecimal().substring(2));
            next();
        } else {
            Toast.makeText(this, "拍帽錯誤, 請重拍\nPlease tap again", Toast.LENGTH_LONG).show();
            redirectTapCardActivity();
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

    public void redirectTapCardActivity() {
        Intent intent = new Intent(this, CardPressingActivity.class);
        startActivity(intent);
        finish();
    }
}