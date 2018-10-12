package com.buinak.curreq.ui.AddScreen;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.buinak.curreq.R;

public class AddActivity extends AppCompatActivity {

    private AddViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        viewModel = ViewModelProviders.of(this).get(AddViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println();
    }
}
