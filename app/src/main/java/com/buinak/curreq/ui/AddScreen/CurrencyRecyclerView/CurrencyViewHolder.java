package com.buinak.curreq.ui.AddScreen.CurrencyRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrencyViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.textView_currency)
    TextView currencyTextView;

    private CurrencyRecord currency;

    public CurrencyViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bindCurrency(CurrencyRecord currency){
        this.currency = currency;

        currencyTextView.setText(currency.getName());
    }
}
