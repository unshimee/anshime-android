package com.wtm.anshime.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wtm.anshime.utils.NetworkHelper;

public abstract class BaseActivity extends AppCompatActivity {

    void showProgressBar(boolean show){ }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        if(!isNetworkConnected()){
            Toast toast = Toast.makeText(this, "네트워크 연결상태를 확인해주세요.", Toast.LENGTH_SHORT);
            toast.setGravity(50, 0,0);
            toast.show();
        }
    }

    public boolean isNetworkConnected(){
        NetworkHelper networkHelper = new NetworkHelper(getApplicationContext());
        return networkHelper.isNetworkConnected();
    }

}