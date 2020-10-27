package com.wtm.anshime.ui.main;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.wtm.anshime.R;
import com.wtm.anshime.api.RetrofitBuilder;
import com.wtm.anshime.model.CoordinateResponse;
import com.wtm.anshime.ui.BaseActivity;
import com.wtm.anshime.utils.LocationHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Main Fragment 및 nav_route 파일의 네비게이션 관련 로직을 호스팅하는 액티비티.
 * ViewModel 을 액티비티에서 초기화 한 후 귀가 정보 작성 및 기타 정보들을
 * 헤당 뷰모델에서 관리합니다.
 * */
public class MainActivity extends BaseActivity {

    public MainViewModel mainViewModel;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseCrashlytics firebaseCrashlytics = FirebaseCrashlytics.getInstance();
        firebaseCrashlytics.log("starting main activity ... ");

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        LocationHelper locationHelper = LocationHelper.getInstance();
        locationHelper.setContext(getApplicationContext());

        /*
         * 사용자의 위치 권한이 수락되었다는 것을 전제로 하지만
         * 혹시 몰라 한 번 더 체크합니다.
         * */
        if(locationHelper.isLocationPermissionGranted()) {
            Log.d(TAG, "onCreate: Location permission is granted");
            location = locationHelper.getLocation();
            if(location != null){
                getInitialUserLocation(location.getLongitude(), location.getLatitude());
            }else{
                Toast.makeText(getApplicationContext(), R.string.user_location_not_found, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), R.string.avtivate_location_service, Toast.LENGTH_SHORT).show();
        }
    }

    private void getInitialUserLocation(Double x, Double y){
        Call<CoordinateResponse> coordinateResponseCall =
                RetrofitBuilder.getInstance()
                        .homeApiService
                        .convertCoordinateToAddress(
                            Double.toString(x), Double.toString(y)
                        );

        Callback<CoordinateResponse> callback = new Callback<CoordinateResponse>() {
            @Override
            public void onResponse(Call<CoordinateResponse> call,
                                   Response<CoordinateResponse> response) {
                if(response.isSuccessful()){
                    if(response.body() != null && response.body().getErrors() == null){
                        Log.d(TAG, "onResponse: " + response.body());
                        mainViewModel.setCoordinateResponseLiveData(
                            new CoordinateResponse(
                                response.body().getDepth1(),
                                response.body().getDepth2(),
                                response.body().getDepth3()
                            )
                        );
                    }else{
                        mainViewModel.setCoordinateResponseLiveData(
                                new CoordinateResponse(
                                        response.body() != null ? response.body().getErrors() : null
                                )
                        );
                        Toast.makeText(MainActivity.this,
                                R.string.user_location_not_found,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CoordinateResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this,
                        R.string.location_server_error,
                        Toast.LENGTH_SHORT).show();
            }
        };
        coordinateResponseCall.enqueue(callback);
    }

    private static final String TAG = "MainActivity";
}