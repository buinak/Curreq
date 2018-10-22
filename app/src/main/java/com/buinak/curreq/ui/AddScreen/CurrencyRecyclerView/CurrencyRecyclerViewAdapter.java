package com.buinak.curreq.ui.AddScreen.CurrencyRecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.Currency;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public class CurrencyRecyclerViewAdapter extends RecyclerView.Adapter<CurrencyViewHolder> {

    private List<Currency> currencyRecordList;

    private Single<Integer> maxWidthObservable;

    public CurrencyRecyclerViewAdapter(Single<Integer> maxWidthObservable) {
        this.maxWidthObservable = maxWidthObservable;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        View view = inflater.inflate(R.layout.activity_add_currency_item, parent, false);
        CurrencyViewHolder viewHolder = new CurrencyViewHolder(view);
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

    public void setCurrencyRecordList(List<Currency> currencyRecordList) {
        this.currencyRecordList = currencyRecordList;
    }
}
