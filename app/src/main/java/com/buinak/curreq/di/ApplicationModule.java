package com.buinak.curreq.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Foreseer on 16-Oct-18.
 */

@Module
public class ApplicationModule {
    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext(){
        return context;
    }
}
