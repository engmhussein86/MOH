package com.moh.app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moh.app.models.HolidayClass;
import com.moh.app.moh.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by pc-3 on 8/29/2017.
 */


public class ViewHolidayAdapter extends RecyclerView.Adapter<ViewHolidayAdapter.ViewHolder> {

    Context context;
    ArrayList<HolidayClass> viewHolidayArray;

    public ViewHolidayAdapter(Context context, ArrayList<HolidayClass> viewHolidayArray) {
        this.context = context;
        this.viewHolidayArray = viewHolidayArray;
    }

    @Override
    public ViewHolidayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi = inflater.inflate(R.layout.row_viewholiday_item, parent, false);

        ViewHolder holder = new ViewHolder(vi);
        //Log.e("TEST", "End create");
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolidayAdapter.ViewHolder holder, final int position) {

        Log.e("TEST", viewHolidayArray.get(position).getTB_EMPBASICINFO_NO());
        holder.TB_CHOLIDAY_NAME.setText(viewHolidayArray.get(position).getTB_CHOLIDAY_NAME());
        holder.C_ASSIST_NAME_AR.setText(viewHolidayArray.get(position).getC_ASSIST_NAME_AR());
        holder.TB_EMPHOLIDAYS_HCOUNT.setText("" + (viewHolidayArray.get(position).getTB_EMPHOLIDAYS_HCOUNT()));
        holder.TB_EMPHOLIDAYS_STARTDATE.setText(viewHolidayArray.get(position).getTB_EMPHOLIDAYS_STARTDATE());
        holder.TB_EMPHOLIDAYS_ENDDATE.setText(viewHolidayArray.get(position).getTB_EMPHOLIDAYS_ENDDATE());

    }

    @Override
    public int getItemCount() {
        return viewHolidayArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView TB_CHOLIDAY_NAME, C_ASSIST_NAME_AR, TB_EMPHOLIDAYS_HCOUNT, TB_EMPHOLIDAYS_STARTDATE, TB_EMPHOLIDAYS_ENDDATE;

        public ViewHolder(View itemView) {
            super(itemView);
            TB_CHOLIDAY_NAME = (TextView) itemView.findViewById(R.id.TB_CHOLIDAY_NAME);
            C_ASSIST_NAME_AR = (TextView) itemView.findViewById(R.id.C_ASSIST_NAME_AR);
            TB_EMPHOLIDAYS_HCOUNT = (TextView) itemView.findViewById(R.id.TB_EMPHOLIDAYS_HCOUNT);
            TB_EMPHOLIDAYS_STARTDATE = (TextView) itemView.findViewById(R.id.TB_EMPHOLIDAYS_STARTDATE);
            TB_EMPHOLIDAYS_ENDDATE = (TextView) itemView.findViewById(R.id.TB_EMPHOLIDAYS_ENDDATE);


        }
    }
}

