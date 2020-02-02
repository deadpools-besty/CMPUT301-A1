package com.example.android.cardiobook;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddMeasurementDialog.OnFragmentInteractionListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Measurement> measurements;
    MeasurementAdapter adapter;
    SharedPreferences sharedPreferences;

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

        // load data
        readMeasurements();

        // build RecyclerView
        adapter = new MeasurementAdapter(this, measurements);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteMeasurement:
                adapter.setOnItemClickListener(new MeasurementAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        measurements.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // initialize add measurement dialog
    public void add() {
        AddMeasurementDialog addMeasurement = new AddMeasurementDialog();
        addMeasurement.show(getSupportFragmentManager(), "Add");
    }

    private void saveMeasurements() {
        sharedPreferences = getSharedPreferences("key", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(measurements);
        editor.putString("Measurements", json);
        Log.d("Save", "called");
        editor.apply();
    }

    private void readMeasurements() {

        sharedPreferences = getSharedPreferences("key", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Measurements", null);
        Type type = new TypeToken<ArrayList<Measurement>>(){}.getType();
        measurements = gson.fromJson(json, type);

        if (measurements == null) {
            measurements = new ArrayList<>();
        }
    }

    // handle onClickListener for add measurement dialog fragment
    @Override
    public void onAddPressed(Measurement newMeasurement) {
        measurements.add(newMeasurement);
        adapter.notifyDataSetChanged();
        saveMeasurements();
    }

}
