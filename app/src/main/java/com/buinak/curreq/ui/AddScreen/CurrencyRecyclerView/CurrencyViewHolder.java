package com.buinak.curreq.ui.AddScreen.CurrencyRecyclerView;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrencyViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.constraint_layout_currency_item)
    ConstraintLayout constraintLayout;

    @BindView(R.id.textView_currency)
    TextView currencyTextView;

    private CurrencyRecord currency;
    private int maxWidth;

    public CurrencyViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void bindCurrency(CurrencyRecord currency){
        this.currency = currency;
        constraintLayout.setMaxWidth(maxWidth);
        currencyTextView.setText(currency.getCode());
    }
}
