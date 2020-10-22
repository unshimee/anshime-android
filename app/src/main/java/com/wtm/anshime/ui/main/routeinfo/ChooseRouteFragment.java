package com.wtm.anshime.ui.main.routeinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.wtm.anshime.R;

/**
 * 귀가 정보를 작성하기 전 출발지와 도착지를 설정하는 화면
 * */
public class ChooseRouteFragment extends Fragment {


    private Button completeChooseRouteBtn;
    private MaterialToolbar toolbar;

    public ChooseRouteFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_route, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.choose_route_toolbar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        completeChooseRouteBtn = view.findViewById(R.id.choose_route_next_button);
        completeChooseRouteBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_chooseRouteFragment_to_writeInfoFragment);
        });
    }
}
