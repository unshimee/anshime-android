package com.wtm.anshime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kakao.sdk.user.model.Gender;
import com.wtm.anshime.R;
import com.wtm.anshime.ui.main.MainActivity;

import static com.wtm.anshime.utils.Constants.FEMALE;

public class AuthActivity extends BaseActivity{

    private static final String TAG = "AuthActivity";

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Button kakaoLoginBtn = findViewById(R.id.btn_kakao_login);
        progressBar = findViewById(R.id.progress_bar);

        askLocationPermission();
        askStoragePermission();

        kakaoLoginBtn.setOnClickListener(v -> {
            if(!isNetworkConnected()){
                Toast.makeText(this, R.string.check_network_connection, Toast.LENGTH_LONG).show();
            }else {
                // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
                if (LoginClient.getInstance().isKakaoTalkLoginAvailable(getApplicationContext())) {
                    //카카오톡이 있는 경우
                    setUpKakaoTalkLogin();
                } else {
                    //카카오톡은 없지만 카카오 계정은 있는 경우
                    setUpKakaoAccountLogin();
                }
            }
        });
    }

    private void askStoragePermission() {

    }

    private void askLocationPermission() {

    }

    private void setUpKakaoTalkLogin() {
        LoginClient.getInstance().loginWithKakaoTalk(getApplicationContext(), (oAuthToken, throwable) -> {
            if(throwable != null){
                Log.e(TAG, "setUpKakaoLogin: ERROR => " + throwable.getMessage());
            }else if(oAuthToken != null){
                Log.d(TAG, "setUpKakaoLogin: SUCCESS => " + oAuthToken);
                getUserInfo();
            }
            return null;
        });
    }

    private void setUpKakaoAccountLogin(){
        LoginClient.getInstance().loginWithKakaoAccount(getApplicationContext(), (oAuthToken, throwable) -> {
            if(throwable != null){
                Log.e(TAG, "setUpKakaoLogin: ERROR => " + throwable.getMessage());
            }else if(oAuthToken != null){
                Log.d(TAG, "setUpKakaoLogin: SUCCESS => " + oAuthToken);
                getUserInfo();
            }
            return null;
        });
    }

    private void getUserInfo(){
        UserApiClient.getInstance().me((user, error) -> {
            if(error != null){
                Log.e(TAG, "getUserInfo: " + error.getMessage());
            }else if(user != null){
                Log.i(TAG, "getUserInfo: " + user.getId() + " / "
                        + user.getKakaoAccount().getEmail() + " / "
                        + user.getKakaoAccount().getProfile().getNickname() + " / "
                        + user.getKakaoAccount().getGender()
                );

                if(user.getKakaoAccount() != null){
                    Account account = user.getKakaoAccount();
                    validateGender(account);

                    // TODO: 10/20/2020 Add validation checks for emails & nickname too.
                }
            }
            //getUserInfo: 1508526144 / sarahan774@kakao.com / 한가희 / FEMALE
            return null;
        });
    }

    private void validateGender(Account account) {
        if(account.getGender() != null){
            Gender gender = account.getGender();
            if(!gender.toString().equals(FEMALE)){
                Toast.makeText(this,
                        R.string.if_not_female_msg,
                        Toast.LENGTH_SHORT).show();
            }else{
                // TODO: 10/20/2020 Send user information to server

                // TODO: 10/20/2020 Save account information to local database

                navigateToMainActivity();
            }
        }
    }

    private void navigateToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    void showProgressBar(boolean show) {
        if(show){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }
}


//OAuthToken(accessToken=_bllIbchIfY3hO5987i_4C6NIQtXSYQ1krqEWgo9dRoAAAF1RX4oXg,
// accessTokenExpiresAt=Wed Oct 21 07:11:35 GMT+09:00 2020,
// refreshToken=_iGGHs835_XMFCh_8tD50DdDHKv7zhNnZB7Kfwo9dRoAAAF1RX4oXQ,
// refreshTokenExpiresAt=Sat Dec 19 19:11:35 GMT+09:00 2020,
// scopes=[account_email, gender, profile])