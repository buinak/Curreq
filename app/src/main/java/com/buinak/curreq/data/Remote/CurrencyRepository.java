package com.buinak.curreq.data.Remote;

import com.buinak.curreq.data.Remote.Fixer.CurrencyListResponse;
import com.buinak.curreq.data.Remote.Fixer.FixerIOApi;
import com.buinak.curreq.data.Remote.Fixer.RateResponse;
import com.buinak.curreq.entities.CurreqEntity.Currency;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRate;
import com.buinak.curreq.entities.CurreqEntity.Request;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public class CurrencyRepository implements RemoteDataSource {

    //TODO: requests shouldn't be spammable too frequently, make a limitation to like one per day

    private FixerIOApi fixerIOApi;

    private Map<String, Currency> currencyRecordMap;

    public CurrencyRepository(FixerIOApi fixerIOApi) {
        this.fixerIOApi = fixerIOApi;
        currencyRecordMap = new HashMap<>();
    }

    @Override
    public Single<Request> getRates(){
        return fixerIOApi.getLatestRates(FixerIOApi.ACCESS_KEY)
                    .map(this::parseApiRateResponse);
    }



    @Override
    public Single<List<Currency>> getCurrencyList() {
        return fixerIOApi.getCurrencyList(FixerIOApi.ACCESS_KEY)
                .map(this::parseApiCurrencyListResponse);
    }

    @Override
    public void setCurrencyList(List<Currency> currencies) {
        for (Currency currency :
                currencies) {
            currencyRecordMap.put(currency.getCode(), currency);
        }
    }

    @Override
    public boolean isReady() {
        return (currencyRecordMap != null);
    }

    private Request parseApiRateResponse(RateResponse response){
        List<CurrencyExchangeRate> record = new ArrayList<>(response.getRates().entrySet().size());
        Currency baseCurrency = currencyRecordMap.get(FixerIOApi.BASE_CURRENCY);
        for (Map.Entry<String, Double> entry :
                response.getRates().entrySet()) {
            Currency currency = currencyRecordMap.get(entry.getKey());
            record.add(new CurrencyExchangeRate(null, currency, baseCurrency, entry.getValue(), null));
        }

        return new Request(record, new Date());
    }

    private List<Currency> parseApiCurrencyListResponse(CurrencyListResponse response){
        List<Currency> currencyList = new ArrayList<>();
        boolean fillMap = false;
        if (currencyRecordMap == null || currencyRecordMap.size() == 0){
            currencyRecordMap = new HashMap<>();
            fillMap = true;
        }
        for (Map.Entry<String, String> entry :
                response.getCurrencyNames().entrySet()) {
            Currency currency = new Currency(entry.getKey(), entry.getValue());
            currencyList.add(currency);
            if (fillMap) {
                currencyRecordMap.put(entry.getKey(), currency);
            }
        }
        return currencyList;
    }
}
