package com.buinak.curreq.ui.AddScreen.CurrencyRecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;

import java.util.List;

public class CurrencyRecyclerViewAdapter extends RecyclerView.Adapter<CurrencyViewHolder> {

    private List<CurrencyRecord> currencyRecordList;

    private int maxWidth;

    public CurrencyRecyclerViewAdapter(List<CurrencyRecord> currencyRecordList, int maxWidth) {
        this.currencyRecordList = currencyRecordList;
        this.maxWidth = maxWidth;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.activity_add_currency_item, parent, false);
        CurrencyViewHolder viewHolder = new CurrencyViewHolder(view);
        viewHolder.setMaxWidth(maxWidth);
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

    public void setCurrencyRecordList(List<CurrencyRecord> currencyRecordList) {
        this.currencyRecordList = currencyRecordList;
    }
}
