package com.moh.app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moh.app.models.OperationClass;
import com.moh.app.moh.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by pc-3 on 5/7/2018.
 */

public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.ViewHolder> {

    Context context;
    ArrayList<OperationClass> OperationArray;

    public OperationAdapter(Context context, ArrayList<OperationClass> operationArray) {
        this.context = context;
        OperationArray = operationArray;
    }

    @Override
    public OperationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi = inflater.inflate(R.layout.row_operation_item1, parent, false);

        ViewHolder holder = new ViewHolder(vi);
        return holder;
    }

    @Override
    public void onBindViewHolder(OperationAdapter.ViewHolder holder, int position) {
        Log.e("bind", OperationArray.get(position).getMR_FULL_NAME_AR());

        holder.OP_TIME.setText(OperationArray.get(position).getOP_TIME());
        holder.MR_FULL_NAME_AR.setText(OperationArray.get(position).getMR_FULL_NAME_AR());
        holder.OP_TYPE_NAME_AR.setText(OperationArray.get(position).getOP_TYPE_NAME_AR() + "-" + OperationArray.get(position).getOP_STATUS_NAME_AR());
        holder.MRP_ID.setText(OperationArray.get(position).getMRP_ID());
        holder.MRP_DOB.setText(OperationArray.get(position).getMRP_DOB());
        holder.ICD_NAME_EN.setText(OperationArray.get(position).getICD_NAME_EN());
        holder.OP_ENTER_DATE.setText(OperationArray.get(position).getOP_ENTER_DATE());
        holder.H_NAME_AR.setText(OperationArray.get(position).getH_NAME_AR());
        holder.DOCTOR_NAME.setText(OperationArray.get(position).getDOCTOR_NAME());
        holder.OP_PATIENT_MOBILE_NO.setText(OperationArray.get(position).getOP_PATIENT_MOBILE_NO());

    }

    @Override
    public int getItemCount() {
        return OperationArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView MRP_ID, MR_FULL_NAME_AR, MRP_DOB, ICD_NAME_EN, OP_TYPE_NAME_AR, OP_TIME, OP_ENTER_DATE, H_NAME_AR, DOCTOR_NAME, OP_PATIENT_MOBILE_NO;

        public ViewHolder(View itemView) {
            super(itemView);
            OP_TIME = (TextView) itemView.findViewById(R.id.OP_TIME);
            MR_FULL_NAME_AR = (TextView) itemView.findViewById(R.id.MR_FULL_NAME_AR);
            OP_TYPE_NAME_AR = (TextView) itemView.findViewById(R.id.OP_TYPE_NAME_AR);
            MRP_ID = (TextView) itemView.findViewById(R.id.MRP_ID);
            MRP_DOB = (TextView) itemView.findViewById(R.id.MRP_DOB);
            ICD_NAME_EN = (TextView) itemView.findViewById(R.id.ICD_NAME_EN);
            OP_ENTER_DATE = (TextView) itemView.findViewById(R.id.OP_ENTER_DATE);
            H_NAME_AR = (TextView) itemView.findViewById(R.id.H_NAME_AR);
            DOCTOR_NAME = (TextView) itemView.findViewById(R.id.DOCTOR_NAME);
            OP_PATIENT_MOBILE_NO = (TextView) itemView.findViewById(R.id.OP_PATIENT_MOBILE_NO);
        }
    }
}
