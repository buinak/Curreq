package com.buinak.curreq.ui.AddScreen.CurrencyRecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.BitmappedCurrencyRecord;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class CurrencyRecyclerViewAdapter extends RecyclerView.Adapter<CurrencyViewHolder> {

    private List<BitmappedCurrencyRecord> currencyRecordList;

    private Single<Integer> maxWidthObservable;
    private PublishSubject<String> selectedCodesSubject;

    public CurrencyRecyclerViewAdapter(Single<Integer> maxWidthObservable, PublishSubject<String> selectedCodesSubject) {
        this.maxWidthObservable = maxWidthObservable;
        this.selectedCodesSubject = selectedCodesSubject;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        View view = inflater.inflate(R.layout.activity_add_currency_item, parent, false);
        CurrencyViewHolder viewHolder = new CurrencyViewHolder(view, selectedCodesSubject);
        Disposable disposable = maxWidthObservable.subscribe
                (i -> viewHolder.setMaxWidth(i / currencyRecordList.size()));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        holder.bindCurrency(currencyRecordList.get(position));
    }

    @Override
    public int getItemCount() {
        return currencyRecordList.size();
    }

    public void setCurrencyRecordList(List<BitmappedCurrencyRecord> currencyRecordList) {
        this.currencyRecordList = currencyRecordList;
    }
}
