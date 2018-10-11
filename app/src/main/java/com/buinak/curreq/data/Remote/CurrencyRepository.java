package com.buinak.curreq.data.Remote;

import com.buinak.curreq.data.Remote.Fixer.CurrencyListResponse;
import com.buinak.curreq.data.Remote.Fixer.FixerIOApi;
import com.buinak.curreq.data.Remote.Fixer.RateResponse;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class CurrencyRepository implements RemoteDataSource {

    FixerIOApi fixerIOApi;

    private Map<String, CurrencyRecord> currencyRecordMap;

    public CurrencyRepository(FixerIOApi fixerIOApi) {
        this.fixerIOApi = fixerIOApi;
    }

    @Override
    public Single<RateRequestRecord> getRates(){
        if (currencyRecordMap != null) {
            return fixerIOApi.getLatestRates(FixerIOApi.ACCESS_KEY)
                    .map(this::parseApiRateResponse);
        }
        //shouldn't happen normally, should be initialised
        return null;
    }

    @Override
    public Single<List<CurrencyRecord>> getCurrencyList() {
        return fixerIOApi.getCurrencyList(FixerIOApi.ACCESS_KEY)
                .map(this::parseApiCurrencyListResponse);
    }

    private RateRequestRecord parseApiRateResponse(RateResponse response){
        List<RateRecord> record = new ArrayList<>(response.getRates().entrySet().size());
        for (Map.Entry<String, Double> entry :
                response.getRates().entrySet()) {
            CurrencyRecord currency = currencyRecordMap.get(entry.getKey());
            //FIX!!!
            //fix fiXFIX
            CurrencyRecord baseCurrency = currencyRecordMap.get("EUR");
            record.add(new RateRecord(currency, baseCurrency, entry.getValue()));
        }

        return new RateRequestRecord(record, new Date());
    }

    private List<CurrencyRecord> parseApiCurrencyListResponse(CurrencyListResponse response){
        List<CurrencyRecord> currencyList = new ArrayList<>();
        for (Map.Entry<String, String> entry :
                response.getCurrencyNames().entrySet()) {
            currencyList.add(new CurrencyRecord(entry.getKey(), entry.getValue()));
        }
        return currencyList;
    }
}
