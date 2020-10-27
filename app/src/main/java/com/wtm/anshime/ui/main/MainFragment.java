package com.wtm.anshime.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wtm.anshime.R;

public class MainFragment extends Fragment {

    private FloatingActionButton mainFab;
    private ExtendedFloatingActionButton editFab;
    private ExtendedFloatingActionButton startFab;
    private ExtendedFloatingActionButton endFab;
    private Animation fabOpen, fabClose, fabRotateClock, fabRotateAntiClock;

    private TextView mainBoardSi;

    private Button enterChatBtn;

    private MainViewModel mainViewModel;

    boolean isOpen = false;

    public MainFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setUpFloatingActionButtons(view);

        enterChatBtn = view.findViewById(R.id.btn_enter_group_chat);
        mainBoardSi = view.findViewById(R.id.main_board_si);

        //Main Activity 에서 초기화된 뷰모델을 공유하는 객체
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

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

        enterChatBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_main_to_chat);
        });


        /*
         * Main Activity 에서 서버에서 불러온 위치 정보를 뷰모델에 셋팅한 후
         * 현황판에 시 정보를 반영합니다.
         * 만약 값이 없다면 ???시 로 셋팅합니다.
         * */
        mainViewModel.getCoordinateResponseLiveData().observe(requireActivity(), coordinateResponse -> {
            if(coordinateResponse.getErrors() == null){
                if(coordinateResponse.getDepth1() != null){
                   mainBoardSi.setText(coordinateResponse.getDepth1());
                }else{
                    mainBoardSi.setText(R.string.unkown_location);
                }
            }
        });
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
