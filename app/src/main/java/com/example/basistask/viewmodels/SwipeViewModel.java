package com.example.basistask.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.basistask.data.remote.modelClasses.DataResponseModel;
import com.example.basistask.data.remote.modelClasses.DatumResponseModelClass;
import com.example.basistask.managers.NetworkManager;

import java.util.List;

public class SwipeViewModel extends AndroidViewModel {

    private NetworkManager networkManager;

    public SwipeViewModel(@NonNull Application application) {
        super(application);
        networkManager=new NetworkManager();
    }

//    public LiveData<DataResponseModel> getDataListLiveData(){
//        return dataList;
//    }

    public MutableLiveData<List<DatumResponseModelClass>> getData(){
        Log.v("SwipeViewModel","getData method");
        return networkManager.getDataList();
    }
}
