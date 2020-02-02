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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddMeasurementDialog.OnFragmentInteractionListener, EditMeasurementFragment.OnFragmentInteractionListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Measurement> measurements;
    private int editItemPosition;
    MeasurementAdapter adapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load data
        readMeasurements();

        // Set up floating action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        // build RecyclerView
        adapter = new MeasurementAdapter(this, measurements);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // itemclick listener to edit items
        adapter.setOnItemClickListener(new MeasurementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                editItemPosition = position;
                Measurement item = measurements.get(position);
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                EditMeasurementFragment measurementFragment = EditMeasurementFragment.newInstance(item);
                measurementFragment.show(fm, "EDIT");
            }
        });

    }


    // initialize add measurement dialog
    public void add() {
        AddMeasurementDialog addMeasurement = new AddMeasurementDialog();
        addMeasurement.show(getSupportFragmentManager(), "Add");
    }
    public void edit(int position) {


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

    @Override
    public void onEditPressed(Measurement measurement) {
        measurements.set(editItemPosition, measurement);
        adapter.notifyDataSetChanged();
        saveMeasurements();
    }
}
