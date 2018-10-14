package com.buinak.curreq.ui.AddScreen;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.buinak.curreq.R;
import com.buinak.curreq.entities.CurreqEntity.CurrencyRecord;
import com.buinak.curreq.ui.AddScreen.CurrencyRecyclerView.CurrencyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    CurrencyRecyclerViewAdapter adapter;

    private AddViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ButterKnife.bind(this);
        initialiseRecyclerView();

        viewModel = ViewModelProviders.of(this).get(AddViewModel.class);

        viewModel.getCurrencyList()
                .observe(this, this::updateRecyclerView);

    }

    private void updateRecyclerView(List<CurrencyRecord> list){
        adapter.setCurrencyRecordList(list);
        adapter.notifyDataSetChanged();
    }

    private void initialiseRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CurrencyRecyclerViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }
}
