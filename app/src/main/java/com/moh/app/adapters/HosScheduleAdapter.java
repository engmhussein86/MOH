package com.moh.app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moh.app.models.HosScheduleClass;
import com.moh.app.moh.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class HosScheduleAdapter extends RecyclerView.Adapter<HosScheduleAdapter.ViewHolder> {
    Context context;
    ArrayList<HosScheduleClass> schedule;

    public HosScheduleAdapter(Context context, ArrayList<HosScheduleClass> schedule) {
        Log.e("Mariam_constr", "123");

        this.context = context;
        this.schedule = schedule;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("Mariam_create", "123");
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi = inflater.inflate(R.layout.row_hos_schedule_item, parent, false);

        ViewHolder holder = new ViewHolder(vi);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("Mariam_bind", "123");

        holder.LOC_NAME_AR.setText(schedule.get(position).getLOC_NAME_AR());
        holder.FULL_NAME.setText(schedule.get(position).getFULL_NAME());
        holder.CPT_NAME_AR.setText(schedule.get(position).getCPT_NAME_AR());

    }

    @Override
    public int getItemCount() {
        return schedule.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView LOC_NAME_AR, FULL_NAME, CPT_NAME_AR;

        public ViewHolder(View itemView) {
            super(itemView);
            LOC_NAME_AR = (TextView) itemView.findViewById(R.id.LOC_NAME_AR);
            FULL_NAME = (TextView) itemView.findViewById(R.id.FULL_NAME);
            CPT_NAME_AR = (TextView) itemView.findViewById(R.id.CPT_NAME_AR);
        }
    }


}
