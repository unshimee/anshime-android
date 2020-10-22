package com.wtm.anshime.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.wtm.anshime.R;
import com.wtm.anshime.ui.BaseActivity;


/**
 * Main Fragment 및 nav_route 파일의 네비게이션 관련 로직을 호스팅하는 액티비티.
 * ViewModel 을 액티비티에서 초기화 한 후 귀가 정보 작성 및 기타 정보들을
 * 헤당 뷰모델에서 관리합니다.
 * */
public class MainActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    private static final String TAG = "MainActivity";
}