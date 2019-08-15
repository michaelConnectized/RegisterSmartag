package com.infosmart.registerSmartag.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.infosmart.registerSmartag.R;
import com.infosmart.registerSmartag.object.PairInfo;

public class ConfirmActivity extends AppCompatActivity {
    private String id;
    private String englishName;
    private String chineseName;
    private String trade;

    private TextView tv_eng;
    private TextView tv_chi;
    private TextView tv_cwr;
    private TextView tv_trade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_confirm);

        ((TextView)findViewById(R.id.tv_title)).setText(PairInfo.getTitle());

        initVar();
        initUI();
//        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sound2);
//        mp.start();
    }
    public void initVar() {
        tv_eng = findViewById(R.id.tv_eng);
        tv_chi = findViewById(R.id.tv_chi);
        tv_cwr = findViewById(R.id.tv_cwr);
        tv_trade = findViewById(R.id.tv_trade);

        id = PairInfo.getWorker().getWorkerIdNo();
        englishName = PairInfo.getWorker().getEnglishName();
        chineseName = PairInfo.getWorker().getChineseName();
        trade = "";

    }

    public void initUI() {
        tv_eng.setText(englishName);
        tv_chi.setText(chineseName);
        tv_cwr.setText(id);
        tv_trade.setText(trade);
    }

    public void clickNext(View view) {
        PairInfo.setWorkerId(PairInfo.getWorker().getId());
        Intent intent = new Intent(this, CardPressingActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickBack(View view) {
        finish();
    }
}