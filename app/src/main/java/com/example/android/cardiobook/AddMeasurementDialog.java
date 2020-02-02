package com.example.android.cardiobook;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class AddMeasurementDialog extends DialogFragment {


    private OnFragmentInteractionListener listener;
    private TextView date;
    private TextView time;
    private EditText HR, dBP, sBP, comment;
    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;

    public interface OnFragmentInteractionListener {
        void onAddPressed(Measurement newMeasurement);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof  OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
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

        // on click listener to be able to set the date
        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

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
                month += 1;

                String dateStr = year + "-" + month + "-" + dayOfMonth;
                date.setText(dateStr);
            }
        };

        // On click listener to set the time
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePicker = new TimePickerDialog(getContext(), timeSetListener, currentHour, currentMinute, true);
                timePicker.show();
            }
        });
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timeStr = hourOfDay + ":" + minute;
                time.setText(timeStr);
            }
        };

        // Build alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add Measurement")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // build the new measurement object given the values
                        String dateStr = date.getText().toString();
                        String timeStr = time.getText().toString();
                        int HRInt = Integer.valueOf(HR.getText().toString());
                        int dBPInt = Integer.valueOf(dBP.getText().toString());
                        int sBPInt = Integer.valueOf(sBP.getText().toString());
                        if (!comment.getText().toString().isEmpty()) {
                            listener.onAddPressed(new Measurement(dateStr, timeStr, sBPInt, dBPInt, HRInt));
                        }
                        else {
                            String commentStr = comment.getText().toString();
                            listener.onAddPressed(new Measurement(dateStr, timeStr, sBPInt, dBPInt, HRInt, commentStr));
                        }
                    }
                }).create();

    }

}

