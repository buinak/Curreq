package com.buinak.curreq.data.Remote;

import com.buinak.curreq.data.Remote.Fixer.FixerIOApi;
import com.buinak.curreq.data.Remote.Fixer.RateResponse;
import com.buinak.curreq.entities.CurreqEntity.RateRecord;
import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyRepository implements RemoteDataSource {

    @Inject
    FixerIOApi fixerIOApi;

    private final int TIMEOUT_READ = 60;
    private final int TIMEOUT_CONNECT = 60;

    @Inject
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
