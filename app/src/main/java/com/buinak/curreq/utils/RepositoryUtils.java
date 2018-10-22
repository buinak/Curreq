package com.buinak.curreq.utils;

import android.support.annotation.NonNull;

import com.buinak.curreq.entities.CurreqEntity.Currency;

import java.util.ArrayList;
import java.util.List;

public class RepositoryUtils {
    @NonNull
    public static List<Currency> filterList(List<Currency> list) {
        List<Currency> newList = new ArrayList<>(Constants.PERMITTED_CODES.size());
        for (String currencyCode : Constants.PERMITTED_CODES) {
            for (Currency currency : list) {
                if (currency.getCode().equals(currencyCode)){
                    newList.add(currency);
                    break;
                }
            }
        }
        return newList;
    }
}
