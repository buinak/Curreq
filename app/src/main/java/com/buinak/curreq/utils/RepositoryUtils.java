package com.buinak.curreq.utils;

import android.support.annotation.NonNull;

import com.buinak.curreq.data.Remote.Fixer.FixerIOApi;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;

import java.util.ArrayList;
import java.util.List;

public class RepositoryUtils {
    @NonNull
    public static List<CurrencyRecord> filterList(List<CurrencyRecord> list) {
        List<CurrencyRecord> newList = new ArrayList<>(FixerIOApi.PERMITTED_CODES.size());
        for (String currencyCode : FixerIOApi.PERMITTED_CODES) {
            for (CurrencyRecord currencyRecord : list) {
                if (currencyRecord.getCode().equals(currencyCode)){
                    newList.add(currencyRecord);
                    break;
                }
            }
        }
        return newList;
    }
}
