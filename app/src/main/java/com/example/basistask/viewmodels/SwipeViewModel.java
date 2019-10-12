package com.example.basistask.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.basistask.data.remote.modelClasses.DataResponseModel;
import com.example.basistask.managers.NetworkManager;

public class SwipeViewModel extends AndroidViewModel {

    private MutableLiveData<DataResponseModel> dataList;
    private NetworkManager networkManager;

    public SwipeViewModel(@NonNull Application application) {
        super(application);
        dataList=new MutableLiveData<>();
        networkManager=new NetworkManager();
    }

    public LiveData<DataResponseModel> getDataListLiveData(){
        return dataList;
    }

    public void getData(){
        networkManager.getDataList(dataList);
    }
}
