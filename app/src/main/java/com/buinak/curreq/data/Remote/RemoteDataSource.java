package com.buinak.curreq.data.Remote;

import com.buinak.curreq.entities.CurreqEntity.RateRequestRecord;

import io.reactivex.Single;

public interface RemoteDataSource {
    Single<RateRequestRecord> getRates();
}
