package com.buinak.curreq.ui.MainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import com.buinak.curreq.R;
import com.buinak.curreq.application.CurreqApplication;
import com.buinak.curreq.data.DataSource;
import com.buinak.curreq.ui.AddScreen.AddActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @Inject
    DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        CurreqApplication.inject(this);

        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        });

        dataSource.getAllSavedRecords().observe(this, result -> {
            System.out.println();
            System.out.println();
        });
    }
}
