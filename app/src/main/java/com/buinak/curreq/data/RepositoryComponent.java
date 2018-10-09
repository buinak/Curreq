package com.buinak.curreq.data;

import android.support.v7.app.AppCompatActivity;

import com.buinak.curreq.data.Local.LocalDataSource;
import com.buinak.curreq.data.Remote.Fixer.FixerIOApi;
import com.buinak.curreq.data.Remote.RemoteDataSource;
import com.buinak.curreq.ui.LoadingScreen.LoadingActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = RepositoryModule.class)
public interface RepositoryComponent {
    DataSource getDataSource();

    void inject(LoadingActivity activity);
}
