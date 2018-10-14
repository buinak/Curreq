package com.buinak.curreq.application;

import android.app.Application;

import com.buinak.curreq.data.DaggerRepositoryComponent;
import com.buinak.curreq.data.RepositoryComponent;
import com.buinak.curreq.ui.AddScreen.AddViewModel;
import com.buinak.curreq.ui.AddScreen.DaggerAddComponent;
import com.buinak.curreq.ui.LoadingScreen.DaggerLoadingComponent;
import com.buinak.curreq.ui.LoadingScreen.LoadingViewModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CurreqApplication extends Application {

    private static CurreqApplication application;

    public static final String DATABASE_NAME = "curreq_db.realm";

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;


        Realm.init(this);
        RealmConfiguration databaseConfig = new RealmConfiguration.Builder()
                .name(DATABASE_NAME)
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(databaseConfig);
    }

    public static void inject(LoadingViewModel viewModel){
        RepositoryComponent repositoryComponent = DaggerRepositoryComponent.builder().build();

        DaggerLoadingComponent.builder()
                .repositoryComponent(repositoryComponent)
                .build()
                .inject(viewModel);
    }

    public static void inject(AddViewModel viewModel){
        RepositoryComponent repositoryComponent = DaggerRepositoryComponent.builder().build();

        DaggerAddComponent.builder()
                .repositoryComponent(repositoryComponent)
                .build()
                .inject(viewModel);
    }
}
