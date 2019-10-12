package com.example.basistask.managers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;

import com.example.basistask.data.remote.modelClasses.DataResponseModel;
import com.example.basistask.data.remote.modelClasses.DatumResponseModelClass;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

//this class abstracts the network related queries from the view.
public class NetworkManager {
    public RetrofitManagerNew retrofitManagerNew;

    // a live data which is reponsible for updating the values at view
    private MutableLiveData<List<DatumResponseModelClass>> dataList=new MutableLiveData<>();

    //context is not necessary here. I have used it to show a toast
    private Context appContext;

    public NetworkManager(Context appContext){
        retrofitManagerNew=new RetrofitManagerNew();
        this.appContext=appContext;
    }

    public MutableLiveData<List<DatumResponseModelClass>> getDataList(){

        //call the method ar retrofitManagerNew to get the data
        retrofitManagerNew.getApiEndpointService().getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<DataResponseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<DataResponseModel> dataResponseModelResponse) {
                        //this is the ideal case when we receive the correct response
                        if(dataResponseModelResponse.code() == 200){
                            Log.v("NetworkManager","200 returned");

                            //this case is when there is no internet. need to research on it further
                            if(dataResponseModelResponse.body() == null){
                                Toast.makeText(appContext,"Unable to fetch data",Toast.LENGTH_LONG).show();
                            }
                            else{
                                dataList.postValue(dataResponseModelResponse.body().getData());
                            }
                        }
                        //when we get code other than 200
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
