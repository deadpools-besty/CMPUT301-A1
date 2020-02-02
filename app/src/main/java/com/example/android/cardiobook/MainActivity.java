package com.example.android.cardiobook;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddMeasurementDialog.OnFragmentInteractionListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    private ArrayList<Measurement> measurements = new ArrayList<>();
    MeasurementAdapter adapter = new MeasurementAdapter(this, measurements);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up floating action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    // initialize add measurement dialog
    public void add() {
        AddMeasurementDialog addMeasurement = new AddMeasurementDialog();
        addMeasurement.show(getSupportFragmentManager(), "Add");
    }

    // handle onClickListener for add measurement dialog fragment
    @Override
    public void onAddPressed(Measurement newMeasurement) {
        measurements.add(newMeasurement);
        adapter.notifyDataSetChanged();
    }

}
