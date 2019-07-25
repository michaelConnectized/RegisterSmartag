package com.infosmart.registerSmartag.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.infosmart.registerSmartag.R;
import com.infosmart.registerSmartag.model.RegisterApiConnectionAdaptor;
import com.infosmart.registerSmartag.object.Worker;
import com.infosmart.registerSmartag.utils.Permission;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String tag = "MainActivity";

    private RegisterApiConnectionAdaptor registerApiConnectionAdaptor;
    private String projectId = "";
    private List<Worker> workerList;

    private EditText et_englishName;
    private EditText et_workerId;
    private Worker selectWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        requestPermission();
        initVar();
        initListener();
        initWorkerListFromMongo();
        Log.e(tag, workerList.toString());
    }

    public void initVar() {
        et_workerId = findViewById(R.id.et_workerId);
        et_englishName = findViewById(R.id.et_englishName);
        registerApiConnectionAdaptor = new RegisterApiConnectionAdaptor(getResources());

        projectId = getResources().getString(R.string.project_id);
    }

    public void initListener() {
        et_workerId.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String id = "";
                id += et_workerId.getText();

                Worker worker = new Worker();
                worker.setId(id);
                if (workerList.contains(worker)) {
                    selectWorker = workerList.get(workerList.indexOf(worker));
                    et_englishName.setEnabled(true);
                    et_englishName.setText(selectWorker.getEnglishName());
                    et_englishName.setEnabled(false);
                    Log.e(tag, "Contain: "+id+", "+selectWorker.getEnglishName());
                } else {
                    Log.e(tag, "Not Contain: "+id);
                    selectWorker = null;
                    et_englishName.setEnabled(true);
                    et_englishName.setText("");
                    et_englishName.setEnabled(false);
                }
            }
        });

    }

    public void initWorkerListFromMongo() {
        if (workerList==null)
            workerList = registerApiConnectionAdaptor.getWorkerList(projectId);
    }

    public void clickNext(View view) {
        if (selectWorker==null) {
            Toast.makeText(this, "Worker Not Found!", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, ConfirmActivity.class);
        intent.putExtra("workerId", selectWorker.getId());
        intent.putExtra("englishName", selectWorker.getEnglishName());
        intent.putExtra("chineseName", selectWorker.getChineseName());
        startActivity(intent);
//        finish();
    }

    public void requestPermission() {
        String[] permissionTypes = {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.READ_CONTACTS
        };

        Permission permission = new Permission(this);
        permission.checkPermission(permissionTypes);
    }
}
