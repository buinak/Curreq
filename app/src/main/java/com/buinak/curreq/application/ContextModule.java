package com.buinak.curreq.application;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Foreseer on 16-Oct-18.
 */

@Module
public class ContextModule {
    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext(){
        return context;
    }
}
