package com.buinak.curreq.ui.AddScreen.RowRecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;

import java.util.List;

public class RowRecyclerViewAdapter extends RecyclerView.Adapter<RowViewHolder> {

    private List<List<CurrencyRecord>> rows;

    private int maxWidth;

    private View view;

    public RowRecyclerViewAdapter(List<List<CurrencyRecord>> rows) {
        this.rows = rows;
    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.activity_add_row, parent, false);

        maxWidth = (int) ((parent.getMeasuredWidth() / 3) * 0.90);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {

        holder.bindCurrencies(rows.get(position), maxWidth);
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    public void setRows(List<List<CurrencyRecord>> rows) {
        this.rows = rows;
    }
}
