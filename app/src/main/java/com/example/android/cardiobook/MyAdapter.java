package com.example.android.cardiobook;

import android.content.Context;
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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mCtx;
    private List<Measurement> measurementList;

    public MyAdapter(Context mCtx, List<Measurement> measurementList) {
        this.mCtx = mCtx;
        this.measurementList = measurementList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.card, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        Measurement measurement = measurementList.get(position);

        // Date formatting and view binding
        Date mDate = measurement.getDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(mDate);
        holder.mDate.setText(strDate);

        // Time formatting and view binding
        DateFormat timeFormat = new SimpleDateFormat("hh:mm");
        String strTime = timeFormat.format(mDate);
        holder.mTime.setText(strTime);

        // View binding for diastolic and systolic BP
        String diaBP = "Diastolic Blood Pressure: " + measurement.getDiastolicBP();
        String sysBP = "Systolic Blood Pressure: " + measurement.getDiastolicBP();
        holder.mDia.setText(diaBP);
        holder.mSys.setText(sysBP);

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

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mDate, mDia, mSys, mHR, mTime, mComment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.measDate);
            mTime = itemView.findViewById(R.id.Time);
            mDia = itemView.findViewById(R.id.diaBP);
            mSys = itemView.findViewById(R.id.sysBP);
            mHR = itemView.findViewById(R.id.heartRate);
            mComment = itemView.findViewById(R.id.Comment);

        }
    }
}
