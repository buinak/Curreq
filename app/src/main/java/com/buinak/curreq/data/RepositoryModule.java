package com.buinak.curreq.data;

import com.buinak.curreq.data.Local.LocalDataSource;
import com.buinak.curreq.data.Local.LocalDataSourceModule;
import com.buinak.curreq.data.Remote.RemoteDataSource;
import com.buinak.curreq.data.Remote.RemoteDataSourceModule;

import dagger.Module;
import dagger.Provides;

@Module (includes = {LocalDataSourceModule.class, RemoteDataSourceModule.class})
public class RepositoryModule {

    private final DataSource.DataSourceListener listener;

    public RepositoryModule(DataSource.DataSourceListener listener) {
        this.listener = listener;
    }

    @Provides
    DataSource provideDataSource(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        Repository result = new Repository(listener, localDataSource, remoteDataSource);
        return result;
    }
}
