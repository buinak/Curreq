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

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module (includes = {LocalDataSourceModule.class, RemoteDataSourceModule.class})
public class RepositoryModule {

    @Inject
    public RepositoryModule(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Provides
    public DataSource provideDataSource() {
        return new Repository(localDataSource, remoteDataSource);
    }
}
