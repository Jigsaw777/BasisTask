package com.example.basistask.managers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.basistask.data.remote.modelClasses.DataResponseModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NetworkManager {
    public RetrofitManager retrofitManager;

    public NetworkManager(){
        retrofitManager=new RetrofitManager();
    }

    public LiveData<DataResponseModel> getDataList(final MutableLiveData<DataResponseModel> responseLiveData){

        retrofitManager.getApiEndpointService().getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<DataResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<DataResponseModel> dataResponseModelResponse) {
                        if(dataResponseModelResponse.code() == 200){
                            responseLiveData.postValue(dataResponseModelResponse.body());
                        }
                        else{
                            responseLiveData.postValue(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        responseLiveData.postValue(null);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        return responseLiveData;
    }
}
