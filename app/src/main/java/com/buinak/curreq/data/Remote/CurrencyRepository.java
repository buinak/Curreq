package com.buinak.curreq.data.Remote;

import com.buinak.curreq.data.Remote.Fixer.FixerIOApi;
import com.buinak.curreq.data.Remote.Fixer.RateResponse;
import com.buinak.curreq.entities.CurreqEntity.RateRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public class CurrencyRepository implements RemoteDataSource {

    FixerIOApi fixerIOApi;

    public CurrencyRepository(FixerIOApi fixerIOApi) {
        this.fixerIOApi = fixerIOApi;
    }

    @Override
    public Single<RateRequestRecord> getRates(){
        return fixerIOApi.getCurrency(FixerIOApi.ACCESS_KEY)
                .map(this::parseApiResponse);
    }

    private RateRequestRecord parseApiResponse(RateResponse response){
        List<RateRecord> record = new ArrayList<>(response.getRates().entrySet().size());
        for (Map.Entry<String, Double> entry :
                response.getRates().entrySet()) {
            record.add(new RateRecord(entry.getKey(), entry.getValue()));
        }

        return new RateRequestRecord(record, new Date());
    }
}
