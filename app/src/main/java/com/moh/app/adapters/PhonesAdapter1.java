package com.moh.app.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moh.app.db.PhoneDBHandler;
import com.moh.app.models.PhoneClass;
import com.moh.app.moh.R;

import java.util.ArrayList;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by pc-3 on 11/20/2016.
 */
public class PhonesAdapter1 extends RecyclerView.Adapter<PhonesAdapter1.ViewHolder> {

    Context context;
    ArrayList<PhoneClass> phones;

    public PhonesAdapter1(Context context, ArrayList<PhoneClass> phones) {
        this.context = context;
        this.phones = phones;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi = inflater.inflate(R.layout.row_phone_item, parent, false);

        ViewHolder holder = new ViewHolder(vi);
        //Log.e("TEST", "End create");
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final PhoneDBHandler handler = new PhoneDBHandler(context);

        holder.phone_sid.setText(phones.get(position).getPHONE_SID());//get("PHONE_SID"));
        holder.phone_pos.setText("" + (position + 1));
        holder.phone_no.setText(phones.get(position).getPHONE_NO());
        holder.phone_user.setText(phones.get(position).getPHONE_USER());
        holder.JOB_TITLE.setText(phones.get(position).getJOB_TITLE());
        holder.LOC_TITLE_AR.setText(phones.get(position).getLOC_TITLE_AR());


        if (handler.checkFav(Integer.parseInt(phones.get(position).getPHONE_SID())) > 0) {
            holder.fav_btn.setBackgroundResource(R.drawable.ic_favorite_blue_48dp);
            phones.get(position).setFav(true);
        } else {
            holder.fav_btn.setBackgroundResource(R.drawable.ic_favorite_border_blue_48dp);
            phones.get(position).setFav(false);
        }

        holder.call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("phone", "123");
                String phone = phones.get(position).getPHONE_NO();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "08" + phone)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {
                    context.startActivity(intent);
                }
            }
        });

        holder.fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (phones.get(position).isFav()) {

                    handler.deletePhone(Integer.parseInt(phones.get(position).getPHONE_SID()));
                    Toast.makeText(context, "تم الازالة من المفضلة ", Toast.LENGTH_SHORT).show();
                    holder.fav_btn.setBackgroundResource(R.drawable.ic_favorite_border_blue_48dp);
                    phones.get(position).setFav(false);

                } else {
                    handler.addPhone(phones.get(position));
                    Toast.makeText(context, "تمت الاضافة للمفضلة ", Toast.LENGTH_SHORT).show();
                    //holder.fav_btn.setImageResource(R.drawable.ic_favorite_red_48dp);
                    holder.fav_btn.setBackgroundResource(R.drawable.ic_favorite_blue_48dp);
                    phones.get(position).setFav(true);


                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return phones.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView phone_sid, phone_pos, phone_no, phone_user, is_fav, JOB_TITLE, LOC_TITLE_AR;
        ImageButton fav_btn, call_btn;
        LinearLayout row;


        public ViewHolder(View itemView) {
            super(itemView);

            phone_sid = (TextView) itemView.findViewById(R.id.phone_sid);
            phone_pos = (TextView) itemView.findViewById(R.id.phone_pos);
            phone_no = (TextView) itemView.findViewById(R.id.phone_no);
            phone_user = (TextView) itemView.findViewById(R.id.phone_user);
            JOB_TITLE = (TextView) itemView.findViewById(R.id.JOB_TITLE);
            LOC_TITLE_AR = (TextView) itemView.findViewById(R.id.LOC_TITLE_AR);
            is_fav = (TextView) itemView.findViewById(R.id.is_fav);
            fav_btn = (ImageButton) itemView.findViewById(R.id.fav_btn);
            call_btn = (ImageButton) itemView.findViewById(R.id.call_btn);
            row = (LinearLayout) itemView.findViewById(R.id.phone_item_row);


        }
    }
}
