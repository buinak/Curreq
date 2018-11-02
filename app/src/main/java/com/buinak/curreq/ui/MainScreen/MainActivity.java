package com.buinak.curreq.ui.MainScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.CurrencyExchangeRate;
import com.buinak.curreq.ui.AddScreen.AddActivity;
import com.buinak.curreq.ui.MainScreen.RatesRecyclerView.RatesRecyclerViewAdapter;
import com.buinak.curreq.ui.MainScreen.RatesRecyclerView.RatesViewHolder;
import com.buinak.curreq.ui.SettingsScreen.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
    Button resetButton;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.testView_date)
    TextView textViewDate;

    @BindView(R.id.imageButton_settings)
    ImageButton imageButtonSettings;

    private MainViewModel viewModel;

    private RatesRecyclerViewAdapter adapter;

    private static MainScreenObservableModule mainScreenObservableModule;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.onBind();
        initialiseRecyclerView();
        viewModel.getSavedRateRecords()
                .observe(this, results -> {
            if (results != null) {
                updateRecyclerView(results);
            }
        });

        viewModel.getLastUpdatedDate()
                .observe(this, date -> {
            if (date != null) {
                String dateString = new SimpleDateFormat("dd.MM HH:mm").format(date);
                textViewDate.setText(dateString);
            }
        });

        mainScreenObservableModule = new MainScreenObservableModule(PublishSubject.create());
        disposable = mainScreenObservableModule.provideObservableClickedCurrencyRecords()
                .subscribe(recordId -> viewModel.onRateRecordSwapped(recordId));

        floatingActionButton.setOnClickListener(v -> startAddActivity());
        resetButton.setOnClickListener(v -> viewModel.onResetPressed());
        swipeRefreshLayout.setOnRefreshListener(() -> viewModel.onUpdatePressed());
        imageButtonSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

        viewModel.getIsUpdating().observe(this, isUpdating -> swipeRefreshLayout.setRefreshing(isUpdating));
        viewModel.getToastLiveData().observe(this, string -> Toast.makeText(this, string, Toast.LENGTH_LONG).show());

    }

    private void updateRecyclerView(List<CurrencyExchangeRate> results) {
        adapter.setRateRecords(results);
        adapter.notifyDataSetChanged();
    }

    private void startAddActivity() {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    private void initialiseRecyclerView() {
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
