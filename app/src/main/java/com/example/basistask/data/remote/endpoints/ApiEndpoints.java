package com.example.basistask.data.remote.endpoints;

import com.example.basistask.data.remote.modelClasses.DataResponseModel;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ApiEndpoints {

    @GET("fjaqJ")
    Observable<Response<DataResponseModel>> getData();
}
