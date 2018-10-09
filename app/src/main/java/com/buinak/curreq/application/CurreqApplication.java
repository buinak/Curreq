package com.buinak.curreq.application;

import android.app.Application;

import com.buinak.curreq.data.DaggerRepositoryComponent;
import com.buinak.curreq.data.RepositoryComponent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CurreqApplication extends Application {

    private static CurreqApplication application;

    public static final String DATABASE_NAME = "curreq_db.realm";

    private RepositoryComponent repositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        initialiseRepositoryComponent();

        Realm.init(this);
        RealmConfiguration databaseConfig = new RealmConfiguration.Builder()
                .name(DATABASE_NAME)
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(databaseConfig);
    }

    public static CurreqApplication getApplication() {
        return application;
    }

    private void initialiseRepositoryComponent(){
        repositoryComponent = DaggerRepositoryComponent.builder()
                .build();
    }

    public RepositoryComponent getRepositoryComponent(){
        return repositoryComponent;
    }
}
