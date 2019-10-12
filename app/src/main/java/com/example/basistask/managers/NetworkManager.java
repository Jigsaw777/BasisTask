package com.example.basistask.managers;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.basistask.data.remote.modelClasses.DataResponseModel;
import com.example.basistask.data.remote.modelClasses.DatumResponseModelClass;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NetworkManager {
//    public RetrofitManager retrofitManager;
    public RetrofitManagerNew retrofitManagerNew;
    private MutableLiveData<List<DatumResponseModelClass>> dataList=new MutableLiveData<>();

    public NetworkManager(){
        retrofitManagerNew=new RetrofitManagerNew();
    }

    public MutableLiveData<List<DatumResponseModelClass>> getDataList(){

        retrofitManagerNew.getApiEndpointService().getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<DataResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<DataResponseModel> dataResponseModelResponse) {
                        if(dataResponseModelResponse.code() == 200){
                            Log.v("NetworkManager","200 returned");
                            dataList.postValue(dataResponseModelResponse.body().getData());
                        }
                        else{
                            Log.v("NetworkManager","error code : "+dataResponseModelResponse.code());
                            dataList.postValue(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dataList.postValue(null);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        return dataList;
    }
}
