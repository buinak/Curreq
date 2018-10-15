package com.buinak.curreq.ui.AddScreen;

import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class AddRepository{

    private DataSource dataSource;

    public AddRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Single<List<List<CurrencyRecord>>> getCurrencyList(){
        return dataSource.requestFilteredCurrencyList()
                .map(this::separateIntoLists);
    }

    List<List<CurrencyRecord>> separateIntoLists(List<CurrencyRecord> list){
        List<List<CurrencyRecord>> resultList = new ArrayList<>();
        List<CurrencyRecord> tempList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            tempList.add(list.get(i));
            if ((i + 1) % Constants.ADD_SCREEN_AMOUNT_OF_CURRENCIES_PER_ROW == 0){
                resultList.add(tempList);
                tempList = new ArrayList<>();
            }
            if ((i + 1) == list.size()){
                if (tempList.size() != 0) {
                    resultList.add(tempList);
                }
            }
        }

        return resultList;
    }
}
