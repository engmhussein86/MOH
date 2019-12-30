package com.moh.app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moh.app.models.LabOrderClass;
import com.moh.app.moh.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class LabOrderAdapter extends RecyclerView.Adapter<LabOrderAdapter.ViewHolder> {
    Context context;
    ArrayList<LabOrderClass> LabOrderArray;

    public LabOrderAdapter(Context context, ArrayList<LabOrderClass> labOrderArray) {
        this.context = context;
        LabOrderArray = labOrderArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi = inflater.inflate(R.layout.row_laborder_item, parent, false);

        ViewHolder holder = new ViewHolder(vi);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("bind", LabOrderArray.get(position).getH_NAME_AR());
        holder.ORDER_REQUEST_DATE.setText(LabOrderArray.get(position).getORDER_REQUEST_DATE());
        holder.CATEGORY_NAME_AR.setText(LabOrderArray.get(position).getCATEGORY_NAME_AR());
        holder.H_NAME_AR.setText(LabOrderArray.get(position).getH_NAME_AR());

    }

    @Override
    public int getItemCount() {
        return LabOrderArray.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ORDER_REQUEST_DATE, CATEGORY_NAME_AR, H_NAME_AR;

        public ViewHolder(View itemView) {
            super(itemView);
            ORDER_REQUEST_DATE = (TextView) itemView.findViewById(R.id.ORDER_REQUEST_DATE);
            CATEGORY_NAME_AR = (TextView) itemView.findViewById(R.id.CATEGORY_NAME_AR);
            H_NAME_AR = (TextView) itemView.findViewById(R.id.H_NAME_AR);
        }
    }
}
