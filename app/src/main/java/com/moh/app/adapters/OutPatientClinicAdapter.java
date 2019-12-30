package com.moh.app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moh.app.models.OutPatientClinicClass;
import com.moh.app.moh.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class OutPatientClinicAdapter extends RecyclerView.Adapter<OutPatientClinicAdapter.ViewHolder> {
    Context context;
    ArrayList<OutPatientClinicClass> OutPatientClinicArray;

    public OutPatientClinicAdapter(Context context, ArrayList<OutPatientClinicClass> OutPatientClinicArray) {
        this.context = context;
        this.OutPatientClinicArray = OutPatientClinicArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi = inflater.inflate(R.layout.row_outclinic_item, parent, false);

        ViewHolder holder = new ViewHolder(vi);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("bind", OutPatientClinicArray.get(position).getMR_FULL_NAME_AR());

        holder.MR_FULL_NAME_AR.setText(OutPatientClinicArray.get(position).getMR_FULL_NAME_AR());
        holder.MRP_ID.setText(OutPatientClinicArray.get(position).getMRP_ID());
        holder.MRP_DOB.setText(OutPatientClinicArray.get(position).getMRP_DOB());
        holder.VISIT_TIME.setText(OutPatientClinicArray.get(position).getVISIT_TIME());
        holder.H_NAME_AR.setText(OutPatientClinicArray.get(position).getH_NAME_AR());
        holder.LOC_NAME_AR.setText(OutPatientClinicArray.get(position).getLOC_NAME_AR());
        holder.VISITTP_NAME_AR.setText(OutPatientClinicArray.get(position).getVISITTP_NAME_AR());
        holder.DOC_FULL_NAME_AR.setText(OutPatientClinicArray.get(position).getDOC_FULL_NAME_AR());

    }

    @Override
    public int getItemCount() {
        return OutPatientClinicArray.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView MRP_ID, MR_FULL_NAME_AR, MRP_DOB, VISIT_TIME, H_NAME_AR, LOC_NAME_AR, VISITTP_NAME_AR, DOC_FULL_NAME_AR;

        public ViewHolder(View itemView) {
            super(itemView);
            VISIT_TIME = (TextView) itemView.findViewById(R.id.VISIT_TIME);
            MR_FULL_NAME_AR = (TextView) itemView.findViewById(R.id.MR_FULL_NAME_AR);
            H_NAME_AR = (TextView) itemView.findViewById(R.id.H_NAME_AR);
            MRP_ID = (TextView) itemView.findViewById(R.id.MRP_ID);
            MRP_DOB = (TextView) itemView.findViewById(R.id.MRP_DOB);
            LOC_NAME_AR = (TextView) itemView.findViewById(R.id.LOC_NAME_AR);
            VISITTP_NAME_AR = (TextView) itemView.findViewById(R.id.VISITTP_NAME_AR);
            DOC_FULL_NAME_AR = (TextView) itemView.findViewById(R.id.DOC_FULL_NAME_AR);
        }
    }

}
