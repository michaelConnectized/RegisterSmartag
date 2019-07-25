package com.infosmart.registerSmartag.activity;

import android.content.Intent;
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

    private TextView tv_eng;
    private TextView tv_chi;
    private TextView tv_cwr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_confirm);

        initVar();
        initUI();
    }
    public void initVar() {
        tv_eng = findViewById(R.id.tv_eng);
        tv_chi = findViewById(R.id.tv_chi);
        tv_cwr = findViewById(R.id.tv_cwr);

        Intent intent = getIntent();
        id = intent.getStringExtra("workerId");
        englishName = intent.getStringExtra("englishName");
        chineseName = intent.getStringExtra("chineseName");
    }

    public void initUI() {
        tv_eng.setText(englishName);
        tv_chi.setText(chineseName);
        tv_cwr.setText(id.replace("CWR", "CWR- "));
    }

    public void clickNext(View view) {
        PairInfo.setWorkerId(id);
        Intent intent = new Intent(this, CardPressingActivity.class);
        startActivity(intent);
        finish();
    }
}