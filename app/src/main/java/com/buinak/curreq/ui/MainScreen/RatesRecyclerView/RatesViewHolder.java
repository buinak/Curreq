package com.buinak.curreq.ui.MainScreen.RatesRecyclerView;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.SavedRateRecordBitmapWrapper;
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

    private SavedRateRecordBitmapWrapper record;

    @Inject
    PublishSubject<String> publishSubject;

    public RatesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        MainActivity.inject(this);

        imageButton.setOnClickListener(v -> publishSubject.onNext(record.getId()));
    }

    public void bindRate(SavedRateRecordBitmapWrapper record){
        this.record = record;
        imageViewBaseCurrency.setImageBitmap(record.getBaseCurrencyRecord().getBitmap());
        imageViewCurrency.setImageBitmap(record.getCurrencyRecord().getBitmap());

        textViewBaseCurrency.setText(record.getBaseCurrencyRecord().getCode());
        textViewCurrency.setText(record.getCurrencyRecord().getCode());

        String rate = String.valueOf(record.getRate()).substring(0, 5);
        textViewRate.setText(rate);
    }
}
