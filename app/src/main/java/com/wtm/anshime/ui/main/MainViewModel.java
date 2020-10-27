package com.wtm.anshime.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wtm.anshime.model.CoordinateResponse;

public class MainViewModel extends ViewModel {

    private MutableLiveData<CoordinateResponse> coordinateResponseLiveData = new MutableLiveData<>();

    public LiveData<CoordinateResponse> getCoordinateResponseLiveData() {
        return coordinateResponseLiveData;
    }

    public void setCoordinateResponseLiveData(CoordinateResponse response) {
        this.coordinateResponseLiveData.setValue(response);
    }
}
