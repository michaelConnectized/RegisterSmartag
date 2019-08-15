package com.infosmart.registerSmartag.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.PrecomputedText;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infosmart.registerSmartag.R;
import com.infosmart.registerSmartag.model.RegisterApiConnectionAdaptor;
import com.infosmart.registerSmartag.object.PairInfo;
import com.infosmart.registerSmartag.object.Project;
import com.infosmart.registerSmartag.object.Worker;
import com.infosmart.registerSmartag.utils.Permission;
import com.infosmart.registerSmartag.utils.Utils;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String tag = "MainActivity";

    private RegisterApiConnectionAdaptor registerApiConnectionAdaptor;
    private String projectId = "";
    private List<Worker> workerList;
    private List<Project> projectList;

    private EditText et_workerId;
    private Worker selectWorker;

    private int secretCount = 0;

    MediaPlayer mp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);
        Utils.setActivity(this);

        requestPermission();
        initVar();
        initProjectId();
        initProjects();
        initListener();
        initWorkerListFromMongo();
//        mp1 = MediaPlayer.create(getApplicationContext(), R.raw.bgm);
//
//        mp1.start();
//        mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                if (mp1 == null) {return;}
//                mp1.start();
//                mp1.setLooping(true);
//            }
//        });
//
//        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sound1);
//        mp.start();
//        Log.e(tag, workerList.toString());
        Intent intent = new Intent(this, CardPressingActivity.class);
        startActivity(intent);
        finish();
    }

    public void initVar() {
        et_workerId = findViewById(R.id.et_workerId);
        registerApiConnectionAdaptor = new RegisterApiConnectionAdaptor(this);
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
                secretCount = 0;
                String id = "";
                id += et_workerId.getText();

                Worker worker = new Worker();
                worker.setWorkerIdNo(id);
                if (workerList.contains(worker)) {
                    selectWorker = workerList.get(workerList.indexOf(worker));
                    Log.e(tag, "Contain: "+id+", "+selectWorker.getEnglishName());
                } else {
                    Log.e(tag, "Not Contain: "+id);
                    selectWorker = null;
                }
            }
        });

    }

    public void initWorkerListFromMongo() {
        if (workerList==null)
            workerList = registerApiConnectionAdaptor.getWorkerList(projectId);

        PairInfo.setWorkers(workerList);
    }

    public void initProjectId() {
        projectId = Utils.getSharedPreferences().getString("projectId", getResources().getString(R.string.project_id));
    }

    public void initProjects() {
        projectList = registerApiConnectionAdaptor.getProjectList();

        for (int i=0; i<projectList.size(); i++) {
            if (projectList.get(i).getProjectId().equals(projectId)) {
                PairInfo.setTitle(projectList.get(i).getTitleEng());
                ((TextView)findViewById(R.id.tv_title)).setText(PairInfo.getTitle());
            }
        }
    }

    public void clickNext(View view) {
        if (selectWorker==null) {
            Toast.makeText(this, "Worker Not Found!", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, ConfirmActivity.class);
        PairInfo.setWorker(selectWorker);
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


    public void showProjectList() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Select Project, Now: "+Utils.getSharedPreferences().getString("projectId", "test_zoner"));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        for (Project project : projectList) {
            arrayAdapter.add(project.getTitleEng());
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.getSharedPreferences().edit().putString("projectId", projectList.get(which).getProjectId()).commit();
                MainActivity.this.restart();
            }
        });
        builderSingle.show();
    }

    public void showForceUpdate() {
        String fu = "";
        if (Utils.getSharedPreferences().getBoolean("forceUpdate", false)) {
            fu = "true";
        } else {
            fu = "false";
        }

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Force update settings, Now: "+fu);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Yes");
        arrayAdapter.add("No");



        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.getSharedPreferences().edit().putBoolean("forceUpdate", which==0?true:false).commit();
            }
        });

        builderSingle.show();
    }

    public void showChangeServer() {
        String its = "";
        if (Utils.getSharedPreferences().getBoolean("isTestingServer", true)) {
            its = "Testing";
        } else {
            its = "Production";
        }

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Current server: "+its);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Testing");
        arrayAdapter.add("Production");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isTestingServer = which==0;
                if (isTestingServer) {
                    Utils.getSharedPreferences().edit().putBoolean("isTestingServer", isTestingServer).commit();
                    restart();
                } else {
                    saveLogin(isTestingServer);
                }
            }
        });

        builderSingle.show();
        //isTestingServer
    }

    public void saveLogin(final boolean isTestingServer) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Login");


        final EditText username = new EditText(this);
        final EditText password = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams ll_lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        username.setLayoutParams(lp);
        username.setHint("Username");
        password.setLayoutParams(lp);
        password.setHint("Password");

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(ll_lp);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(username);
        ll.addView(password);
        builderSingle.setView(ll);
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.getSharedPreferences().edit().putString("ppwd", password.getText().toString()).commit();
                Utils.getSharedPreferences().edit().putString("puser", username.getText().toString()).commit();
                Utils.getSharedPreferences().edit().putBoolean("isTestingServer", isTestingServer).commit();
                dialog.dismiss();
                restart();
            }
        });

        builderSingle.show();

    }

    public void secretTitle(View view) {
        secretCount++;
        if (secretCount==5) {
            if (et_workerId.getText().toString().equals("pch")) {
                showProjectList();
            }
            if (et_workerId.getText().toString().equals("fuch")) {
                showForceUpdate();
            }
            if (et_workerId.getText().toString().equals("sch")) {
                showChangeServer();
            }

            secretCount = 0;
        }

    }

    public void restart() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
