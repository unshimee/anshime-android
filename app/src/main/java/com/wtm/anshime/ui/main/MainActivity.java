package com.wtm.anshime.ui.main;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gun0912.tedpermission.PermissionListener;
import com.wtm.anshime.R;
import com.wtm.anshime.api.RetrofitBuilder;
import com.wtm.anshime.model.Address;
import com.wtm.anshime.ui.BaseActivity;
import com.wtm.anshime.utils.LocationHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wtm.anshime.utils.Constants.LOCATIONS_API_KEY;
import static com.wtm.anshime.utils.Constants.LOCATIONS_DOMAIN;

@SuppressLint("MissingPermission")
public class MainActivity extends BaseActivity {

    private FloatingActionButton mainFab;
    private ExtendedFloatingActionButton editFab;
    private ExtendedFloatingActionButton startFab;
    private ExtendedFloatingActionButton endFab;
    private Animation fabOpen, fabClose, fabRotateClock, fabRotateAntiClock;

    private TextView mainBoardSi;
    private Location location;

    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpFloatingActionButtons();
        mainBoardSi = findViewById(R.id.main_board_si);

        LocationHelper locationHelper = LocationHelper.getInstance();
        locationHelper.setContext(this);

        locationHelper.askLocationPermission(new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "위치권한 허가됨!", Toast.LENGTH_SHORT).show();
                location = locationHelper.getLocation();
                setLocationOnMainBoard();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "위치권한 거부됨!", Toast.LENGTH_SHORT).show();
            }
        });

        location = locationHelper.getLocation();

        if(locationHelper.isLocationPermissionGranted()) {
            Log.d(TAG, "onCreate: Location permission is granted");
            setLocationOnMainBoard();
        }

    }

    // TODO: 10/21/2020 만약 repository 패턴을 이용한다면 네트워킹 코드는 repository 클래스로 이동해야 합니다 .
    private void setLocationOnMainBoard(){
        Call<Address> call = RetrofitBuilder.getInstance().locationApiService.getAddressFromGps(
                Double.toString(location.getLongitude()),
                Double.toString(location.getLatitude()),
                "json",
                LOCATIONS_API_KEY,
                LOCATIONS_DOMAIN
        );

        Callback<Address> callback = new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                if (response.body() != null && response.body().getStreetAddress() != null) {
                    String address = response.body().getStreetAddress();
                    String si = address.split(" ")[0];
                    Log.d(TAG, "onResponse: " + si);
                    mainBoardSi.setText(si);
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        };
        call.enqueue(callback);
    }

    /*
    * Floating Action Button 의 expand, collapse 애니메이션을 위한 메서드.
    * anim 폴더의 애니메이션 에셋들을 이용해서 접히고 펴지는 효과를 줍니다.
    * */
    private void setUpFloatingActionButtons(){
        mainFab = findViewById(R.id.main_fab);
        editFab = findViewById(R.id.edit_fab);
        startFab = findViewById(R.id.start_fab);
        endFab = findViewById(R.id.end_fab);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRotateClock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRotateAntiClock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        mainFab.setOnClickListener(v -> {
            if(isOpen){
                editFab.startAnimation(fabClose);
                startFab.startAnimation(fabClose);
                endFab.startAnimation(fabClose);

                mainFab.startAnimation(fabRotateClock);

                editFab.setClickable(false);
                startFab.setClickable(false);
                endFab.setClickable(false);

                isOpen = false;
            }else{
                editFab.startAnimation(fabOpen);
                startFab.startAnimation(fabOpen);
                endFab.startAnimation(fabOpen);

                mainFab.startAnimation(fabRotateAntiClock);

                editFab.setClickable(true);
                startFab.setClickable(true);
                endFab.setClickable(true);

                isOpen = true;
            }
        });
    }


    private static final String TAG = "MainActivity";
}