package com.buinak.curreq.ui.AddScreen.RowRecyclerView;

import android.view.View;
import android.view.ViewTreeObserver;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.Currency;
import com.buinak.curreq.ui.AddScreen.CurrencyRecyclerView.CurrencyRecyclerViewAdapter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.SingleSubject;

public class RowViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recycler_view_add_row)
    RecyclerView recyclerView;

    private CurrencyRecyclerViewAdapter adapter;


    public RowViewHolder(View itemView, SingleSubject<Integer> maxWidthSubject) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int maxWidth = recyclerView.getMeasuredWidth();
                maxWidthSubject.onSuccess(maxWidth);
            }
        });

        adapter = new CurrencyRecyclerViewAdapter(maxWidthSubject);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    public void bindCurrencies(List<Currency> currencies) {
        adapter.setCurrencyRecordList(currencies);
        recyclerView.setAdapter(adapter);
    }
}
