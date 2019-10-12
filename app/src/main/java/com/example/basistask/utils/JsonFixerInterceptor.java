package com.example.basistask.utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class JsonFixerInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        final Request request = chain.request();
        final Response response = chain.proceed(request);

        // Removing the leading garbage character is easy
        response.body().byteStream().skip(1);

        return response;
    }
}