package com.buinak.curreq.data.Remote.Fixer;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    @Provides
    static FixerIOApi provideApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(FixerIOApi.TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(FixerIOApi.TIMEOUT_CONNECT, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(FixerIOApi.SERVICE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(FixerIOApi.class);
    }
}
