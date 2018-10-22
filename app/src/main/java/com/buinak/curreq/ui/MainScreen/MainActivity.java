package com.buinak.curreq.ui.MainScreen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRate;
import com.buinak.curreq.ui.AddScreen.AddActivity;
import com.buinak.curreq.ui.MainScreen.RatesRecyclerView.RatesRecyclerViewAdapter;
import com.buinak.curreq.ui.MainScreen.RatesRecyclerView.RatesViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.main_screen_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.RESET_REMOVE_DEBUG_BUTTON)
    Button button;

    private MainViewModel viewModel;

    private RatesRecyclerViewAdapter adapter;

    private static MainScreenObservableModule mainScreenObservableModule;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mainScreenObservableModule = new MainScreenObservableModule(PublishSubject.create());
        disposable = mainScreenObservableModule.provideObservableClickedCurrencyRecords()
                .subscribe(recordId -> viewModel.onRateRecordSwapped(recordId));

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        initialiseRecyclerView();
        viewModel.getSavedRateRecords().observe(this, results -> {
            if (results != null) {
                updateRecyclerView(results);
            }
        });

        floatingActionButton.setOnClickListener(v -> {
            startAddActivity();
        });

        button.setOnClickListener(v -> viewModel.onResetPressed());
    }

    private void updateRecyclerView(List<CurrencyExchangeRate> results) {
        adapter.setRateRecords(results);
        adapter.notifyDataSetChanged();
    }

    private void startAddActivity() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    private void initialiseRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RatesRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
    }


    public static void inject(RatesViewHolder viewHolder) {
        DaggerMainComponent.builder()
                .mainScreenObservableModule(mainScreenObservableModule)
                .build()
                .inject(viewHolder);
    }
}
