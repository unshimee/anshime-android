package com.wtm.anshime.ui.main;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wtm.anshime.R;
import com.wtm.anshime.api.RetrofitBuilder;
import com.wtm.anshime.model.Address;
import com.wtm.anshime.utils.LocationHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wtm.anshime.utils.Constants.JSON;
import static com.wtm.anshime.utils.Constants.LOCATIONS_API_KEY;
import static com.wtm.anshime.utils.Constants.LOCATIONS_DOMAIN;

public class MainFragment extends Fragment {


    private FloatingActionButton mainFab;
    private ExtendedFloatingActionButton editFab;
    private ExtendedFloatingActionButton startFab;
    private ExtendedFloatingActionButton endFab;
    private Animation fabOpen, fabClose, fabRotateClock, fabRotateAntiClock;

    private TextView mainBoardSi;
    private Location location;

    boolean isOpen = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpFloatingActionButtons(view);
        mainBoardSi = view.findViewById(R.id.main_board_si);

        LocationHelper locationHelper = LocationHelper.getInstance();
        locationHelper.setContext(getContext());

        /*
         * 사용자의 위치 권한이 수락되었다는 것을 전제로 하지만
         * 혹시 몰라 한 번 더 체크합니다.
         * */
        if(locationHelper.isLocationPermissionGranted()) {
            location = locationHelper.getLocation();
            Log.d(TAG, "onCreate: Location permission is granted");
            setLocationOnMainBoard();
        }

        /* 귀가 정보 작성 페이지로 넘어갑니다.
         * */
        editFab.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_chooseRouteFragment);
        });

        // TODO: 10/22/2020 귀가 시작 이후의 로직을 작성해 주시면 됩니다.
        startFab.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "귀가 시작", Toast.LENGTH_SHORT).show();
        });

        // TODO: 10/22/2020 귀가 종료 이후의 로직을 작성해주시면 됩니다.
        endFab.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "귀가 종료", Toast.LENGTH_SHORT).show();
        });
    }

    // 참고) 만약 repository 패턴을 이용한다면 네트워킹 코드는 repository 클래스로 이동해야 합니다 .
    /* GPS 값을 도로명주소로 변환하는 API를 호출하고
     *  만약 올바른 GPS 값을 받아서 도로명주소로 제대로 반환이 된 경우
     *  무슨 시에 있는지 표시해줍니다.
     *  만약 GPS 값이 잘못되어서 서버에서 도로명주소를 읽어오지 못한 경우
     *  ???시 로 디스플레이 됩니다.
     * */
    private void setLocationOnMainBoard(){

        Call<Address> call = RetrofitBuilder.getInstance().locationApiService.getAddressFromGps(
                Double.toString(location.getLongitude()),
                Double.toString(location.getLatitude()),
                JSON,
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
                }else{
                    //emulator 에서 올바른 gps 값을 읽어오지 못할 경우
                    //일단은 ???로 디스플레이 하도록 합니다.
                    mainBoardSi.setText(R.string.si_placeholder);
                    Toast.makeText(getContext(), R.string.wrong_gps_value_msg, Toast.LENGTH_SHORT).show();
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
    private void setUpFloatingActionButtons(View view){
        mainFab = view.findViewById(R.id.main_fab);
        editFab = view.findViewById(R.id.edit_fab);
        startFab = view.findViewById(R.id.start_fab);
        endFab = view.findViewById(R.id.end_fab);

        fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        fabRotateClock = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_clockwise);
        fabRotateAntiClock = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anticlockwise);

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

    private static final String TAG = "MainFragment";
}
