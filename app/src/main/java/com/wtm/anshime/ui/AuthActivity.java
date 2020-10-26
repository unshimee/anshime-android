package com.wtm.anshime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kakao.sdk.user.model.Gender;
import com.wtm.anshime.R;
import com.wtm.anshime.api.RetrofitBuilder;
import com.wtm.anshime.model.AuthBody;
import com.wtm.anshime.model.AuthResponse;
import com.wtm.anshime.storage.AppPreference;
import com.wtm.anshime.ui.main.MainActivity;
import com.wtm.anshime.utils.LocationHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wtm.anshime.utils.Constants.ACCESS_TOKEN_KEY;
import static com.wtm.anshime.utils.Constants.FEMALE;
import static com.wtm.anshime.utils.Constants.REFRESH_TOKEN_KEY;
import static com.wtm.anshime.utils.Constants.USER_NAME_KEY;

public class AuthActivity extends BaseActivity{

    private static final String TAG = "AuthActivity";

    private RetrofitBuilder retrofitBuilder = RetrofitBuilder.getInstance();
    private AppPreference appPreference;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Button kakaoLoginBtn = findViewById(R.id.btn_kakao_login);
        progressBar = findViewById(R.id.progress_bar);
        appPreference = AppPreference.getInstance();
        appPreference.setContext(getApplicationContext());

        askLocationPermission();
        askStoragePermission();


        try {
            String authToken = appPreference.readFromPrefs(ACCESS_TOKEN_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }

        kakaoLoginBtn.setOnClickListener(v -> {
            if(!isNetworkConnected()){
                Toast.makeText(this, R.string.check_network_connection, Toast.LENGTH_LONG).show();
            }else {
                // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
//                if (LoginClient.getInstance().isKakaoTalkLoginAvailable(getApplicationContext())) {
//                    //카카오톡이 있는 경우
//                    setUpKakaoTalkLogin();
//                } else {

                // TODO: 10/26/2020 카카오 앱으로 로그인 할 때 sdk 버그 발생.
                    //카카오톡은 없지만 카카오 계정은 있는 경우
                    setUpKakaoAccountLogin();
//                }
            }
        });
    }

    private void askStoragePermission() {

    }

    /*
    * 로그인 화면에 들어가면 사용자에게 위치 권한을 묻습니다.
    * 해커톤인만큼 최대한 단순하게 코드를 짜기 위해
    * 위치 권한이 본 단계에서 수락되었음을 가정하고 이후 코드를 작성합니다.
    * */
    private void askLocationPermission() {
        LocationHelper locationHelper = LocationHelper.getInstance();
        locationHelper.setContext(this);

        locationHelper.askLocationPermission(new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(AuthActivity.this, R.string.location_permission_granted_msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(AuthActivity.this, R.string.location_permission_denied_msg, Toast.LENGTH_SHORT).show();
            }
        });
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

    private void signIn(AuthBody authBody, Account account){
        Call<AuthResponse> authResponseCall = retrofitBuilder.authApiService.signInKakao(authBody);
        Callback<AuthResponse> authResponseCallback
                = new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                if(response.body() != null){
                    Log.d(TAG, "onResponse: " + response.body());
                    if(response.body().getErrors().isEmpty()
                            && !response.body().getAccessToken().isEmpty()
                            && !response.body().getRefreshToken().isEmpty()){

                        String accessToken = response.body().getAccessToken();
                        String refreshToken = response.body().getRefreshToken();

                        Log.d(TAG, "onResponse: " + accessToken + "\n\n" + refreshToken);

                        try {
                            appPreference.writeToPrefs(USER_NAME_KEY, account.getProfile().getNickname());
                            appPreference.writeToPrefs(ACCESS_TOKEN_KEY, accessToken);
                            appPreference.writeToPrefs(REFRESH_TOKEN_KEY, refreshToken);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        navigateToMainActivity();
                    }else{
                        Log.e(TAG, "onResponse: " + response.body().getErrors());
                        Toast.makeText(AuthActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.d(TAG, "onResponse: body is null " + response);
                }
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(AuthActivity.this, "로그인 실패\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        };
        authResponseCall.enqueue(authResponseCallback);
    }

    private void getUserInfo(){
        UserApiClient.getInstance().me((user, error) -> {
            if(error != null){
                Log.e(TAG, "getUserInfo: " + error.getMessage());
            }else if(user != null){
                long userId = user.getId();
                if(user.getKakaoAccount() != null){
                    Account account = user.getKakaoAccount();
                    boolean isGenderValid = validateGender(account);
                    boolean isEmailValid = validateEmail(account);
                    boolean isNameValid = validateName(account);

                    Log.d(TAG, "getUserInfo: " + account.getEmail() + ", " + userId);
                    Log.d(TAG, "getUserInfo: " + isEmailValid + ", " + isGenderValid + ", " + isNameValid);

                    if(isGenderValid && isEmailValid && isNameValid && userId > 0.0){

                        AuthBody authBody = new AuthBody(
                                account.getEmail(),
                                account.getGender().toString().toLowerCase(),
                                Long.toString(userId),
                                account.getProfile().getNickname()
                        );
                        signIn(authBody, account);
                    }
                }
            }
            return null;
        });
    }

    private boolean validateName(Account account) {
        if(account.getProfile() != null){
            return account.getProfile().getNickname() != null;
        }else{
            return false;
        }
    }

    private boolean validateEmail(Account account) {
        if(account.getEmail() != null){
            String email = account.getEmail();
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }else{
            return false;
        }
    }

    private boolean validateGender(Account account) {
        if(account.getGender() != null){
            Gender gender = account.getGender();
            if(!gender.toString().equals(FEMALE)){
                Toast.makeText(this,
                        R.string.if_not_female_msg,
                        Toast.LENGTH_SHORT).show();
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    private void navigateToMainActivity(){
        Toast.makeText(AuthActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
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