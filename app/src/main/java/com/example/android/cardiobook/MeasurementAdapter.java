package com.example.android.cardiobook;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.MeasurementViewHolder> {

    private Context mCtx;
    private List<Measurement> measurementList;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public MeasurementAdapter(Context mCtx, List<Measurement> measurementList) {
        this.mCtx = mCtx;
        this.measurementList = measurementList;
    }

    @Override
    public MeasurementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.card, null);
        return new MeasurementViewHolder(view, mlistener);
    }

    @Override
    public void onBindViewHolder(MeasurementViewHolder holder, int position) {


        Measurement measurement = measurementList.get(position);

        // Date formatting and view binding
        String mDate = measurement.getDate();
        holder.mDate.setText(mDate);

        // Time formatting and view binding
        holder.mTime.setText(measurement.getTime());

        // View binding for diastolic and systolic BP
        String diaBP = "Diastolic Blood Pressure: " + measurement.getDiastolicBP().getBP();
        String sysBP = "Systolic Blood Pressure: " + measurement.getSystolicBP().getBP();
        holder.mDia.setText(diaBP);
        holder.mSys.setText(sysBP);

        // highlight text if BP is unusual
        if (measurement.getDiastolicBP().isUnusual()) {
            holder.mDia.setTextColor(Color.parseColor("#D50000"));
        }

        if (measurement.getSystolicBP().isUnusual()) {
            holder.mSys.setTextColor(Color.parseColor("#D50000"));
        }

        // View binding for heart rate
        String HR = "Heart rate: " + measurement.getHeartRate();
        holder.mHR.setText(HR);

        // View binding for the comment
        String comment = "Comment: " + measurement.getComment();
        holder.mComment.setText(comment);
    }

    @Override
    public int getItemCount() {
        return measurementList.size();
    }

    public class MeasurementViewHolder extends RecyclerView.ViewHolder {

        TextView mDate, mDia, mSys, mHR, mTime, mComment;

        public MeasurementViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mDate = itemView.findViewById(R.id.measDate);
            mTime = itemView.findViewById(R.id.Time);
            mDia = itemView.findViewById(R.id.diaBP);
            mSys = itemView.findViewById(R.id.sysBP);
            mHR = itemView.findViewById(R.id.heartRate);
            mComment = itemView.findViewById(R.id.Comment);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
