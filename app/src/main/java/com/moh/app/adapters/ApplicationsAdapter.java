package com.moh.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moh.app.DrawerActivity;
import com.moh.app.moh.R;
import com.moh.app.news.FragNews;
import com.moh.app.phone.FragPhones;

import androidx.fragment.app.FragmentTransaction;

/**
 * Created by pc-3 on 9/29/2016.
 */
public class ApplicationsAdapter extends BaseAdapter {

    String[] Application_Names;
    int[] Application_Icons;
    Intent intent;
    private Context mContext;
    private Activity mAct;


    public ApplicationsAdapter(Activity mAct, Context mContext, String[] Application_Names, int[] Application_Icons) {
        this.mContext = mContext;
        this.Application_Icons = Application_Icons;
        this.Application_Names = Application_Names;
        this.mAct = mAct;
    }

    @Override
    public int getCount() {
        return Application_Icons.length;
    }

    @Override
    public Object getItem(int position) {
        return Application_Icons[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Typeface font_bold = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + mContext.getString(R.string.font_name_bold));
        Holder holder = new Holder();
        View rowView, drawerView;

        LayoutInflater inflater = LayoutInflater.from(mContext);
        rowView = inflater.inflate(R.layout.row_application_item, null);


        holder.tv = (TextView) rowView.findViewById(R.id.textView1);
        holder.img = (ImageView) rowView.findViewById(R.id.imageView1);

        holder.tv.setText(Application_Names[position]);
        holder.img.setImageResource(Application_Icons[position]);

        holder.tv.setTypeface(font_bold);


        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(mContext, "You Clicked "+Application_Names[position], Toast.LENGTH_LONG).show();

                DrawerActivity act = (DrawerActivity) mAct;
                FragmentTransaction fragmentTransaction = act.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

                switch (position) {


                    case 0:

                        FragNews mFragNews = new FragNews();
                        fragmentTransaction.replace(R.id.frame, mFragNews, "A");

                        break;

                    case 1:

                        FragPhones mFragPhones = new FragPhones();
                        fragmentTransaction.replace(R.id.frame, mFragPhones, "A");

                        break;

//                    case 6:
//                        Fragment_Login frag_login = new Fragment_Login();
//                        fragmentTransaction.replace(R.id.frame, frag_login, "A");
//                        break;

                }

                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();


            }
        });

        return rowView;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }
}
