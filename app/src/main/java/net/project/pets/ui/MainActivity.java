package net.project.pets.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.project.pets.R;
import net.project.pets.adapter.PetAdapter;
import net.project.pets.data.Config;
import net.project.pets.data.Pet;
import net.project.pets.network.ConnectionState;
import net.project.pets.network.DownloadManager;
import net.project.pets.util.Constants;
import net.project.pets.util.DateUtil;
import net.project.pets.util.FilesUtil;
import net.project.pets.util.JsonFormatter;
import net.project.pets.util.PermissionsUtil;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout loadingForeground;
    TextView errorForeground;
    Config storeConfig;
    ArrayList<Pet> pets = new ArrayList<>();
    PetAdapter adapter;
    RecyclerView petsRecyclerView;
    TextView workingHoursTextView;
    Button chatButton;
    Button callButton;

    public static final int REQUEST_CODE_WRITE_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        registerBroadcastReceiverFilters();

        if (!PermissionsUtil.checkPermission(MainActivity.this)) {
            PermissionsUtil.requestPermission(MainActivity.this);
        } else {
            attemptLoadData();
        }
    }

    /**
     * register filters for broadcast receiver
     */

    void registerBroadcastReceiverFilters() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.UPDATE_CONFIG_UI_ACTION));

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.UPDATE_DATA_UI_ACTION));

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.SHOW_ERROR_UI_ACTION));

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(Constants.UPDATE_DATA_UI_ACTION)) {
                loadDataListUI();
            }

            if (action.equals(Constants.UPDATE_CONFIG_UI_ACTION)) {
                loadConfigUI();
            }

            if (action.equals(Constants.SHOW_ERROR_UI_ACTION)) {
                showErrorUI();
            }

            loadingForeground.setVisibility(View.GONE);
        }
    };

    /**
     * init UI views
     */

    void initViews() {
        loadingForeground = findViewById(R.id.loading_foreground);
        errorForeground = findViewById(R.id.error_foreground);
        workingHoursTextView = findViewById(R.id.times_tv);
        chatButton = findViewById(R.id.chat_button);
        callButton = findViewById(R.id.call_button);
        petsRecyclerView = findViewById(R.id.rv_pets);
        petsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatButton.setOnClickListener(this);
        callButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String workingHours = storeConfig.getWorkingHours();
        String message = "";

        try {

            if (DateUtil.canContactStoreNow(workingHours)) {
                message = "Thank you for getting in touch with us. We’ll get back to you as soon as possible";
            } else {
                message = "Work hours has ended. Please contact us again on the next work day";
            }

        } catch (IllegalArgumentException e) {
            message = "Could't read working hours";
        }

        MessageDialog.showDialog(MainActivity.this, message, false);

    }

    /**
     * method to read config files and update ui based
     */

    private void loadConfigUI() {
        readConfigJson();
        updateContactInfo();
    }

    /**
     * method to read data files and update ui based
     */

    private void loadDataListUI() {
        readPetJson();
        setRecyclerViewData();
    }

    /**
     * show error message if request fails
     */

    private void showErrorUI() {
        errorForeground.setVisibility(View.VISIBLE);
        loadingForeground.setVisibility(View.GONE);
    }

    /**
     * set data to recycler view
     */

    void setRecyclerViewData() {
        adapter = new PetAdapter(this, pets);
        petsRecyclerView.setAdapter(adapter);
        loadingForeground.setVisibility(View.GONE);
    }

    /**
     * update chat, call and working hours views in UI based on data
     */

    void updateContactInfo() {
        if (!storeConfig.isChatEnabled()) {
            chatButton.setVisibility(View.GONE);
        }

        if (!storeConfig.isCallEnabled()) {
            callButton.setVisibility(View.GONE);
        }

        workingHoursTextView.setText("Office Hours: " + storeConfig.getWorkingHours());
    }

    /**
     * read data of config Json and update UI
     */

    void readConfigJson() {
        String configDataRaw = FilesUtil.readJsonData(MainActivity.this, Constants.CONFIG_FILE_NAME);
        //String configDataRaw = FilesUtil.readFileFromRawDirectory(MainActivity.this, R.raw.config);
        storeConfig = JsonFormatter.getDataFromConfigJson(configDataRaw);
        updateContactInfo();
    }

    /**
     * read data of Json and store in array list
     */

    void readPetJson() {
        String petsDataRaw = FilesUtil.readJsonData(MainActivity.this, Constants.DATA_FILE_NAME);
        //String petsDataRaw = FilesUtil.readFileFromRawDirectory(MainActivity.this, R.raw.data);
        pets = JsonFormatter.getDataFromPetsJson(petsDataRaw);
    }

    /**
     * check connection and download data if internet is available
     */

    private void attemptLoadData() {
        if (ConnectionState.isInternetAvailable(MainActivity.this)) {
            DownloadManager.downloadFileAsync(MainActivity.this,
                    Constants.CONFIG_FILE_URL,
                    Constants.CONFIG_FILE_NAME,
                    Constants.REQUEST_TYPE_DOWNLOAD_CONFIG);

            DownloadManager.downloadFileAsync(MainActivity.this,
                    Constants.DATA_FILE_URL,
                    Constants.DATA_FILE_NAME,
                    Constants.REQUEST_TYPE_DOWNLOAD_DATA);
        } else {
            checkSavedData(false);
        }
    }

    /**
     * @param checkStoragePermission
     * check if there store data from previous sessions and load them if available
     */

    void checkSavedData(boolean checkStoragePermission) {

        String configDataRaw = FilesUtil.readJsonData(MainActivity.this, Constants.CONFIG_FILE_NAME);
        String petsDataRaw = FilesUtil.readJsonData(MainActivity.this, Constants.DATA_FILE_NAME);

        if (configDataRaw.isEmpty() && petsDataRaw.isEmpty()) {

            //no saved data, close app
            String message = "No data saved. Cannot use app";

            if (checkStoragePermission) {
                message = "Cannot use app without this permission.";
            }

            MessageDialog.showDialog(MainActivity.this, message, true);

        } else if (!petsDataRaw.isEmpty()){
            loadConfigUI();
            loadDataListUI();
            //display message of cached data

        } else {
            String message = "No data saved. Cannot use app";
            MessageDialog.showDialog(MainActivity.this, message, true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_WRITE_PERMISSION:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //download data from server
                    attemptLoadData();
                } else {
                    checkSavedData(true);
                }
                break;
        }
    }
}
