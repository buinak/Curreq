package com.buinak.curreq.data;

import com.buinak.curreq.ui.LoadingScreen.LoadingRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = RepositoryModule.class)
public interface RepositoryComponent {
    DataSource getDataSource();

    void inject(LoadingRepository loadingRepository);
}
