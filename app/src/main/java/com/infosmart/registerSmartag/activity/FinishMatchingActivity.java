package com.infosmart.registerSmartag.activity;

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
import com.infosmart.registerSmartag.model.RegisterApiConnectionAdaptor;
import com.infosmart.registerSmartag.object.PairInfo;

public class FinishMatchingActivity extends AppCompatActivity {
    private RegisterApiConnectionAdaptor registerApiConnectionAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_finish_matching);

        ((TextView)findViewById(R.id.tv_title)).setText(PairInfo.getTitle());

        Log.e("FinishMatchingActivity", PairInfo.getJson());
        initVar();
        checkAndRegisterHelmet();
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sound5);
        mp.start();
    }

    public void initVar() {
        registerApiConnectionAdaptor = new RegisterApiConnectionAdaptor(this);
    }



    public void checkAndRegisterHelmet() {
        if (PairInfo.getHelmetId()!=null && PairInfo.getHexCardId()!=null) {
            if (registerApiConnectionAdaptor.registerHelmet(PairInfo.getHexCardId(), PairInfo.getHelmetId())) {
//                Toast.makeText(this, "Register Helmet Successful!", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        redirectTapCardActivity();
                    }
                }, 4000);
            } else {
                Toast.makeText(this, "此卡可能已註冊頭盔", Toast.LENGTH_LONG).show();
                redirectTapHelmetActivity();
            }
        }
    }

    public void clickNext(View view) {
//        redirectTapCardActivity();
    }

    public void redirectTapCardActivity() {
        Intent intent = new Intent(this, CardPressingActivity.class);
        startActivity(intent);
        finish();
    }

    public void redirectTapHelmetActivity() {
        Intent intent = new Intent(this, HelmetPressingActivity.class);
        startActivity(intent);
        finish();
    }
}