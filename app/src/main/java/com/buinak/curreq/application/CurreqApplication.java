package com.buinak.curreq.application;

import android.app.Application;

import com.buinak.curreq.di.ApplicationComponent;
import com.buinak.curreq.di.DaggerApplicationComponent;
import com.buinak.curreq.ui.AddScreen.AddViewModel;
import com.buinak.curreq.ui.LoadingScreen.LoadingViewModel;
import com.buinak.curreq.ui.MainScreen.MainViewModel;
import com.buinak.curreq.ui.SettingsScreen.SettingsViewModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CurreqApplication extends Application {

    private static ApplicationComponent repositoryComponent;
    private static ApplicationModule applicationModule;

    public static final String DATABASE_NAME = "curreq_db.realm";

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration databaseConfig = new RealmConfiguration.Builder()
                .name(DATABASE_NAME)
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(databaseConfig);

        applicationModule = new ApplicationModule(this);

        repositoryComponent = DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .build();
    }

    public static void inject(LoadingViewModel viewModel){
        repositoryComponent.inject(viewModel);
    }

    public static void inject(AddViewModel viewModel){
        repositoryComponent.inject(viewModel);
    }

    public static void inject(MainViewModel viewModel){
        repositoryComponent.inject(viewModel);
    }

    public static void inject(SettingsViewModel viewModel){
        repositoryComponent.inject(viewModel);
    }


}
