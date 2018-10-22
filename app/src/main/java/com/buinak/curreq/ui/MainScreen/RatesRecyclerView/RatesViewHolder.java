package com.buinak.curreq.ui.MainScreen.RatesRecyclerView;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRate;
import com.buinak.curreq.ui.MainScreen.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

public class RatesViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.main_screen_rate_item_card_view)
    CardView cardView;

    @BindView(R.id.imageView_baseCurrency)
    ImageView imageViewBaseCurrency;

    @BindView(R.id.imageView_currency)
    ImageView imageViewCurrency;

    @BindView(R.id.textView_baseCurrency)
    TextView textViewBaseCurrency;

    @BindView(R.id.textView_currency)
    TextView textViewCurrency;

    @BindView(R.id.textView_currencyAmount)
    TextView textViewRate;

    @BindView(R.id.imageButton)
    ImageButton imageButton;

    private CurrencyExchangeRate record;

    @Inject
    PublishSubject<String> publishSubject;

    public RatesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        MainActivity.inject(this);

        imageButton.setOnClickListener(v -> publishSubject.onNext(record.getId()));
    }

    public void bindRate(CurrencyExchangeRate record){
        this.record = record;
        imageViewBaseCurrency.setImageBitmap(record.getBaseCurrency().getBitmap());
        imageViewCurrency.setImageBitmap(record.getCurrency().getBitmap());

        textViewBaseCurrency.setText(record.getBaseCurrency().getCode());
        textViewCurrency.setText(record.getCurrency().getCode());

        String rate = String.valueOf(record.getRate()).substring(0, 5);
        if (rate.charAt(4) == '.'){
            rate = rate.substring(0, 4);
        }
        textViewRate.setText(rate);
    }
}
