package com.buinak.curreq.data.Remote.Fixer;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FixerIOApi {
    String SERVICE_ENDPOINT = "http://data.fixer.io/api/";

    String ACCESS_KEY = "9304adb0c7afee9210aaabe79cae43a2";

    int TIMEOUT_READ = 60;
    int TIMEOUT_CONNECT = 60;

    @GET("/latest")
    Single<RateResponse> getCurrency(@Query("access_key") String accessKey);
}
