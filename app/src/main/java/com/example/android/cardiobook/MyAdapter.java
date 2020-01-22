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
        //inflating and returning our view holder
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
        holder.measDate.setText(strDate);

        // Time formatting and view binding
        DateFormat timeFormat = new SimpleDateFormat("hh:mm");
        String strTime = timeFormat.format(mDate);



    }

    @Override
    public int getItemCount() {
        return measurementList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mDate, mDia, mSys, mHR, mTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tweetMessage = itemView.findViewById(R.id.TweetMessage);
            date = itemView.findViewById(R.id.Date);
        }
    }
} {
}
