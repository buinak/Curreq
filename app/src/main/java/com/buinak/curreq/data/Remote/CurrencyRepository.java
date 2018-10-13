package com.buinak.curreq.data.Remote;

import com.buinak.curreq.data.Remote.Fixer.CurrencyListResponse;
import com.buinak.curreq.data.Remote.Fixer.FixerIOApi;
import com.buinak.curreq.data.Remote.Fixer.RateResponse;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public class CurrencyRepository implements RemoteDataSource {

    private FixerIOApi fixerIOApi;

    private Map<String, CurrencyRecord> currencyRecordMap;

    public CurrencyRepository(FixerIOApi fixerIOApi) {
        this.fixerIOApi = fixerIOApi;
        currencyRecordMap = new HashMap<>();
    }

    @Override
    public Single<RateRequestRecord> getRates(){
        return fixerIOApi.getLatestRates(FixerIOApi.ACCESS_KEY)
                    .map(this::parseApiRateResponse);
    }



    @Override
    public Single<List<CurrencyRecord>> getCurrencyList() {
        return fixerIOApi.getCurrencyList(FixerIOApi.ACCESS_KEY)
                .map(this::parseApiCurrencyListResponse);
    }

    @Override
    public void setCurrencyList(List<CurrencyRecord> currencies) {
        for (CurrencyRecord currencyRecord :
                currencies) {
            currencyRecordMap.put(currencyRecord.getCode(), currencyRecord);
        }
    }

    @Override
    public boolean isReady() {
        return (currencyRecordMap != null);
    }

    private RateRequestRecord parseApiRateResponse(RateResponse response){
        List<RateRecord> record = new ArrayList<>(response.getRates().entrySet().size());
        CurrencyRecord baseCurrency = currencyRecordMap.get(FixerIOApi.BASE_CURRENCY);
        for (Map.Entry<String, Double> entry :
                response.getRates().entrySet()) {
            CurrencyRecord currency = currencyRecordMap.get(entry.getKey());
            record.add(new RateRecord(currency, baseCurrency, entry.getValue()));
        }

        return new RateRequestRecord(record, new Date());
    }

    private List<CurrencyRecord> parseApiCurrencyListResponse(CurrencyListResponse response){
        List<CurrencyRecord> currencyList = new ArrayList<>();
        boolean fillMap = false;
        if (currencyRecordMap == null || currencyRecordMap.size() == 0){
            currencyRecordMap = new HashMap<>();
            fillMap = true;
        }
        for (Map.Entry<String, String> entry :
                response.getCurrencyNames().entrySet()) {
            CurrencyRecord currencyRecord = new CurrencyRecord(entry.getKey(), entry.getValue());
            currencyList.add(currencyRecord);
            if (fillMap) {
                currencyRecordMap.put(entry.getKey(), currencyRecord);
            }
        }
        return currencyList;
    }
}
