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

        Log.e("FinishMatchingActivity", PairInfo.getJson());
        initVar();
        checkAndUpdateCardId();
        checkAndRegisterHelmet();
    }

    public void initVar() {
        registerApiConnectionAdaptor = new RegisterApiConnectionAdaptor(getResources());
    }

    public void checkAndUpdateCardId() {
        if (PairInfo.getCardId()!=null && PairInfo.getWorkerId()!=null) {
            if (registerApiConnectionAdaptor.setWorkerCardId(PairInfo.getWorkerId(), PairInfo.getCardId())) {
//                Toast.makeText(this, "Card Update Successful!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Card Update Fail!", Toast.LENGTH_LONG).show();
                redirectTapCardActivity();
            }
        }
    }

    public void checkAndRegisterHelmet() {
        if (PairInfo.getHelmetId()!=null && PairInfo.getHexCardId()!=null) {
            if (registerApiConnectionAdaptor.registerHelmet(PairInfo.getHexCardId(), PairInfo.getHelmetId())) {
//                Toast.makeText(this, "Register Helmet Successful!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Register Helmet Fail!", Toast.LENGTH_LONG).show();
                redirectTapHelmetActivity();
            }
        }
    }

    public void clickNext(View view) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        finish();
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