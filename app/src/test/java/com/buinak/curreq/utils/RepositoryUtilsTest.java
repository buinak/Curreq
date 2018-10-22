package com.buinak.curreq.utils;

import com.buinak.curreq.entities.CurreqEntity.Currency;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

public class RepositoryUtilsTest {

    @Test
    public void filterList() {
        List<String> oldList = Arrays.asList("EUR", "CZK", "ZWD", "HELLO!");
        String checkString = "ZWD";
        String checkString2 = "HELLO!";

        List<Currency> currencies = new ArrayList<>(oldList.size());
        for (String string :
                oldList) {
            Currency currency = new Currency(string, string);
            currencies.add(currency);
        }

        List<Currency> newList = RepositoryUtils.filterList(currencies);
        for (Currency currency :
                newList) {
            String code = currency.getCode();
            assertThat(Constants.PERMITTED_CODES, hasItem(code));
        }
    }
}