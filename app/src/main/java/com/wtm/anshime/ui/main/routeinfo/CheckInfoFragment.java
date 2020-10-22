package com.wtm.anshime.ui.main.routeinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.wtm.anshime.R;

/**
 * 출발지, 도착지를 정하고 귀가 정보를 작성한 이후
 * 작성된 정보들을 다시 한 번 확인하는 화면.
 * */
public class CheckInfoFragment extends Fragment {

    private MaterialToolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.check_info_toolbar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
    }
}
