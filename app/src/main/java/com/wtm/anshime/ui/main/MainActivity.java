package com.wtm.anshime.ui.main;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wtm.anshime.R;
import com.wtm.anshime.ui.BaseActivity;

public class MainActivity extends BaseActivity {

    private FloatingActionButton mainFab;
    private ExtendedFloatingActionButton editFab;
    private ExtendedFloatingActionButton startFab;
    private ExtendedFloatingActionButton endFab;

    private Animation fabOpen, fabClose, fabRotateClock, fabRotateAntiClock;

    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpFloatingActionButtons();

    }




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
}