package com.buinak.curreq.ui.AddScreen.CurrencyRecyclerView;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.BitmappedCurrencyRecord;
import com.buinak.curreq.utils.ViewUtils;

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

    private PublishSubject<String> selectedCodesSubject;

    private BitmappedCurrencyRecord currency;


    public CurrencyViewHolder(View itemView, PublishSubject<String> selectedCodesSubject) {
        super(itemView);

        this.selectedCodesSubject = selectedCodesSubject;

        ButterKnife.bind(this, itemView);
        isToggled = false;

        cardView.setOnClickListener(v -> {
            if (!isToggled) {
                ViewUtils.animateViewColourChange(cardView,
                        cardView.getResources().getColor(R.color.lighter_gray),
                        cardView.getResources().getColor(R.color.colorAccent),
                        150);
                isToggled = true;
            } else {
                ViewUtils.animateViewColourChange(cardView,
                        cardView.getResources().getColor(R.color.colorAccent),
                        cardView.getResources().getColor(R.color.lighter_gray),
                        150);
                isToggled = false;
            }
            selectedCodesSubject.onNext(currency.getCode());
        });
    }

    public void setMaxWidth(int maxWidth){
        constraintLayout.setMaxWidth(maxWidth);
    }

    public void bindCurrency(BitmappedCurrencyRecord currency){
        this.currency = currency;
        textViewCurrency.setText(currency.getCode());

        String code = currency.getCode().substring(0, 2).toLowerCase();
        imageViewCurrency.setImageBitmap(currency.getBitmap());
    }
}
