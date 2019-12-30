package com.moh.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moh.app.models.AttendanceClass;
import com.moh.app.moh.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by pc-3 on 1/26/2017.
 */
public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    Context context;
    ArrayList<AttendanceClass> AttendanceArray;

    public AttendanceAdapter(Context context, ArrayList<AttendanceClass> AttendanceArray) {
        this.context = context;
        this.AttendanceArray = AttendanceArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi = inflater.inflate(R.layout.row_attendance_item, parent, false);

        ViewHolder holder = new ViewHolder(vi);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        switch (AttendanceArray.get(position).getATTENDTYP()) {
            //Replacing the main content with ContentFragment Which is our Inbox View;
            case 13:
                holder.ATTENDDAT.setBackgroundResource(R.color.green_light);
                holder.ATTENDTIM.setBackgroundResource(R.color.green_light);
                holder.ARRTIME.setBackgroundResource(R.color.green_light);
                holder.ARRIDATE.setBackgroundResource(R.color.green_light);
                holder.ATTDAY.setBackgroundResource(R.color.green_light);
                holder.ATTENDSTA.setBackgroundResource(R.color.green_light);
                break;

            case 10:

                holder.ATTENDDAT.setBackgroundResource(R.color.purple_light);
                holder.ATTENDTIM.setBackgroundResource(R.color.purple_light);
                holder.ARRTIME.setBackgroundResource(R.color.purple_light);
                holder.ARRIDATE.setBackgroundResource(R.color.purple_light);
                holder.ATTDAY.setBackgroundResource(R.color.purple_light);
                holder.ATTENDSTA.setBackgroundResource(R.color.purple_light);
                break;

            //attend3, .attend4, .attend5, .attend6, .attend7, .attend8 holiday

            default:
                holder.ATTENDDAT.setBackgroundResource(R.color.icons);
                holder.ATTENDTIM.setBackgroundResource(R.color.icons);
                holder.ARRTIME.setBackgroundResource(R.color.icons);
                holder.ARRIDATE.setBackgroundResource(R.color.icons);
                holder.ATTDAY.setBackgroundResource(R.color.icons);
                holder.ATTENDSTA.setBackgroundResource(R.color.icons);

                break;
        }

        holder.ATTENDDAT.setText(AttendanceArray.get(position).getATTENDDAT());
        holder.ATTENDTIM.setText(AttendanceArray.get(position).getATTENDTIM());
        holder.ARRTIME.setText(AttendanceArray.get(position).getARRTIME());
        holder.ARRIDATE.setText(AttendanceArray.get(position).getARRIDATE());
        holder.ATTDAY.setText(AttendanceArray.get(position).getATTDAY());
        holder.ATTENDSTA.setText(AttendanceArray.get(position).getATTENDSTA());

    }

    @Override
    public int getItemCount() {
        return AttendanceArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ATTENDDAT, ATTENDTIM, ARRTIME, ARRIDATE, ATTDAY, ATTENDSTA;
        LinearLayout attn_layout;

        public ViewHolder(View itemView) {
            super(itemView);

            attn_layout = (LinearLayout) itemView.findViewById(R.id.attn_layout);
            ATTENDDAT = (TextView) itemView.findViewById(R.id.ATTENDDAT);
            ATTENDTIM = (TextView) itemView.findViewById(R.id.ATTENDTIM);
            ARRTIME = (TextView) itemView.findViewById(R.id.ARRTIME);
            ARRIDATE = (TextView) itemView.findViewById(R.id.ARRIDATE);
            ATTDAY = (TextView) itemView.findViewById(R.id.ATTDAY);
            ATTENDSTA = (TextView) itemView.findViewById(R.id.ATTENDSTA);
        }
    }


}
