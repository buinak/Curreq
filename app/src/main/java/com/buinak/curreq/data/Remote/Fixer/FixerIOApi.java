package com.buinak.curreq.data.Remote.Fixer;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FixerIOApi {
    String SERVICE_ENDPOINT = "http://data.fixer.io/api/";

    String ACCESS_KEY = "9304adb0c7afee9210aaabe79cae43a2";

    List<String> PERMITTED_CODES = Arrays.asList("EUR", "USD", "NOK", "SEK", "PLN"
            , "UAH", "RUB", "CAD", "DKK", "HRK", "HUF", "GBP", "BGN", "GIP", "RON", "CHF");

    String BASE_CURRENCY = "EUR";

    int TIMEOUT_READ = 60;
    int TIMEOUT_CONNECT = 60;


    @GET("/latest")
    Single<RateResponse> getLatestRates(@Query("access_key") String accessKey);

    @GET("/symbols")
    Single<CurrencyListResponse> getCurrencyList(@Query("access_key") String accessKey);
}
