


/**
 * Created by pc-3 on 1/21/2018.
 */


package com.moh.app.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moh.app.models.InjuriesClass;
import com.moh.app.moh.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by pc-3 on 11/20/2016.
 */
public class InjuriesAdapter extends RecyclerView.Adapter<InjuriesAdapter.ViewHolder> {

    Context context;
    ArrayList<InjuriesClass> Injuries;

    public InjuriesAdapter(Context context, ArrayList<InjuriesClass> Injuries) {
        this.context = context;
        this.Injuries = Injuries;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi = inflater.inflate(R.layout.row_injuries_item, parent, false);

        ViewHolder holder = new ViewHolder(vi);
        //Log.e("TEST", "End create");
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //holder.phone_sid.setText(phones.get(position).getPHONE_SID());//get("PHONE_SID"));
        holder.pos.setText("" + (position + 1));
        holder.AK_NAME_AR.setText("" + Injuries.get(position).getAK_NAME_AR());
        holder.COUNT_RESULT.setText("" + Injuries.get(position).getInjuriesCount());
        Log.e("TEST", "" + Injuries.get(position).getAK_NAME_AR());
//        holder.phone_user.setText(phones.get(position).getPHONE_USER());
//        holder.JOB_TITLE.setText(phones.get(position).getJOB_TITLE());
//        holder.LOC_TITLE_AR.setText(phones.get(position).getLOC_TITLE_AR());


    }

    @Override
    public int getItemCount() {
        return Injuries.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView AK_NAME_AR, COUNT_RESULT, pos;

        public ViewHolder(View itemView) {
            super(itemView);

            AK_NAME_AR = (TextView) itemView.findViewById(R.id.AK_NAME_AR);
            COUNT_RESULT = (TextView) itemView.findViewById(R.id.COUNT_RESULT);
            pos = (TextView) itemView.findViewById(R.id.pos);

        }
    }
}

