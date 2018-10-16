package com.buinak.curreq.ui.AddScreen.RowRecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.BitmappedCurrencyRecord;

import java.util.List;

import io.reactivex.subjects.SingleSubject;

public class RowRecyclerViewAdapter extends RecyclerView.Adapter<RowViewHolder> {

    private List<List<BitmappedCurrencyRecord>> rows;

    private View view;

    private final SingleSubject<Integer> maxWidthSubject;

    public RowRecyclerViewAdapter(List<List<BitmappedCurrencyRecord>> rows) {
        this.rows = rows;
        maxWidthSubject = SingleSubject.create();
    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.activity_add_row, parent, false);

        //int maxHeight = parent.getMeasuredHeight() / Constants.ADD_SCREEN_AMOUNT_OF_ROWS_PER_SCREEN;
        return new RowViewHolder(view, maxWidthSubject);
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
        holder.bindCurrencies(rows.get(position));
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    public void setRows(List<List<BitmappedCurrencyRecord>> rows) {
        this.rows = rows;
    }
}
