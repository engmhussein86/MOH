package com.moh.app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moh.app.models.FinanceClass;
import com.moh.app.moh.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by pc-3 on 12/29/2016.
 */
public class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.ViewHolder> {
    Context context;
    ArrayList<FinanceClass> financeArray;

    public FinanceAdapter(Context context, ArrayList<FinanceClass> financeArray) {
        this.context = context;
        this.financeArray = financeArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi = inflater.inflate(R.layout.row_finance_item, parent, false);

        ViewHolder holder = new ViewHolder(vi);
        //Log.e("TEST", "End create");
        return holder;
    }

    @Override
    public void onBindViewHolder(FinanceAdapter.ViewHolder holder, int position) {

        holder.HOS_NAME.setText(financeArray.get(position).getHOS_NAME());
        holder.ENTRY_DATE.setText(financeArray.get(position).getENTRY_DATE());
        holder.TOTALLY_AMOUNT.setText("" + (financeArray.get(position).getADDED_COSTS1() + financeArray.get(position).getAMOUNT()));
        holder.PAYED_AMOUNT.setText("" + (financeArray.get(position).getPAYED_AMOUNT()));
        holder.REMAINING.setText("" + ((financeArray.get(position).getADDED_COSTS1() + financeArray.get(position).getAMOUNT()) - (financeArray.get(position).getPAYED_AMOUNT())));

    }

    @Override
    public int getItemCount() {
        Log.e("size from adapter", financeArray.size() + "");
        return financeArray.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView HOS_NAME, ENTRY_DATE, TOTALLY_AMOUNT, PAYED_AMOUNT, REMAINING;

        public ViewHolder(View itemView) {
            super(itemView);

            HOS_NAME = (TextView) itemView.findViewById(R.id.HOS_NAME);
            ENTRY_DATE = (TextView) itemView.findViewById(R.id.ENTRY_DATE);
            TOTALLY_AMOUNT = (TextView) itemView.findViewById(R.id.TOTALLY_AMOUNT);
            PAYED_AMOUNT = (TextView) itemView.findViewById(R.id.PAYED_AMOUNT);
            REMAINING = (TextView) itemView.findViewById(R.id.REMAINING);
        }
    }
}