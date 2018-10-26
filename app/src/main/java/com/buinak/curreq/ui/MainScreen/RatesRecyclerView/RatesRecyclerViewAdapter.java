package com.buinak.curreq.ui.MainScreen.RatesRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRate;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RatesRecyclerViewAdapter extends RecyclerView.Adapter<RatesViewHolder> {

    private List<CurrencyExchangeRate> rateRecords;

    public RatesRecyclerViewAdapter() {
        rateRecords = new ArrayList<>();
    }

    public void setRateRecords(List<CurrencyExchangeRate> rateRecords) {
        this.rateRecords = rateRecords;
    }

    @NonNull
    @Override
    public RatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_main_rate_item, parent, false);
        return new RatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatesViewHolder holder, int position) {
        holder.bindRate(rateRecords.get(position));
    }

    @Override
    public int getItemCount() {
        return rateRecords.size();
    }
}
