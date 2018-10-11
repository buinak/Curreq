package com.buinak.curreq.data.Remote.Fixer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by Foreseer on 11-Oct-18.
 */

public class CurrencyListResponse {
    @SerializedName("symbols")
    @Expose
    Map<String, String> currencyNames;

    public CurrencyListResponse(Map<String, String> currencyNames) {
        this.currencyNames = currencyNames;
    }

    public Map<String, String> getCurrencyNames() {
        return currencyNames;
    }

    public void setCurrencyNames(Map<String, String> currencyNames) {
        this.currencyNames = currencyNames;
    }
}
