package com.buinak.curreq.ui.AddScreen;

import com.buinak.curreq.data.RepositoryComponent;

import javax.inject.Singleton;

import dagger.Component;

@Component (dependencies = RepositoryComponent.class, modules = {AddModule.class})
@Singleton
public interface AddComponent {

    AddRepository provideAddRepository();

    void inject(AddViewModel viewModel);
}
