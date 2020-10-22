package com.wtm.anshime.ui.main.routeinfo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.wtm.anshime.R;

import java.util.Objects;


/**
 * 귀가 정보를 작성하는 UI
 * */
public class WriteInfoFragment extends Fragment {
    public WriteInfoFragment() {
        // Required empty public constructor
    }

    private Button writeInfoCompleteBtn;
    private MaterialToolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.write_info_toolbar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        writeInfoCompleteBtn = view.findViewById(R.id.write_info_next_button);

        String[] COUNTRIES = new String[] {"도보", "버스", "택시", "지하철", "자전거"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        requireContext(),
                        R.layout.dropdown_menu_popup_item,
                        COUNTRIES);

        AutoCompleteTextView editTextFilledExposedDropdown =
                view.findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);

        writeInfoCompleteBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_writeInfoFragment_to_checkInfoFragment);
        });
    }
}