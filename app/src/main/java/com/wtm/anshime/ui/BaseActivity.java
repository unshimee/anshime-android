package com.wtm.anshime.ui;

import androidx.appcompat.app.AppCompatActivity;

import com.wtm.anshime.utils.NetworkHelper;

public abstract class BaseActivity extends AppCompatActivity {

    void showProgressBar(boolean show){ }

    boolean isNetworkConnected(){
        NetworkHelper networkHelper = new NetworkHelper(getApplicationContext());
        return networkHelper.isNetworkConnected();
    }

}