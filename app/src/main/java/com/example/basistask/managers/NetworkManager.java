package com.example.basistask.managers;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.basistask.data.remote.modelClasses.DataResponseModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NetworkManager {
//    public RetrofitManager retrofitManager;
    public RetrofitManagerNew retrofitManagerNew;
    public NetworkManager(){
        retrofitManagerNew=new RetrofitManagerNew();
    }

    public void getDataList(final MutableLiveData<DataResponseModel> responseLiveData){

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
                            responseLiveData.postValue(dataResponseModelResponse.body());
                        }
                        else{
                            Log.v("NetworkManager","error code : "+dataResponseModelResponse.code());
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
    }
}
