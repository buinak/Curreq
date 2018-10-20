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
import com.buinak.curreq.ui.AddScreen.AddActivity;
import com.buinak.curreq.ui.MainScreen.RatesRecyclerView.RatesRecyclerViewAdapter;
import com.buinak.curreq.ui.MainScreen.RatesRecyclerView.RatesViewHolder;

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

    private static MainScreenObservableModule mainScreenObservableModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        PublishSubject<String> publishSubject = PublishSubject.create();
        mainScreenObservableModule = new MainScreenObservableModule(publishSubject);
        Disposable disposable = publishSubject.subscribe(recordId -> {
            viewModel.onRateRecordSwapped(recordId);
        });

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RatesRecyclerViewAdapter adapter = new RatesRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        viewModel.getSavedRateRecords().observe(this, results -> {
            adapter.setRateRecords(results);
            adapter.notifyDataSetChanged();
        });

        button.setOnClickListener(v -> viewModel.onResetPressed());
    }


    public static void inject(RatesViewHolder viewHolder) {
        DaggerMainComponent.builder()
                .mainScreenObservableModule(mainScreenObservableModule)
                .build()
                .inject(viewHolder);
    }
}
