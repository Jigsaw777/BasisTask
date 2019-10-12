package com.example.basistask.managers;

import com.example.basistask.data.remote.endpoints.ApiEndpoints;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitManager {
    private static final String BASE_URL = "https://git.io";
    private  Retrofit retrofit = getRetrofitInstance();

    private Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public ApiEndpoints getApiEndpointService(){
        return retrofit.create(ApiEndpoints.class);
    }
}
