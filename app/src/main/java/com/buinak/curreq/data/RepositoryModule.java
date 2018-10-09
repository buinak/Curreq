package com.buinak.curreq.data;

import com.buinak.curreq.data.Local.CurrencyDatabase;
import com.buinak.curreq.data.Local.LocalDataSource;
import com.buinak.curreq.data.Local.LocalDataSourceModule;
import com.buinak.curreq.data.Remote.CurrencyRepository;
import com.buinak.curreq.data.Remote.Fixer.FixerIOApi;
import com.buinak.curreq.data.Remote.RemoteDataSource;
import com.buinak.curreq.data.Remote.RemoteDataSourceModule;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module (includes = {LocalDataSourceModule.class, RemoteDataSourceModule.class})
public class RepositoryModule {

    @Provides
    @Singleton
    public DataSource provideDataSource(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        Repository result = new Repository(localDataSource, remoteDataSource);
        return result;
    }
}
