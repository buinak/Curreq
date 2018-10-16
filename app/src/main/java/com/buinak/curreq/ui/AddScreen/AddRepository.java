package com.buinak.curreq.ui.AddScreen;

import android.graphics.Bitmap;

import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.entities.CurreqEntity.BitmapWrapper;
import com.buinak.curreq.entities.CurreqEntity.BitmappedCurrencyRecord;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class AddRepository{

    private DataSource dataSource;

    public AddRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Single<List<BitmappedCurrencyRecord>> getBitmappedCurrencyRecords(){
        return getCurrencyList()
                .zipWith(getBitmaps(), (currencyRecords, bitmapWrappers) -> {
            List<BitmappedCurrencyRecord> bitmappedCurrencyRecords = new ArrayList<>();
            for (CurrencyRecord currencyRecord : currencyRecords) {
                Bitmap bitmap = null;
                for (BitmapWrapper bitmapWrapper :  bitmapWrappers) {
                    if (bitmapWrapper.getCode().equalsIgnoreCase(currencyRecord.getCode().substring(0, 2))){
                        bitmap = bitmapWrapper.getBitmap();
                        break;
                    }
                }
                bitmappedCurrencyRecords.add(new BitmappedCurrencyRecord(currencyRecord, bitmap));
            }
            return bitmappedCurrencyRecords;
        });
    }

    public Single<List<BitmapWrapper>> getBitmaps(){
        return dataSource.getAllBitmaps();
    }

    public Single<List<CurrencyRecord>> getCurrencyList(){
        return dataSource.requestFilteredCurrencyList();
    }
}
