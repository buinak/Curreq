package com.buinak.curreq.ui.AddScreen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.Currency;
import com.buinak.curreq.entities.CurreqEntity.CurrencyCountryFlagWrapper;
import com.buinak.curreq.ui.AddScreen.CurrencyRecyclerView.CurrencyViewHolder;
import com.buinak.curreq.ui.AddScreen.RowRecyclerView.RowRecyclerViewAdapter;
import com.buinak.curreq.utils.Constants;
import com.buinak.curreq.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class AddActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_add_allrows)
    RecyclerView recyclerView;
    RowRecyclerViewAdapter adapter;

    private AddViewModel viewModel;

    private Observable<Currency> selectedRates;
    private Disposable disposable;

    private static AddScreenObservablesModule addScreenObservablesModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ButterKnife.bind(this);
        initialiseRecyclerView();

        viewModel = ViewModelProviders.of(this).get(AddViewModel.class);
        viewModel.reset();

        viewModel.getCurrencyLists()
                .observe(this, list -> {
                    if (list == null || list.isEmpty()) {
                        return;
                    }
                    int orientation = getResources().getConfiguration().orientation;
                    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        updateRecyclerView(ListUtils.separateIntoLists
                                (list, Constants.ADD_SCREEN_AMOUNT_OF_CURRENCIES_PER_ROW_PORTRAIT));
                    } else {
                        updateRecyclerView(ListUtils.separateIntoLists
                                (list, Constants.ADD_SCREEN_AMOUNT_OF_CURRENCIES_PER_ROW_LANDSCAPE));
                    }
                });

        viewModel.getFinished()
                .observe(this, isFinished -> {
                    if (isFinished != null) {
                        if (isFinished) {
                            finish();
                        }
                    }
                });

    }

    private void updateRecyclerView(List<List<CurrencyCountryFlagWrapper>> list) {
        adapter.setRows(list);
        adapter.notifyDataSetChanged();
    }

    private void initialiseRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        PublishSubject<Currency> selectedRatesSubject = PublishSubject.create();
        selectedRates = selectedRatesSubject;
        addScreenObservablesModule = new AddScreenObservablesModule(selectedRatesSubject);

        disposable = selectedRates
                .buffer(2)
                .filter(bufferedList -> !bufferedList.get(0).getCode()
                        .equalsIgnoreCase(bufferedList.get(1).getCode()))
                .map(bufferedList -> new Pair<>(bufferedList.get(0), bufferedList.get(1)))
                .subscribe(result -> {
                    viewModel.onRatePairSelected(result);
                });

        adapter = new RowRecyclerViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        disposable = null;
    }

    public static void inject(CurrencyViewHolder viewHolder) {
        DaggerAddComponent.builder()
                .addScreenObservablesModule(addScreenObservablesModule)
                .build()
                .inject(viewHolder);
    }
}
