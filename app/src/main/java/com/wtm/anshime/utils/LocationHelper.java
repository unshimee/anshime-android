package com.wtm.anshime.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;


/*
* 위치권한을 확인할 수 있는 헬퍼 클래스입니다.
* 본 클래스를 통해서
* 1. 위치 권한이 부여되었는지 확인하고
* 2. Location Manager 를 가져와서
* 3. Location 객체의 값을 읽을 수 있습니다.
* */
public class LocationHelper {

    private static LocationHelper instance;
    private LocationManager locationManager;
    private Location location;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public static LocationHelper getInstance() {
        if(instance == null){
            instance = new LocationHelper();
        }
        return instance;
    }


    public void askLocationPermission(PermissionListener permissionListener){
        //위치권한이 부여되지 않은 경우
        if (!isLocationPermissionGranted()) {

            String[] permissions = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };

            TedPermission.with(context)
                    .setRationaleMessage("안심이 현황판 사용을 위해 위치 권한이 필요합니다.")
                    .setDeniedMessage("위치 권한이 없는경우 안심이 앱을 사용하실 수 없습니다.")
                    .setPermissions(permissions)
                    .setPermissionListener(permissionListener)
                    .check();
        }
    }

    public LocationManager getLocationManager() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager;
    }

    @SuppressLint("MissingPermission")
    public Location getLocation(){
        LocationManager locationManager = getLocationManager();
        Log.d(TAG, "location manager " + locationManager.getAllProviders());

        Log.d(TAG, "getLocation: " + "\n"
        + locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) + "\n" +
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.d(TAG, "getLocation: from gps " + location);
        }else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }else if(locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){
            location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        } else{
            FirebaseCrashlytics.getInstance().log("location manager : no provider !");
            return null;
        }
        Log.d(TAG, "getLocation: " + location);
        return location;
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation(){

    }

    public boolean isLocationPermissionGranted(){
        return (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED);
    }

    private static final String TAG = "LocationHelper";
}
