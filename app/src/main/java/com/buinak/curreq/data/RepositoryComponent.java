package com.buinak.curreq.data;

import dagger.Component;

@Component (modules = RepositoryModule.class)
public interface RepositoryComponent {
    DataSource getDataSource();
}
