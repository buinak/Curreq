package com.buinak.curreq.ui.AddScreen.RowRecyclerView;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.ui.AddScreen.CurrencyRecyclerView.CurrencyRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RowViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recycler_view_add_row)
    RecyclerView recyclerView;

    private List<CurrencyRecord> currencies;

    public RowViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void bindCurrencies(List<CurrencyRecord> currencies, int maxWidth){
        this.currencies = currencies;

        CurrencyRecyclerViewAdapter adapter = new CurrencyRecyclerViewAdapter(currencies, maxWidth);
        recyclerView.setAdapter(adapter);
    }
}
