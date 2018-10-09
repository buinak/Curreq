package com.buinak.curreq.application;

import android.app.Application;

import com.buinak.curreq.data.DaggerRepositoryComponent;
import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.data.RepositoryComponent;
import com.buinak.curreq.data.RepositoryModule;

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

    public static RepositoryComponent getRepositoryComponent(DataSource.DataSourceListener listener){
        return DaggerRepositoryComponent.builder()
                .repositoryModule(new RepositoryModule(listener))
                .build();
    }
}
