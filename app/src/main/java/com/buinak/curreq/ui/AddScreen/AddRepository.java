package com.buinak.curreq.ui.AddScreen;

import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;

import java.util.List;

import io.reactivex.Single;

public class AddRepository{

    private DataSource dataSource;

    public AddRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Single<List<CurrencyRecord>> getCurrencyList(){
        return dataSource.requestFilteredCurrencyList();
    }
}
