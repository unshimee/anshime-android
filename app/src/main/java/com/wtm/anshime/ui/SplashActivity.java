package com.wtm.anshime.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wtm.anshime.R;
import com.wtm.anshime.storage.AppPreference;
import com.wtm.anshime.ui.main.MainActivity;

import static com.wtm.anshime.utils.Constants.ACCESS_TOKEN_KEY;
import static com.wtm.anshime.utils.Constants.REFRESH_TOKEN_KEY;

public class SplashActivity extends AppCompatActivity {

    private AppPreference appPreference;
    private String accessToken;
    private String refreshToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        appPreference = AppPreference.getInstance();
        appPreference.setContext(getApplicationContext());
        try {
            accessToken = appPreference.readFromPrefs(ACCESS_TOKEN_KEY);
            refreshToken = appPreference.readFromPrefs(REFRESH_TOKEN_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: 10/26/2020 authentication with token

        //navigate to auth activity no matter what for now.
        boolean isAuthenticated = false;
        if(isAuthenticated){
            //if authenticated, navigate to MainActivity
            Handler mHandler = new Handler();
            mHandler.postDelayed(this::navigateToMainActivity, 800);
        }else{
            //if not authenticated, navigate to AuthActivity
            Handler mHandler = new Handler();
            mHandler.postDelayed(this::navigateToAuthActivity, 800);
        }
    }

    private void navigateToAuthActivity(){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}