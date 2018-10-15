package com.buinak.curreq.data;

import com.buinak.curreq.data.Local.LocalDataSource;
import com.buinak.curreq.data.Local.LocalDataSourceModule;
import com.buinak.curreq.data.Remote.RemoteDataSource;
import com.buinak.curreq.data.Remote.RemoteDataSourceModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module (includes = {LocalDataSourceModule.class, RemoteDataSourceModule.class})
public class RepositoryModule {

    @Provides
    @Singleton
    DataSource provideDataSource(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        return new Repository(localDataSource, remoteDataSource);
    }
}
