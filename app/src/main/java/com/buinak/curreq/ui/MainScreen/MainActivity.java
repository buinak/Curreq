package com.buinak.curreq.ui.MainScreen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.SavedRateRecordBitmapWrapper;
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
    private List<SavedRateRecordBitmapWrapper> data;

    private RatesRecyclerViewAdapter adapter;


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
        adapter = new RatesRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        viewModel.getSavedRateRecords().observe(this, results -> {
            if (results != null) {
                data = results;
                adapter.setRateRecords(results);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "GOT NULL", Toast.LENGTH_SHORT).show();
            }
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
