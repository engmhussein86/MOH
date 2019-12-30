package com.moh.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.moh.app.moh.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Hussein on 02/04/2016.
 */
public class SitesAdapter extends ArrayAdapter<HashMap<String, String>> {

    ArrayList<HashMap<String, String>> actions;
    Context context;

    public SitesAdapter(Context context, int resource, List<HashMap<String, String>> actions) {
        super(context, resource, actions);

        this.context = context;
        this.actions = (ArrayList) actions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap<String, String> map = actions.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.row_site_item, parent, false);

        TextView id = (TextView) view.findViewById(R.id.site_sid);
        TextView title = (TextView) view.findViewById(R.id.site_title);

        id.setText(map.get("LOC_SID"));
        title.setText(map.get("LOC_TITLE_AR"));

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        HashMap<String, String> map = actions.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.row_site_item, parent, false);

        TextView id = (TextView) view.findViewById(R.id.site_sid);
        TextView title = (TextView) view.findViewById(R.id.site_title);

        id.setText(map.get("LOC_SID"));
        title.setText(map.get("LOC_TITLE_AR"));

        return view;
    }
}
