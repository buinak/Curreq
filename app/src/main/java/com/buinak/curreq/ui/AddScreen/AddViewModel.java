package com.buinak.curreq.ui.AddScreen;

import android.arch.lifecycle.ViewModel;

public class AddViewModel extends ViewModel {

    private AddRepository repository;

    public AddViewModel() {
        repository = new AddRepository(this);
    }
}
