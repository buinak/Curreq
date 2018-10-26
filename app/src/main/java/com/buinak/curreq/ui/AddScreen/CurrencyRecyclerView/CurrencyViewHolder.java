package com.buinak.curreq.ui.AddScreen.CurrencyRecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.Currency;
import com.buinak.curreq.ui.AddScreen.AddActivity;
import com.buinak.curreq.utils.ViewUtils;

import javax.inject.Inject;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

public class CurrencyViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.constraint_layout_currency_item)
    ConstraintLayout constraintLayout;

    @BindView(R.id.textView_currency)
    TextView textViewCurrency;

    @BindView(R.id.imageView_currency)
    ImageView imageViewCurrency;

    @BindView(R.id.currency_item_cardView)
    CardView cardView;

    private boolean isToggled;

    private static final int DURATION = 250;

    @Inject
    PublishSubject<Currency> selectedCodesSubject;

    private Currency currency;


    public CurrencyViewHolder(View itemView) {
        super(itemView);

        AddActivity.inject(this);

        ButterKnife.bind(this, itemView);
        isToggled = false;

        cardView.setOnClickListener(v -> {
            if (!isToggled) {
                ViewUtils.animateCardViewColourChange(cardView,
                        cardView.getResources().getColor(R.color.colorPrimaryLight),
                        cardView.getResources().getColor(R.color.colorAccent),
                        DURATION);
                ViewUtils.animateTextColourChange(textViewCurrency,
                        textViewCurrency.getResources().getColor(R.color.white),
                        textViewCurrency.getResources().getColor(R.color.colorPrimary),
                        DURATION);
                isToggled = true;
            } else {
                ViewUtils.animateCardViewColourChange(cardView,
                        cardView.getResources().getColor(R.color.colorAccent),
                        cardView.getResources().getColor(R.color.colorPrimaryLight),
                        DURATION);
                ViewUtils.animateTextColourChange(textViewCurrency,
                        textViewCurrency.getResources().getColor(R.color.colorPrimary),
                        textViewCurrency.getResources().getColor(R.color.white),
                        DURATION);
                isToggled = false;
            }
            selectedCodesSubject.onNext(new Currency(currency.getCode(), currency.getName()));
        });
    }

    public void setMaxWidth(int maxWidth){
        constraintLayout.setMaxWidth(maxWidth);
    }

    public void bindCurrency(Currency currency){
        this.currency = currency;
        textViewCurrency.setText(currency.getCode());

        String code = currency.getCode().substring(0, 2).toLowerCase();
        imageViewCurrency.setImageBitmap(currency.getBitmap());
    }
}
