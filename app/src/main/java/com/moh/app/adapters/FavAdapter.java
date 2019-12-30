package com.moh.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.moh.app.db.PhoneDBHandler;
import com.moh.app.models.PhoneClass;
import com.moh.app.moh.R;
import com.moh.app.phone.FragPhones;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by pc-3 on 11/21/2016.
 */
public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    Context context;
    ArrayList<PhoneClass> phones;

    public FavAdapter(Context context, ArrayList<PhoneClass> phones) {
        this.context = context;
        this.phones = phones;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vi = inflater.inflate(R.layout.row_fav_item, parent, false);
        ViewHolder holder = new ViewHolder(vi);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final PhoneDBHandler handler = new PhoneDBHandler(context);

        holder.fav_phone_sid.setText(phones.get(position).getPHONE_SID());
        // holder.fav_phone_pos.setText(""+(holder.getAdapterPosition()+1));
        holder.fav_phone_no.setText(phones.get(position).getPHONE_NO());
        holder.phone_user.setText(phones.get(position).getPHONE_USER());

        holder.fav_call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phones.get(position).getPHONE_NO();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "08" + phone)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ;
                context.startActivity(intent);
            }
        });

        holder.fav_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();

                Log.e("TEST", " " + pos);

                handler.deletePhone(Integer.parseInt(phones.get(pos).getPHONE_SID()));
                Toast.makeText(context, "تم الازالة من المفضلة ", Toast.LENGTH_SHORT).show();
                Log.e("TEST", " " + pos);
                phones.remove(pos);
                Log.e("TEST", " " + pos);
                Log.e("TEST", " " + phones.size());
                notifyItemRemoved(pos);
                Log.e("TEST", " " + pos);
                Log.e("TEST", " " + phones.size());
                FragPhones.size.setText("" + phones.size());


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

        TextView fav_phone_sid, fav_phone_no, phone_user;//, fav_phone_pos
        ImageButton fav_del_btn, fav_call_btn;


        public ViewHolder(View itemView) {
            super(itemView);

            Log.e("ViewHolder", "ViewHolder");

            fav_phone_sid = (TextView) itemView.findViewById(R.id.fav_phone_sid);
            //fav_phone_pos = (TextView) itemView.findViewById(R.id.fav_phone_pos);
            fav_phone_no = (TextView) itemView.findViewById(R.id.fav_phone_no);
            phone_user = (TextView) itemView.findViewById(R.id.phone_user);
            fav_del_btn = (ImageButton) itemView.findViewById(R.id.fav_del_btn);
            fav_call_btn = (ImageButton) itemView.findViewById(R.id.fav_call_btn);


        }
    }
}




