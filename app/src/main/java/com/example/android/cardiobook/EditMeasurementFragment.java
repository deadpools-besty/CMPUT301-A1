package com.example.android.cardiobook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class EditMeasurementFragment extends DialogFragment {


    private EditMeasurementFragment.OnFragmentInteractionListener listener;
    private TextView date;
    private TextView time;
    private EditText HR, dBP, sBP, comment;
    private Measurement measurementObject;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    public interface OnFragmentInteractionListener {
        void onEditPressed(Measurement measurement);
    }

    static EditMeasurementFragment newInstance(Measurement measurement) {
        Bundle args = new Bundle();
        args.putSerializable("measurements", measurement);
        EditMeasurementFragment fragment = new EditMeasurementFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof EditMeasurementFragment.OnFragmentInteractionListener){
            listener = (EditMeasurementFragment.OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + "must implement OnFragmentInteractionListener");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // bind all views
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_measurement_dialog, null);
        date = view.findViewById(R.id.editDate);
        time = view.findViewById(R.id.editTime);
        HR = view.findViewById(R.id.heartRateEditText);
        dBP = view.findViewById(R.id.diastolicBPEditText);
        sBP = view.findViewById(R.id.systolicBPEditText);
        comment = view.findViewById(R.id.commentEditText);

        // grabbing and binding data from existing measurement object
        assert getArguments() != null;
        measurementObject = (Measurement) getArguments().getSerializable("measurements");
        date.setText(measurementObject.getDate());
        time.setText(measurementObject.getTime());
        Log.d("MeasHR", "onCreateDialog: " + measurementObject.getHeartRate());
        HR.setText(String.valueOf(measurementObject.getHeartRate()));
        dBP.setText(String.valueOf(measurementObject.getDiastolicBP().getBP()));
        sBP.setText(String.valueOf(measurementObject.getSystolicBP().getBP()));
        comment.setText(measurementObject.getComment());

        // Calendar stuff
        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        final int currentMinute = cal.get(Calendar.MINUTE);


        // on click listener to be able to set the date
        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                DatePickerDialog dateDialog = new DatePickerDialog(
                        getContext(),
                        dateSetListener,
                        year, month, day);
                dateDialog.show();

            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateStr = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                date.setText(dateStr);
            }
        };

        // On click listener to set the time
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker = new TimePickerDialog(getContext(), timeSetListener, currentHour, currentMinute, true);
                timePicker.show();
            }
        });
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timeStr = String.format("%02d:%02d", hourOfDay, minute);
                time.setText(timeStr);
            }
        };

        // Build alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add Measurement")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // build the new measurement object given the values


                        String dateStr = date.getText().toString();
                        String timeStr = time.getText().toString();

                        boolean validHR = !HR.getText().toString().isEmpty();
                        boolean validdBP = !dBP.getText().toString().isEmpty();
                        boolean validsBP = !sBP.getText().toString().isEmpty();
                        if (!validHR || !validdBP || !validsBP) {
                            setErrorMessages();
                            Toast.makeText(getContext(), "Please input valid numbers", Toast.LENGTH_LONG).show();
                        }
                        else {
                            int HRInt = Integer.valueOf(HR.getText().toString());
                            int dBPInt = Integer.valueOf(dBP.getText().toString());
                            int sBPInt = Integer.valueOf(sBP.getText().toString());
                            measurementObject.setDate(dateStr);
                            measurementObject.setTime(timeStr);
                            measurementObject.setDiastolicBP(dBPInt);
                            measurementObject.setSystolicBP(sBPInt);
                            measurementObject.setHeartRate(HRInt);

                            if (comment.getText().toString().isEmpty()) {
                                listener.onEditPressed(measurementObject);
                            } else {
                                String commentStr = comment.getText().toString();
                                measurementObject.setComment(commentStr);
                                listener.onEditPressed(measurementObject);
                            }
                        }
                    }
                }).create();

    }


    private void setErrorMessages() {
        boolean validHR = HR.getText().toString().isEmpty();
        boolean validdBP = dBP.getText().toString().isEmpty();
        boolean validsBP = sBP.getText().toString().isEmpty();
        String dBPError = "Please enter a valid diastolic blood pressure";
        String sBPError = "Please enter a valid systolic blood pressure";
        String HRError = "Please enter a valid heart rate";

        if(!validsBP && !validdBP && !validHR) {
            sBP.setError(sBPError);
            dBP.setError(dBPError);
            HR.setError(HRError);
        }
        else if (!validdBP && !validsBP) {
            sBP.setError(sBPError);
            dBP.setError(dBPError);
        }
        else if (!validdBP && !validHR) {
            dBP.setError(dBPError);
            HR.setError(HRError);
        }
        else if (!validsBP && !validHR) {
            HR.setError(HRError);
            sBP.setError(sBPError);
        }
        else if (!validdBP) {
            dBP.setError(dBPError);
        }
        else if (!validsBP) {
            sBP.setError(sBPError);
        }
        else if (!validHR) {
            HR.setError(HRError);
        }
    }


}