package com.buinak.curreq.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CurreqApplication extends Application {

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
    }
}
