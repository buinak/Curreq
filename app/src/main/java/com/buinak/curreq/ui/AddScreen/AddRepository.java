package com.buinak.curreq.ui.AddScreen;

import com.buinak.curreq.data.DataSource;

import javax.inject.Inject;

public class AddRepository{

    @Inject
    DataSource dataSource;

    private AddViewModel viewModel;

    public AddRepository(AddViewModel viewModel) {
        this.viewModel = viewModel;

    }

}
