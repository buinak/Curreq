package com.buinak.curreq.ui.AddScreen.CurrencyRecyclerView;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrencyViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.constraint_layout_currency_item)
    ConstraintLayout constraintLayout;

    @BindView(R.id.textView_currency)
    TextView textViewCurrency;

    @BindView(R.id.imageView_currency)
    ImageView imageViewCurrency;

    private CurrencyRecord currency;


    public CurrencyViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setMaxWidth(int maxWidth){
        constraintLayout.setMaxWidth(maxWidth);
    }

    public void bindCurrency(CurrencyRecord currency){
        this.currency = currency;
        textViewCurrency.setText(currency.getCode());

        String code = currency.getCode().substring(0, 2).toLowerCase();

    }
}
